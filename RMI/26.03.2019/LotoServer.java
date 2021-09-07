import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class LotoServer {
    public LotoServer() throws MalformedURLException, RemoteException, AlreadyBoundException
    {
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/LotoService", new LotoManagerImpl());
    }

    public void close() throws RemoteException, MalformedURLException, NotBoundException
    {
        Naming.unbind("rmi://127.0.0.1:1099/LotoService");
    }

    public static void main(String[] args)
    {
        try {
            LotoServer server = new LotoServer();
            System.out.println("Server je pokrenut na adresi 'rmi://127.0.0.1:1099/LotoService'");

            Scanner s = new Scanner(System.in);

            System.out.println("Unesite ENTER za kraj!");
            s.nextLine();

            s.close();
            server.close();
            
            System.exit(0);
        
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
