package com.Septembar2021;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.Scanner;

public class BingoClient extends UnicastRemoteObject implements BingoCallback {
    private BingoManager manager;

    public BingoClient() throws MalformedURLException, RemoteException, NotBoundException {
        manager = (BingoManager) Naming.lookup("rmi://127.0.0.1:1099/BingoService");
    }

    public void playTicket(Vector<Integer> numbers) throws RemoteException {
        if (this.manager.playTicket(numbers, this) != null)
            System.out.println("Odigrana kombinacija!");
        else
            System.out.println("Nije odigrana kombinacija!");
    }

    @Override
    public void isWinner(Ticket t) throws RemoteException {
        System.out.println("Kraj igre...\nVasa kombinacija [" + t.id + "] je pobednicka!");
        for (int i : t.numbers)
            System.out.print("\t" + i);
        System.out.println();
    }

    @Override
    public void isNotWinner(Ticket t) throws RemoteException {
        System.out.println("Kraj igre...\nVasa kombinacija [" + t.id + "] nije pobednicka!");
        for (int i : t.numbers)
            System.out.print("\t" + i);
        System.out.println();
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Unesite 15 razlicitih brojeva!");
            Vector<Integer> numbers = new Vector<>();
            for (int i = 0; i < 15; i++) {
                int numb = s.nextInt();
                while (numbers.contains(numb) || numb < 1 || numb > 90)
                {
                    System.out.println("Broj " + numb + " je vec unesen ili nije u opsegu!\nUnesite broj ponovo!");
                    numb = s.nextInt();
                }

                numbers.add(numb);
            }

            BingoClient c = new BingoClient();
            c.playTicket(numbers);

            s.nextLine();
            s.nextLine();
            s.close();

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
