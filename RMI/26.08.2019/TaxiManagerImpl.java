import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;

public class TaxiManagerImpl extends UnicastRemoteObject implements TaxiManager {

    private HashMap<Integer, Taxi> taxies;
    private LinkedList<Taxi> freeTaxies;
    private LinkedList<String> clients;
    private int cap;

    public TaxiManagerImpl(int cap) throws RemoteException {
        super();
        this.taxies = new HashMap<>();
        freeTaxies = new LinkedList<>();
        this.clients = new LinkedList<>();
        this.cap = cap;
    }

    @Override
    public boolean requestTaxi(String address) throws RemoteException {
        if (freeTaxies.isEmpty()) {
            if (this.clients.size() == this.cap)
                return false;

            this.clients.add(address);

        } else {
            this.freeTaxies.poll().getCallback().notifyTaxi(address);
        }

        return true;
    }

    @Override
    public void setTaxiStatus(int id, boolean isFree) throws RemoteException {
        if (taxies.containsKey(id)) {
            Taxi temp = taxies.get(id);
            if (isFree) {
                if (!this.clients.isEmpty()) {
                    temp.getCallback().notifyTaxi(this.clients.pop());
                    freeTaxies.remove(temp);
                } else {
                    temp.setStatus(isFree);
                    freeTaxies.push(temp);
                }
            } else {
                temp.setStatus(isFree);
                freeTaxies.remove(temp);
            }
        }
    }

    @Override
    public boolean register(int id, Taxi newTaxi) throws RemoteException {
        if (taxies.containsKey(id))
            return false;

        this.taxies.put(id, newTaxi);
        if (newTaxi.isFree()) {
            if (this.clients.isEmpty())
                freeTaxies.push(newTaxi);
            else
                newTaxi.getCallback().notifyTaxi(this.clients.pop());
        }
        return true;
    }

    @Override
    public boolean unregister(int id) throws RemoteException {
        if (!taxies.containsKey(id))
            return false;

        Taxi temp = this.taxies.remove(id);
        freeTaxies.remove(temp);
        return true;
    }

}
