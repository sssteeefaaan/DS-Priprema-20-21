import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class TaxiImpl extends UnicastRemoteObject implements Taxi {
    private int id;
    private String address;
    private boolean isFree;
    private TaxiCallback callback;

    public TaxiImpl(int id, String address, boolean isFree, TaxiCallback callback) throws RemoteException {
        super();
        this.id = id;
        this.address = address;
        this.isFree = isFree;
        this.callback = callback;
    }

    @Override
    public int getId() throws RemoteException {
        return this.id;
    }

    @Override
    public String getAddress() throws RemoteException {
        return this.address;
    }

    @Override
    public boolean isFree() throws RemoteException {
        return this.isFree;
    }

    @Override
    public void setStatus(boolean isFree) throws RemoteException {
        this.isFree = isFree;
    }

    @Override
    public void setCallback(TaxiCallback callback) throws RemoteException {
        this.callback = callback;
    }

    @Override
    public TaxiCallback getCallback() throws RemoteException {
        return this.callback;
    }
}
