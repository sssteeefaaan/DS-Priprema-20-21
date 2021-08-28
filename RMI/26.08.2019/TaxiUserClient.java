import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TaxiUserClient {

    private TaxiManager proxy;

    public TaxiUserClient(String host, String port, String service)
            throws RemoteException, NotBoundException, MalformedURLException {
        this.proxy = (TaxiManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);

        System.out.println("Uspešno povezan sa serverom na adresi: " + "rmi://" + host + ":" + port + "/" + service);
    }

    public void pozoviTaksi(String address) throws RemoteException {
        if (this.proxy.requestTaxi(address))
            System.out.println("Uspešno pozvan taksi na zahtevanu adresu!");
        else
            System.out.println("Svi automobili su zauzeti!");
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            TaxiUserClient c = new TaxiUserClient("127.0.0.1", "1099", "TaxiService");

            while (true) {
                System.out.println("****** Glavni Menu ******");
                System.out.println("1 - Pozovite taksi");
                System.out.println("2 - Exit");

                String temp = s.nextLine().trim();
                if (temp.equals("2"))
                    break;
                else if (temp.equals("1")) {
                    System.out.println("Unesite adresu:");
                    c.pozoviTaksi(s.nextLine().trim());
                } else
                    System.out.println("Unesite broj ispred opcije!");
            }

            s.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
