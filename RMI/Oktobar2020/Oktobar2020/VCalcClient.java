package Oktobar2020;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class VCalcClient extends UnicastRemoteObject implements VCalcCallback {

    private VCalcManager manager;
    // Ovo nije neophodno, moglo je samo da se potvrdi da je prosledjen request i da
    // callback odstampa Id i rez
    // ali da bude malo preglednije, odradjeno je malo duze, aka da se cuvaju
    // zahtevi
    private HashMap<Integer, VCalcRequest> zahtevi;

    public VCalcClient() throws RemoteException, MalformedURLException, NotBoundException {
        super();

        this.manager = (VCalcManager) Naming.lookup("rmi://127.0.0.1:1099/VCalcService");
        this.zahtevi = new HashMap<>();
    }

    public void sendVCalcRequest(Vector<Double> a, Vector<Double> b) throws RemoteException {
        VCalcRequest req = new VCalcRequest(-1, a, b, this);
        if ((req.cId = this.manager.sendVCalcRequest(req)) != -1) {
            System.out.println("Uspesno poslat zahtev! ID zahteva: " + req.cId);
            this.zahtevi.put(req.cId, req);
        } else
            System.out.println("Neuspesno slanje zahteva!");
    }

    public void runNextCalc() throws RemoteException {
        if (this.manager.runNextVCalc())
            System.out.println("Uspesno izvrsena kalkulacija!");
        else
            System.out.println("Neuspesno izvrsena kalkulacija!");
    }

    @Override
    public void onDone(int cId, double result) throws RemoteException {
        VCalcRequest req = this.zahtevi.remove(cId);

        System.out.println("Izracunavanje sa ID-em " + cId + " je izvrseno!");

        System.out.print("Vector a = [");
        for (Double d : req.a)
            System.out.print("\t" + d);
        System.out.println("\t]");

        System.out.print("Vector b = [");
        for (Double d : req.b)
            System.out.print("\t" + d);
        System.out.println("\t]");

        System.out.println("Rezultat: " + result);
    }

    public static void main(String[] args) {
        try {

            VCalcClient client = new VCalcClient();

            client.sendVCalcRequest(new Vector<Double>() {
                {
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                }
            }, new Vector<Double>() {
                {
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                    add(Math.random() * 10);
                }
            });

            Vector<Double> a = new Vector<>(), b = new Vector<>();

            for(int i = 0; i < 10; i++)
            {
                a.add(Math.random() * 10);
                b.add(Math.random() * 10);
            }

            client.sendVCalcRequest(a, b);

            client.runNextCalc();

            System.out.println("Pritisnite enter za kraj...");
            Scanner s = new Scanner(System.in);
            s.nextLine();
            s.close();

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
