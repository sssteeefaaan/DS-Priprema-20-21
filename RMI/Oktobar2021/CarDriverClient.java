import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CarDriverClient extends UnicastRemoteObject implements CarCallback {
    private CarManager manager;

    public CarDriverClient() throws MalformedURLException, RemoteException, NotBoundException {
        super();
        this.manager = (CarManager) Naming.lookup("rmi://127.0.0.1:1099/CarService");
    }

    @Override
    public void notifyCar(String address) throws RemoteException {
        System.out.println("Klijent vas ceka na lokaciji: '" + address + "'");
    }

    public void register() throws RemoteException {
        Car c = new Car(this);
        c.id = this.manager.register(c);

        if (c.id != -1)
            System.out.println("Uspesno registrovanje!");
        else
            System.out.println("Neuspesno registrovanje!");
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);

            CarDriverClient driver = new CarDriverClient();
            System.out.println("Driver uspesno povezan na server...");
 
            driver.register();

            System.out.println("Pritisnite Enter za kraj...");
            s.nextLine();

            s.close();
            System.exit(0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
