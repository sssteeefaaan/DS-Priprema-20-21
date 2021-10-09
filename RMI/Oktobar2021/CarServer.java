import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class CarServer {

    public CarServer(int capacity) throws RemoteException, MalformedURLException, AlreadyBoundException {
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/CarService", new CarManagerImpl(capacity));
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);

            new CarServer(5);
            System.out.println("Server je pokrenut...\nPritisnite Enter za kraj...");

            s.nextLine();
            s.close();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
