package Okt2020;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class VCalcClient extends UnicastRemoteObject implements VCalcCallback
{
    private VCalcManager proxy;

    public VCalcClient() throws MalformedURLException, RemoteException, NotBoundException
    {
        proxy = (VCalcManager) Naming.lookup("rmi://127.0.0.1:1099/VCalcService");
    }

    @Override
    public void onDone(int cid, double result) throws RemoteException {
        System.out.println("Rezultat operacije sa ID-em " + cid + " iznosi: " + result);
    }

    public int requestOperation(Vector<Double> a, Vector<Double> b) throws RemoteException
    {
        return this.proxy.sendVCalcRequest(new VCalcRequest(a, b, this));
    }
    
    public boolean doOperation() throws RemoteException
    {
        return this.proxy.runNextVCalc();
    }
    
    public static void main(String[] args)
    {
        try{
            VCalcClient client = new VCalcClient();
            Scanner s = new Scanner(System.in);
            String input;
            int temp;

            while (true) {
                System.out.println("***** Glavni Menu *****");
                System.out.println("1 - Novi zahtev");
                System.out.println("2 - Izvrsi jednu operaciju na serveru");
                System.out.println("3 - Exit");

                input = s.nextLine().trim();

                if (input.equals("1")) 
                {
                    System.out.print("Unesite duzinu vektora: ");
                    temp = Integer.parseInt(s.nextLine().trim());

                    Vector<Double> a = new Vector<>(temp);
                    Vector<Double> b = new Vector<>(temp);

                    System.out.print("Unesite vektor a: ");
                    for (int i = 0; i < temp; i++)
                        a.add(s.nextDouble());
                    s.nextLine();

                    System.out.print("Unesite vektor b: ");
                    for (int i = 0; i < temp; i++)
                        b.add(s.nextDouble());
                    s.nextLine();

                    System.out.println("CID operacije: " + client.requestOperation(a, b));
                }
                else if (input.equals("2")) 
                {
                    if (client.doOperation())
                        System.out.println("Operacija uspesno izvrsena!");
                    else
                        System.out.println("Operacija nije izvrsena!");
                } 
                else if (input.equals("3")) 
                {
                    break;
                }
                else {
                    System.out.println("Unesite redni broj komande!");
                }
                
            }
            s.close();
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}