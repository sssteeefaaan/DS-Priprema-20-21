import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CarUserClient {

    private CarManager proxy;

    public CarUserClient() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        proxy = (CarManager) Naming.lookup("rmi://127.0.0.1:1099/CarService");
    }

    public void requestCar(String address) throws RemoteException {
        if (proxy.requestCar(address))
            System.out.println("Uspesno pozvan auto na adresu!");
        else
            System.out.println("Nema slobodnih vozila!");
    }

    public static void main(String[] args)
    {
        try{
            CarUserClient klijent = new CarUserClient();
            Scanner s = new Scanner(System.in);

            System.out.print("Na koju adresu dolazi taksi: ");
            klijent.requestCar(s.nextLine().trim());

            s.nextLine();
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
