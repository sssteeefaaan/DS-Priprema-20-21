import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class CardGameServer {

    private CardGameManager manager;

    public CardGameServer(String host, String port, String service)
            throws AlreadyBoundException, MalformedURLException, RemoteException {
        LocateRegistry.createRegistry(Integer.parseInt(port));
        manager = new CardGameManagerImpl();
        Naming.bind("rmi://" + host + ":" + port + "/" + service, manager);

        System.out.println("Servis je pokrenut na adresi: rmi://" + host + ":" + port + "/" + service);

    }

    public void shutdown(String host, String port, String service) throws NotBoundException, MalformedURLException, RemoteException{
        Naming.unbind("rmi://" + host + ":" + port + "/" + service);
    }

    public static void main(String[] args) {
        try {
            CardGameServer server = new CardGameServer("127.0.0.1", "1099", "CardGameService");
            Scanner s = new Scanner(System.in);

            System.out.println("Pritisnite enter za kraj...");
            s.nextLine();
            s.close();

            server.shutdown("127.0.0.1", "1099", "CardGameService");

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
