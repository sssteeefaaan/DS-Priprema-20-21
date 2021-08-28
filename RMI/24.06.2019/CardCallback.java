import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardCallback extends Remote {
    void isWinner() throws RemoteException;

    void message(String message) throws RemoteException;
}
