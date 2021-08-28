import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CardGameClient extends UnicastRemoteObject implements CardCallback {

    private CardGameManager proxy;
    private Player igrac;

    public CardGameClient(String host, String port, String service)
            throws RemoteException, MalformedURLException, NotBoundException {
        proxy = (CardGameManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);

        System.out.println("Klijent je uspešno povezan na servis: rmi://" + host + ":" + port + "/" + service);

        igrac = null;
    }

    public void registracija(int id, String name) throws RemoteException {
        this.igrac = new PlayerImpl(id, name, 0, this);
        this.proxy.registerPlayer(this.igrac);

        System.out.println("Uspešno logovanje na sistem!");
    }

    public void izlogujSe() throws RemoteException {
        this.proxy.unregisterPlayer(this.igrac);
        this.igrac = null;

        System.out.println("Uspešno izlogovanje sa sistema!");
    }

    public Card izvuci() throws RemoteException {
        if (this.igrac != null)
            return this.proxy.requestCard(this.igrac);
        return null;
    }

    public void preskoci() throws RemoteException {
        if (this.igrac != null)
            this.proxy.pass(this.igrac);
    }

    public void prikaziKarte() throws RemoteException {
        if (this.igrac != null) {
            System.out.println("Karte: ");
            for (Card c : this.igrac.getCards())
                System.out.println(c.color + " " + c.value);
        }
    }

    @Override
    public void isWinner() throws RemoteException {
        System.out.println("Pobedili ste! Jeij!");
    }

    @Override
    public void message(String msg) throws RemoteException {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        try {
            CardGameClient c = new CardGameClient("127.0.0.1", "1099", "CardGameService");
            Scanner s = new Scanner(System.in);
            System.out.println("Unesite id i ime za registraciju!");
            c.registracija(Integer.parseInt(s.nextLine()), s.nextLine());

            while (true) {
                System.out.println("1 - Izvuci kartu");
                System.out.println("2 - Preskoci");
                System.out.println("3 - Izloguj se");

                String temp = s.nextLine();

                if (temp.equals("1")) {
                    c.izvuci();
                } else if (temp.equals("2")) {
                    c.preskoci();
                } else if (temp.equals("3")) {
                    break;
                } else {
                    System.out.println("Unesite redni broj pored opcije!");
                }
            }

            c.izlogujSe();

            s.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
