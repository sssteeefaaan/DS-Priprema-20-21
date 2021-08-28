import java.rmi.RemoteException;
import java.rmi.Remote;

interface Taxi extends Remote {
    int getId() throws RemoteException;

    String getAddress() throws RemoteException;

    boolean isFree() throws RemoteException;

    void setStatus(boolean isFree) throws RemoteException;

    void setCallback(TaxiCallback callback) throws RemoteException;

    TaxiCallback getCallback() throws RemoteException;
}