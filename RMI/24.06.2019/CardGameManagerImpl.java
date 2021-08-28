import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;
import java.util.ArrayList;

public class CardGameManagerImpl extends UnicastRemoteObject implements CardGameManager {

    private HashMap<Integer, Player> players;
    private HashMap<Integer, Player> picked;
    private HashMap<Integer, Player> skipped;
    private ArrayList<Card> deck;

    public CardGameManagerImpl() throws RemoteException {
        super();

        this.players = new HashMap<>();
        this.picked = new HashMap<>();
        this.skipped = new HashMap<>();
        this.deck = new ArrayList<>();

        this.newGame();
    }

    private void newGame() throws RemoteException {
        this.deck.clear();
        for (int j = 0; j < 4; j++)
            for (int k = 0; k < 13; k++)
                this.deck.add(new Card(j < 2 ? "black" : "red", k + 2));

        for (Player p : this.players.values())
            p.clearCards();
        // p.getCards().clear();

        this.newWave();
    }

    private void newWave() throws RemoteException {
        for (Player p : this.players.values())
            p.getCallback().message("Novi krug!");

        this.picked.clear();
        this.skipped.clear();
    }

    @Override
    public Card requestCard(Player player) throws RemoteException {
        Card ret = null;

        if (this.skipped.containsKey(player.getId()) || this.picked.containsKey(player.getId())) {
            player.getCallback().message("Već ste odigrali krug!");
        } else if (player.cardSum() > 21) {
            player.getCallback().message("Suma Vaših karata je iznad 21! Automatski se preskače!");
            this.pass(player);
        } else {
            int ind = (int) Math.floor(Math.random() * (this.deck.size() - 1));
            ret = this.deck.get(ind);
            this.deck.remove(ind);
            player.pickCard(ret);
            player.getCallback().message("Izvukli ste " + ret.color + " " + ret.value + "!");
            this.picked.put(player.getId(), player);
        }

        if (this.players.size() == this.skipped.size() + this.picked.size()) {
            this.checkRes();
        }

        return ret;
    }

    private void checkRes() throws RemoteException {
        if (this.players.size() > this.deck.size() || this.skipped.size() == this.players.size()) {
            this.checkWinners();
            this.newGame();
        } else
            this.newWave();
    }

    private void checkWinners() throws RemoteException {
        ArrayList<Player> winners = new ArrayList<>();
        int max = -1, cardSum;

        for (Player player : this.players.values()) {
            cardSum = player.cardSum();
            if (cardSum < 22 && cardSum >= max) {
                if (cardSum != max) {
                    max = cardSum;
                    winners.clear();
                }
                winners.add(player);
            }
        }

        for (Player p : winners) {
            p.getCallback().isWinner();
            p.setPoints(p.getPoints() + 10);
        }

        for (Player p : this.players.values()) {
            if (!winners.contains(p)) {
                p.getCallback().message("Partija ima " + winners.size() + " pobednika!");
                for (Player w : winners)
                    p.getCallback().message(w.getName());
            }

            p.getCallback().message("Trenutno imate " + p.getPoints() + " poena!");
        }
    }

    @Override
    public void pass(Player player) throws RemoteException {
        if (this.skipped.containsKey(player.getId()) || this.picked.containsKey(player.getId())) {
            player.getCallback().message("Već ste odigrali krug!");
            return;
        }

        this.skipped.put(player.getId(), player);

        if (this.players.size() == this.skipped.size() + this.picked.size()) {
            this.checkRes();
        }
    }

    @Override
    public void registerPlayer(Player player) throws RemoteException {
        if (this.players.containsKey(player.getId()))
            return;
        this.players.put(player.getId(), player);
    }

    @Override
    public void unregisterPlayer(Player player) throws RemoteException {
        int id = player.getId();

        if (!this.players.containsKey(id))
            return;

        for (Card c : player.getCards())
            this.deck.add(c);

        this.players.remove(id);
        this.picked.remove(id);
        this.skipped.remove(id);
    }

}
