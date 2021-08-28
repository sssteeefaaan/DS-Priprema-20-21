import java.rmi.Remote;
import java.rmi.RemoteException;

interface TaxiManager extends Remote {
    boolean requestTaxi(String address) throws RemoteException;

    void setTaxiStatus(int id, boolean isFree) throws RemoteException;

    boolean register(int id, Taxi newTaxi) throws RemoteException;

    boolean unregister(int id) throws RemoteException;
}
