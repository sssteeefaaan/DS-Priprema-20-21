import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VCalcCallback extends Remote{
    void onDone(int cid, double result) throws RemoteException;
}
