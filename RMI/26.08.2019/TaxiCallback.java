import java.rmi.RemoteException;

import java.rmi.Remote;

interface TaxiCallback extends Remote {
    void notifyTaxi(String address) throws RemoteException;
}
