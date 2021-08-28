import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TagMessage implements Serializable {
    public User user;
    public String msg;
    public List<String> tags;

    public TagMessage(User user, String msg, List<String> tags) {
        super();
        this.user = user;
        this.msg = msg;
        this.tags = new ArrayList<>();
        for (String s : tags)
            this.tags.add(s);
    }
}
