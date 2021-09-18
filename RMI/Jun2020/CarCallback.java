import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CarCallback extends Remote{
    void notifyCar(String address) throws RemoteException;
}
