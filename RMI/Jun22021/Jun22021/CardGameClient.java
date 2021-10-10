package Jun22021;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CardGameClient extends UnicastRemoteObject implements CardCallback {

    private Player p;
    private CardGameManager manager;

    public CardGameClient() throws MalformedURLException, RemoteException, NotBoundException {
        manager = (CardGameManager) Naming.lookup("rmi://127.0.0.1:1099/CardGameService");
    }

    public void register(String name) throws RemoteException {
        if (this.p != null) {
            System.out.println("Vec ste registrovani!");
            return;
        }

        this.p = new Player(this.manager.register(name, this), name, 0, this);

        if (this.p.id != -1)
            System.out.println("Uspesna registracija!");
        else
        {
            this.p = null;
            System.out.println("Neuspesna registracija!");
        }
    }

    public void requestCard() throws RemoteException {
        Card c = this.manager.requestCard(this.p);

        if (c != null) {
            this.p.pickedCards.add(c);
            System.out.println("Izvukli ste: " + c.color + " " + c.suit + " " + c.value);
        } else
            System.out.println("Neuspesno izvlacenje karte!");
    }

    public void passCard() throws RemoteException {
        this.manager.passCard(this.p);
        System.out.println("Preskocili ste krug!");
    }

    public void startGame() throws RemoteException {
        this.manager.startGame();
    }

    public void unregister() throws RemoteException {
        if (this.p == null)
            System.out.println("Niste registrovani!");

        this.manager.unregister(this.p.id);
        this.p = null;
    }

    @Override
    public void isNewRound() throws RemoteException {
        System.out.println("Krenula je nova runda!");
    }

    @Override
    public void isNewGame() throws RemoteException {
        System.out.println("***** Krenula je nova igra! *****");
        if (p != null) {
            p.pickedCards.clear();
            // p.points = 0;
        }
    }

    @Override
    public void isWinner() throws RemoteException {
        System.out.println("Pobedili ste u ovoj rundi! Dobili ste 10 poena!");
        this.p.points += 10;
    }

    @Override
    public void isLoser() throws RemoteException {
        System.out.println("Vise srece u drugoj rundi!");
    }

    @Override
    public void isEliminated() throws RemoteException {
        System.out.println("Suma Vasih karata je preko 21, stoga ste eliminisani do kraja igre!");
    }

    // Nisam testirao, mrzi me.....
    public static void main(String[] args) {
        try {
            CardGameClient client = new CardGameClient();
            Scanner s = new Scanner(System.in);

            boolean loop = true;
            while (loop) {
                System.out.println("\n*** Izaberi opciju ***");
                System.out.println("1 - Registracija");
                System.out.println("2 - Pocetak igre");
                System.out.println("3 - Izvuci kartu");
                System.out.println("4 - Preskoci izvlacenje");
                System.out.println("5 - Kraj");

                switch (s.nextLine().trim()) {
                    case ("1"):
                        System.out.print("Unesite ime: ");
                        client.register(s.nextLine().trim());
                        break;
                    case ("2"):
                        client.startGame();
                        break;
                    case ("3"):
                        client.requestCard();
                        break;
                    case ("4"):
                        client.passCard();
                        break;
                    case ("5"):
                        client.unregister();
                        loop = false;
                        break;
                    default:
                        System.out.println("Unesite broj pored opcije koju zelite odabrati!");
                        break;
                }

            }

            s.nextLine();
            s.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
