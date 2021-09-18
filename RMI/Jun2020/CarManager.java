import java.rmi.RemoteException;
import java.rmi.Remote;

public interface CarManager extends Remote {
    boolean requestCar(String address) throws RemoteException;

    int register(Car c) throws RemoteException;
}