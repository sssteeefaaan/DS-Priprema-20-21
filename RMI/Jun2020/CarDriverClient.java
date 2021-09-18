import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CarDriverClient extends UnicastRemoteObject implements CarCallback {

    private CarManager proxy;

    public CarDriverClient() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        proxy = (CarManager) Naming.lookup("rmi://127.0.0.1:1099/CarService");
    }

    public void register(String address, boolean isFree) throws RemoteException {
        int res = proxy.register(new Car(-1, address, isFree, this));

        if (res >= 0)
            System.out.println("Uspesno registrovani sa ID-em " + res);
        else
            System.out.println("Neuspesno registrovanje!");
    }

    public void notifyCar(String address) throws RemoteException {
        System.out.println("Klijent Vas ceka na adresi: '" + address + "'");
    }

    public static void main(String[] args)
    {
        try{
            CarDriverClient klijent = new CarDriverClient();
            Scanner s = new Scanner(System.in);

            System.out.print("Unesite svoju adresu: ");
            klijent.register(s.nextLine().trim(), true);

            s.nextLine();
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
