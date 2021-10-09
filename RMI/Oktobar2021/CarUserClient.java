import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CarUserClient {

    private CarManager manager;

    public CarUserClient() throws MalformedURLException, RemoteException, NotBoundException {
        this.manager = (CarManager) Naming.lookup("rmi://127.0.0.1:1099/CarService");
    }

    public void requestCar(String address) throws RemoteException {
        if (this.manager.requestCar(address))
            System.out.println("Vozilo uspesno pozvano!");
        else
            System.out.println("Nespesno pozivanje vozila!");
    }

    public static void main(String[] args)
    {
        try{
            Scanner s = new Scanner(System.in);
            CarUserClient user = new CarUserClient();

            System.out.println("Uspesno povezani na server!");

            System.out.print("Unesite adresu: ");
            user.requestCar(s.nextLine().trim());

            s.close();
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
