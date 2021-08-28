import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class TaxiDriverClient extends UnicastRemoteObject implements TaxiCallback {

    private TaxiManager proxy;
    private Taxi driver;

    public TaxiDriverClient(String host, String port, String service)
            throws RemoteException, NotBoundException, MalformedURLException {
        this.proxy = (TaxiManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
        this.driver = null;

        System.out.println("Uspešno povezan sa serverom na adresi: " + "rmi://" + host + ":" + port + "/" + service);
    }

    @Override
    public void notifyTaxi(String address) throws RemoteException {
        System.out.println("Klijent Vas čeka na adresi: " + address);
        driver.setStatus(false);
    }

    public boolean register(int id, String address, boolean isFree) throws RemoteException {
        this.driver = new TaxiImpl(id, address, isFree, this);
        if (proxy.register(id, this.driver)) {
            System.out.println("Uspešno registrovanje na sistem sa id-em '" + id + "'");
            return true;
        }

        System.out.println("Neuspešno registrovanje na sistem sa id-em '" + id + "'");
        return false;
    }

    public boolean unregister() throws RemoteException {
        if (this.driver != null) {
            if (proxy.unregister(this.driver.getId())) {
                System.out.println("Uspešno deregistrovanje sa sistema!");
                return true;
            } else
                System.out.println("Neuspešno deregistrovanje sa sistema!");
        } else
            System.out.println("Niste se registrovali na sistem!");

        return false;
    }

    public void postaviStatus(boolean status) throws RemoteException {
        if (this.driver != null) {
            proxy.setTaxiStatus(this.driver.getId(), status);
        } else
            System.out.println("Niste se registrovali na sistem!");
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            TaxiDriverClient c = new TaxiDriverClient("127.0.0.1", "1099", "TaxiService");

            System.out.println("Unesite id, adresu i status (slobodan/zauzet)");
            while (!c.register(Integer.parseInt(s.nextLine()), s.nextLine(),
                    s.nextLine().trim().toLowerCase().equals("slobodan")))
                ;

            while (true) {
                System.out.println("****** Glavni Menu ******");
                System.out.println("1 - Postavi status");
                System.out.println("2 - Exit");

                String temp = s.nextLine().trim();
                if (temp.equals("2"))
                    break;
                else if (temp.equals("1")) {
                    System.out.println("Unesite svoj status (slobodan/zauzet):");
                    c.postaviStatus(s.nextLine().trim().toLowerCase().equals("slobodan"));
                } else
                    System.out.println("Unesite broj ispred opcije!");
            }

            while (!c.unregister())
                ;

            s.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
