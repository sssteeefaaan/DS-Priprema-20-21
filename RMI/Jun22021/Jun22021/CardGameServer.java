package Jun22021;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.util.Scanner;

public class CardGameServer {

    public CardGameServer() throws RemoteException, MalformedURLException, AlreadyBoundException {
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/CardGameService", new CardGameManagerImpl());
    }

    public static void main(String[] args)
    {
        try {
            new CardGameServer();
            System.out.println("Server pokrenut....\nPritisnite Enter za kraj...");

            Scanner s = new Scanner(System.in);
            s.nextLine();
            s.close();
            System.exit(0);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
