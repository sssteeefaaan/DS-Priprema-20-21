package Jun22021;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardCallback extends Remote {

    void isNewRound() throws RemoteException;

    void isNewGame() throws RemoteException;

    void isWinner() throws RemoteException;

    void isLoser() throws RemoteException;

    void isEliminated() throws RemoteException;
}
