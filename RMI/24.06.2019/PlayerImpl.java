import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PlayerImpl extends UnicastRemoteObject implements Player {

    private int id;
    private String name;
    private int points;
    private CardCallback callback;
    private LinkedList<Card> picked;

    public PlayerImpl(int id, String name, int points, CardCallback callback) throws RemoteException {
        super();
        this.id = id;
        this.name = name;
        this.points = points;
        this.callback = callback;
        picked = new LinkedList<>();
    }

    @Override
    public int getId() throws RemoteException {
        return this.id;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public int getPoints() throws RemoteException {
        return this.points;
    }

    @Override
    public void setPoints(int points) throws RemoteException {
        this.points = points;
    }

    @Override
    public CardCallback getCallback() throws RemoteException {
        return this.callback;
    }

    @Override
    public void setCallback(CardCallback callback) throws RemoteException {
        this.callback = callback;
    }

    @Override
    public int cardSum() throws RemoteException {
        int s = 0;
        for (Card c : this.picked)
            s += c.value;

        return s;
    }

    @Override
    public void pickCard(Card card) throws RemoteException {
        this.picked.add(card);
    }

    @Override
    public List<Card> getCards() throws RemoteException {
        return this.picked;
    }

    @Override
    public void clearCards() throws RemoteException {
        this.picked.clear();
    }
}
