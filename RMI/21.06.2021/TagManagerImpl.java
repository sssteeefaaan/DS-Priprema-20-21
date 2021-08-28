import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class TagManagerImpl extends UnicastRemoteObject implements TagManager {

    private HashMap<String, HashMap<Integer, User>> followedTags;

    public TagManagerImpl() throws RemoteException{
        super();
        this.followedTags = new HashMap<>();
    }

    @Override
    public void sendMessage(TagMessage msg) throws RemoteException {
        HashMap<Integer, User> notify = new HashMap<>();

        for (String tag : msg.tags) {
            for (User u : this.followedTags.get(tag).values()) {
                if (!notify.containsKey(u.getId()) && u.getId() != msg.user.getId()) {
                    notify.put(u.getId(), u);
                    u.getCallback().onTagMessage(msg, tag);
                }
            }
        }
    }

    @Override
    public void follow(User user, String tag, TagMessageCallback cb) throws RemoteException {
        if (user.getCallback() == null)
            user.setCallback(cb);

        HashMap<Integer, User> followers = this.followedTags.get(tag);

        if (followers == null)
            this.followedTags.put(tag, followers = new HashMap<>());
        
        if (!followers.containsKey(user.getId()))
            followers.put(user.getId(), user);
    }

}
