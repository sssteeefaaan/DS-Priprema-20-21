import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class CarManagerImpl extends UnicastRemoteObject implements CarManager {
    
    private LinkedList<Car> cars;
    private LinkedList<String> clientQueue;
    private int capacity;
    private int idGen;

    public CarManagerImpl(int capacity) throws RemoteException
    {
        super();

        this.cars = new LinkedList<>();
        this.clientQueue = new LinkedList<>();
        this.capacity = capacity;
        this.idGen = 1;
    }

    @Override
    public int register(Car car) throws RemoteException
    {
        if (car.callback == null)
            return -1;
        
        car.id = idGen++;
        this.cars.addLast(car);

        if (car.isFree && this.clientQueue.size() > 0)
        {
            String address = this.clientQueue.pollFirst();
            car.isFree = false;
            car.address = address;
            car.callback.notifyCar(address);
        }

        return car.id;
    }

    @Override
    public boolean requestCar(String address) throws RemoteException
    {
        Car c = null;

        // U listi je zadnji element sa najvecim ID-em,
        // tako da ukoliko se desi vise jednakih roundNumb,
        // prethodni ce biti zamanjen trenutnim, odnosno onim koji ima veci ID
        for (Car car : this.cars)
            if(car.isFree)
                if(c == null || c.roundNumb >= car.roundNumb)
                    c = car;

        if (c != null)
        {
            c.address = address;
            c.isFree = false;
            c.callback.notifyCar(address);

            return true;
        }
        else if (this.clientQueue.size() < this.capacity)
        {
            this.clientQueue.addLast(address);
            return true;
        }

        return false;
    }
}
