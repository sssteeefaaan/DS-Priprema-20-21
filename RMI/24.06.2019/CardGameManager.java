import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardGameManager extends Remote {
    Card requestCard(Player player) throws RemoteException;

    void pass(Player player) throws RemoteException;

    void registerPlayer(Player player) throws RemoteException;

    void unregisterPlayer(Player player) throws RemoteException;
}
