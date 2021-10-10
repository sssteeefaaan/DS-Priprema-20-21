package Jun22021;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CardGameManagerImpl extends UnicastRemoteObject implements CardGameManager {

    public ArrayList<Card> deck;
    public HashMap<Integer, Player> players;

    public HashMap<Integer, Player> eliminated;

    // Svake runde se pravi novi HashMap
    public HashMap<Integer, Player> pickedACard;
    public HashMap<Integer, Player> skippedRound;

    // Genrator ID-a
    public int idGen;

    public CardGameManagerImpl() throws RemoteException {
        super();

        this.deck = new ArrayList<>();
        this.players = new HashMap<>();
        this.pickedACard = new HashMap<>();
        this.skippedRound = new HashMap<>();
        this.eliminated = new HashMap<>();
        this.idGen = 1;

        // this.startGame();
    }

    @Override
    public Card requestCard(Player player) throws RemoteException {
        if (player == null || /* this.eliminated.containsKey(player.id) || */ !this.players.containsKey(player.id)
                || this.pickedACard.containsKey(player.id) || this.skippedRound.containsKey(player.id))
            return null;

        Player pReg = this.players.get(player.id);
        Card c = this.deck.remove((int) (Math.random() * this.deck.size()));
        pReg.pickedCards.add(c);
        this.pickedACard.put(player.id, pReg);

        // Los pristup, jer mu vraca kartu tek nakon sto proveri da li ima pobednika i
        // proglasi pobednike....
        // Card je bolje da se vrati kroz callback
        this.checkGame();

        return c;
    }

    @Override
    public void passCard(Player player) throws RemoteException {
        if (player == null || !this.players.containsKey(player.id) || this.pickedACard.containsKey(player.id)
                || this.skippedRound.containsKey(player.id))
            return;

        this.skippedRound.put(player.id, this.players.get(player.id));
        this.checkGame();
    }

    @Override
    public void startGame() throws RemoteException {
        this.deck.clear();

        for (int i = 1; i < 14; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++)
                    this.deck.add(new Card((j == 0 ? "Black" : "Red"),
                            (j == 0 ? (k == 0 ? "Clubs" : "Spades") : (k == 0 ? "Hearts" : "Diamonds")), i + 1));

        for (Player p : this.eliminated.values())
            this.players.put(p.id, p);

        this.eliminated.clear();

        for (Player p : this.players.values())
            p.callback.isNewGame();

        this.newRound();
    }

    @Override
    public int register(String name, CardCallback callback) throws RemoteException {
        if (name == null || name.equals("") || callback == null || this.pickedACard.size() > 0
                || this.skippedRound.size() > 0)
            return -1;

        this.players.put(this.idGen, new Player(this.idGen, name, 0, callback));

        return idGen++;
    }

    @Override
    public void unregister(int id) throws RemoteException {
        Player p = this.players.remove(id);
        if (p == null)
            p = this.eliminated.remove(id);

        if (p != null) {
            for (Card c : p.pickedCards)
                this.deck.add(c);

            this.pickedACard.remove(id);
            this.skippedRound.remove(id);
        }
    }

    private void newRound() throws RemoteException {
        this.pickedACard.clear();
        this.skippedRound.clear();

        for (Player p : this.players.values())
            p.callback.isNewRound();

        for (Player p : this.eliminated.values())
            p.callback.isNewRound();
    }

    private void checkGame() throws RemoteException {
        if (this.deck.size() > 0 && this.players.size() != this.skippedRound.size()) {
            if (this.players.size() == this.pickedACard.size() + this.skippedRound.size())
                this.newRound();

            return;
        }

        LinkedList<Player> winners = new LinkedList<>(), losers = new LinkedList<>();

        int max = 0;
        for (Player p : this.players.values()) {
            int sum = 0;
            for (Card c : p.pickedCards)
                sum += c.value;

            if (sum > 21)
                this.eliminated.put(p.id, p);
            else if (sum == max)
                winners.add(p);
            else if (sum > max) {
                max = sum;

                for (Player p1 : winners)
                    losers.add(p1);

                winners.clear();
                winners.add(p);
            }
        }

        for (Player p : winners) {
            p.points += 10;
            p.callback.isWinner();
        }

        for (Player p : losers)
            p.callback.isLoser();

        for (Player p : this.eliminated.values()) {
            p.callback.isEliminated();
            this.players.remove(p.id);
        }

        this.startGame();
    }
}