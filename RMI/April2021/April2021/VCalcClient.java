package April2021;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class VCalcClient extends UnicastRemoteObject implements VCalcCallback {

    private VCalcManager manager;

    public VCalcClient() throws RemoteException, MalformedURLException, NotBoundException {
        manager = (VCalcManager) Naming.lookup("rmi://127.0.0.1:1099/VCalcService");
    }

    public void sendVCalcRequest(VCalcRequest req) throws RemoteException {
        if (this.manager.sendVCalcRequest(req) != -1)
            System.out.println("Uspesno dodavanje zahteva!");
        else
            System.out.println("Neuspesno dodavanje zahteva!");
    }

    public void runNextVCalc() throws RemoteException {
        if (this.manager.runNextVCalc())
            System.out.println("Uspesno izvrsena operacija!");
        else
            System.out.println("Neuspesno izvrsena operacija!");
    }

    @Override
    public void onDone(int cid, double result) throws RemoteException {
        System.out.println("Kalkulacija sa ID-em " + cid + " je izvrsena. Rezultat iznosi: " + result);
    }

    public static void main(String[] args) {
        try {
            VCalcClient client = new VCalcClient();

            for (int i = 0; i < 2; i++)
                client.sendVCalcRequest(new VCalcRequest(0, new Vector<Double>() {
                    {
                        add(Math.random() * 100);
                        add(Math.random() * -20);
                        add(Math.random() * 55);
                    }
                }, new Vector<Double>() {
                    {
                        add(Math.random() * -11);
                        add(Math.random() * 60);
                        add(Math.random() * 100);
                    }
                }, client));

            client.runNextVCalc();

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