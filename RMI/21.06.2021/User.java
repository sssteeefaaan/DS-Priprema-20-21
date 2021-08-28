import java.rmi.RemoteException;
import java.rmi.Remote;

public interface User extends Remote {
    int getId() throws RemoteException;

    String getName() throws RemoteException;

    TagMessageCallback getCallback() throws RemoteException;

    void setCallback(TagMessageCallback callback) throws RemoteException;
}
