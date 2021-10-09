import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CarManager extends Remote{
    boolean requestCar(String address) throws RemoteException;
    int register(Car car) throws RemoteException;
}
