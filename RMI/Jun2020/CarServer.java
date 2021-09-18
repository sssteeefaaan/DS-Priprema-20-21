import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class CarServer {
    
    public CarServer() throws MalformedURLException, RemoteException, AlreadyBoundException
    {
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/CarService", new CarManagerImpl());
    }

    public void close() throws RemoteException, MalformedURLException, NotBoundException
    {
        Naming.unbind("rmi://127.0.0.1:1099/CarService");
    }

    public static void main(String[] args)
    {
        try {
            
            CarServer server = new CarServer();
            Scanner s = new Scanner(System.in);

            s.nextLine();

            server.close();
            s.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
