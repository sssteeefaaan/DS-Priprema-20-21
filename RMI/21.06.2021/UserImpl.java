import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserImpl extends UnicastRemoteObject implements User {
    private int id;
    private String name;
    private TagMessageCallback callback;

    public UserImpl(int id, String name) throws RemoteException {
        super();
        this.id = id;
        this.name = name;
        this.callback = null;
    }

    @Override
    public int getId() throws RemoteException {
        return this.id;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public TagMessageCallback getCallback() throws RemoteException {
        return this.callback;
    }

    @Override
    public void setCallback(TagMessageCallback callback) throws RemoteException {
        this.callback = callback;
    }
}
