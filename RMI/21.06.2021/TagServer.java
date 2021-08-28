import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class TagServer {
    private TagManager manager;

    public TagServer(String host, String port, String service)
            throws RemoteException, MalformedURLException, AlreadyBoundException {

        manager = new TagManagerImpl();
        LocateRegistry.createRegistry(Integer.parseInt(port));
        Naming.bind("rmi://" + host + ":" + port + "/" + service, manager);

        System.out.println("Servis je pokrenut na adresi: rmi://" + host + ":" + port + "/" + service);
    }

    public void shutdown(String host, String port, String service)
            throws RemoteException, NotBoundException, MalformedURLException {
        Naming.unbind("rmi://" + host + ":" + port + "/" + service);
    }

    public static void main(String[] args) {
        try {
            TagServer server = new TagServer("127.0.0.1", "1099", "TagMessageService");

            System.out.println("Pritisnite ENTER za kraj...");
            Scanner s = new Scanner(System.in);
            s.nextLine();
            s.close();
            server.shutdown("127.0.0.1", "1099", "TagMessageService");

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
