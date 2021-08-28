import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.Scanner;

import javax.naming.NamingException;

public class TaxiServer {

    private TaxiManager manager;

    public TaxiServer(String host, String port, String service)
            throws RemoteException, MalformedURLException, AlreadyBoundException, NamingException {
        manager = new TaxiManagerImpl(10);
        LocateRegistry.createRegistry(Integer.parseInt(port));

        Naming.bind("rmi://" + host + ":" + port + "/" + service, manager);

        System.out.println("Servis je pokrenut na adresi: rmi://" + host + ":" + port + "/" + service);
    }

    public static void main(String[] args) {
        try {
            new TaxiServer("127.0.0.1", "1099", "TaxiService");

            Scanner s = new Scanner(System.in);

            System.out.println("Pritisnite ENTER za kraj!");
            s.nextLine();
            s.close();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
