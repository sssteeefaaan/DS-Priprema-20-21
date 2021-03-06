package April2021;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class VCalcServer {
    public VCalcServer() throws MalformedURLException, RemoteException, AlreadyBoundException {
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/VCalcService", new VCalcManagerImpl());
    }

    // Ovo ne treba
    public void close() throws RemoteException, MalformedURLException, NotBoundException
    {
        Naming.unbind("rmi://127.0.0.1:1099/VCalcService");
    }

    public static void main(String[] args) {
        try {
            VCalcServer server = new VCalcServer();

            System.out.println("Server je pokrenut....\nPritisnite Enter za kraj...");

            Scanner s = new Scanner(System.in);
            s.nextLine();
            s.close();
            
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
