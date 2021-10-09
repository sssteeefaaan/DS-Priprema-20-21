package April2019;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class LotoManagerImpl extends UnicastRemoteObject implements LotoManager {

    private LinkedList<Integer> izvuceni;
    private HashMap<Integer, Ticket> tiketi;

    protected LotoManagerImpl() throws RemoteException {
        super();
        this.izvuceni = new LinkedList<>();
        this.tiketi = new HashMap<>();
    }

    @Override
    public Ticket playTicket(Vector<Integer> numbers) throws RemoteException {
        for (int i : numbers)
            System.out.println(i + " ");
        
        if (izvuceni.size() > 0)
            return null;

        if (numbers.size() != 7)
            return null;

        for (int i : numbers)
            if (i < 1 || i > 39)
                return null;

        int id;
        do {
            id = (int) Math.random() * 65000;
        } while (tiketi.containsKey(id));

        Ticket temp = new Ticket(id, numbers);
        this.tiketi.put(id, temp);
        return temp;
    }

    @Override
    public Vector<Integer> getWinners() throws RemoteException {
        if (izvuceni.size() < 7)
            return null;

        Vector<Integer> ret = new Vector<>();
        int max = 0, temp;
        for (Ticket t : tiketi.values()) {
            temp = 0;

            for (int i : izvuceni)
                if (t.numbers.contains(i))
                    temp++;

            if (temp > max) {
                max = temp;
                ret.clear();
            } else if (temp == max)
                ret.add(t.id);
        }

        return ret;
    }

    @Override
    public void drawNumbers() throws RemoteException {
        if (izvuceni.size() > 7)
            return;

        int newOne;
        do {
            newOne = ((int) Math.random() * 39) + 1;
        } while (izvuceni.contains(newOne));

        izvuceni.add(newOne);
    }

}
