package Jun22021;

import java.io.Serializable;
import java.util.LinkedList;

public class Player implements Serializable {

    public int id;
    public String name;
    public int points;
    public LinkedList<Card> pickedCards;
    public CardCallback callback;

    public Player(int id, String name, int points, CardCallback callback) {
        super();

        this.id = id;
        this.name = name;
        this.points = points;
        this.callback = callback;
        this.pickedCards = new LinkedList<>();
    }
}
