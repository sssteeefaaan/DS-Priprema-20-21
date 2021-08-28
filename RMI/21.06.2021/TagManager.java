import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TagManager extends Remote{
    void sendMessage(TagMessage msg) throws RemoteException;

    void follow(User user, String tag, TagMessageCallback cb) throws RemoteException;
}
