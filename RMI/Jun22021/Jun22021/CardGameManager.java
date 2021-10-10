package Jun22021;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardGameManager extends Remote {
    int register(String name, CardCallback callback) throws RemoteException;

    void unregister(int id) throws RemoteException;

    Card requestCard(Player player) throws RemoteException;

    void passCard(Player player) throws RemoteException;

    void startGame() throws RemoteException;
}
