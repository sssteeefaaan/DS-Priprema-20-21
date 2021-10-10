package Okt2020;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class VCalcServer {
    private VCalcManager manager;

    public VCalcServer() throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        manager = new VCalcManagerImpl();

        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/VCalcService", manager);

        System.out.println("Servis je pokrenut na adresi: rmi://127.0.0.1:1099/VCalcService");
    }

    public void close() throws RemoteException, MalformedURLException, NotBoundException
    {
        Naming.unbind("rmi://127.0.0.1:1099/VCalcService");
    }

    public static void main(String[] args)
    {
        try{
            VCalcServer server = new VCalcServer();
            Scanner s = new Scanner(System.in);
            
            System.out.println("Unesite enter za kraj!");
            s.nextLine();

            s.close();
            server.close();
            
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}