package com.Septembar2021;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


// Ovo je napisano samo za testiranje, ne treba da se implementira
public class BingoDrawingClient {
    private BingoManager manager;

    public BingoDrawingClient() throws MalformedURLException, RemoteException, NotBoundException {
        manager = (BingoManager) Naming.lookup("rmi://127.0.0.1:1099/BingoService");
    }

    public void drawNumber() throws RemoteException {
        int number = this.manager.drawNumber();

        if (number != -1)
            System.out.println("Izvucen je broj: " + number);
        else
            System.out.println("Greska pri izvlacenju broja!");
    }

    public static void main(String[] args) {
        try {
            BingoDrawingClient bdc = new BingoDrawingClient();
            Scanner s = new Scanner(System.in);
            System.out.println("Unesite draw za izvlacenje/bilo sta za exit");

            while (s.nextLine().trim().toLowerCase().equals("draw"))
                bdc.drawNumber();

            s.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
