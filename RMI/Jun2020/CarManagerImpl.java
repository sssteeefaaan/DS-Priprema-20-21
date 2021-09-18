import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class CarManagerImpl extends UnicastRemoteObject implements CarManager {

    private LinkedList<Car> vozila;
    private LinkedList<String> klijenti;
    private int capacity;

    public CarManagerImpl() throws RemoteException {
        vozila = new LinkedList<>();
        klijenti = new LinkedList<>();
        capacity = 5;
    }

    public boolean requestCar(String address) throws RemoteException {
        Car slobodno = findFreeCarRR();

        if (slobodno != null) {
            slobodno.setFree(false);
            slobodno.getCallback().notifyCar(address);
            return true;
        } else if (klijenti.size() < capacity) {
            klijenti.addLast(address);
            return true;
        }

        return false;
    }
    
    public int register(Car c) throws RemoteException {
        int ret = vozila.size();

        vozila.addLast(new Car(ret, c.getAddress(), c.isFree(), c.getCallback()));

        setWork(vozila.getLast());

        return ret;
    }

    private void setWork(Car c) throws RemoteException
    {
        if (c.isFree()) {
            String address = klijenti.pollFirst();
            if (address != null)
            {
                c.setFree(false);
                c.getCallback().notifyCar(address);
            }
        }
    }

    private Car findFreeCarRR() {
        Car ret = null;

        for (Car c : vozila) {
            if (c.isFree()) {
                ret = c;
                roundRobin(ret);
                break;
            }
        }

        return ret;
    }

    private void roundRobin(Car c)
    {
        vozila.remove(c);
        vozila.addLast(c);
    }
}
