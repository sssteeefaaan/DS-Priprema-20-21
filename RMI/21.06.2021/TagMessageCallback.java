import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TagMessageCallback extends Remote{
    void onTagMessage(TagMessage msg, String tag) throws RemoteException;
}
