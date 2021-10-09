package com.Septembar2021;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class BingoManagerImpl extends UnicastRemoteObject implements BingoManager {

    private HashMap<Integer, Ticket> tickets;
    private Vector<Integer> drawnNumbers;
    private int idGen;
    private boolean status; // true - still playing, false - ended

    public BingoManagerImpl() throws RemoteException {
        super();

        this.tickets = new HashMap<>();
        this.drawnNumbers = new Vector<>(90);
        this.idGen = 1;
        this.status = true;
    }

    @Override
    public Ticket playTicket(Vector<Integer> numbers, BingoCallback callback) throws RemoteException {
        if (!this.status || this.drawnNumbers.size() > 0)
            return null;

        if (numbers == null || numbers.size() != 15 || callback == null)
            return null;

        // nije neophodno proveravasti kombinaciju, ali ako vam je dosadno
        // proverite da li su brojevi u opsegu 1-90
        // proverite da li se ne ponavaljaju
        // Smatramo da je to sve ok

        this.tickets.put(this.idGen, new Ticket(this.idGen, numbers, callback));

        return this.tickets.get(this.idGen++);
    }

    @Override
    public int drawNumber() throws RemoteException {
        int newNumber = -1;

        if (!this.status || this.drawnNumbers.size() > 89)
            return newNumber;

        do {
            newNumber = (int)(Math.random() * 90) + 1;
        } while (drawnNumbers.contains(newNumber));

        drawnNumbers.add(newNumber);

        if (this.drawnNumbers.size() >= 15)
            this.checkTickets();

        return newNumber;
    }

    private void checkTickets() throws RemoteException {
        LinkedList<Ticket> winners = new LinkedList<>(), loosers = new LinkedList<>();

        for (Ticket t : this.tickets.values()) {
            int correct = 0;
            for (int i : t.numbers)
                if (this.drawnNumbers.contains(i))
                    correct++;

            if (correct == 15)
                winners.add(t);
            else
                loosers.add(t);
        }

        if (this.drawnNumbers.size() == 90 || winners.size() > 0) {
            this.status = false;

            for (Ticket t : winners)
                t.callback.isWinner(t);

            for (Ticket t : loosers)
                t.callback.isNotWinner(t);
        }
    }

}
