import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Player extends Remote {
    int getId() throws RemoteException;

    String getName() throws RemoteException;

    int getPoints() throws RemoteException;

    void setPoints(int points) throws RemoteException;

    CardCallback getCallback() throws RemoteException;

    void setCallback(CardCallback callback) throws RemoteException;

    int cardSum() throws RemoteException;

    void pickCard(Card card) throws RemoteException;
    
    List<Card> getCards() throws RemoteException;

    void clearCards() throws RemoteException;
}
