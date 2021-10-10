package Jun22021;

import java.io.Serializable;

public class Card implements Serializable {

    public String color;
    public String suit;
    public int value;

    public Card(String color, String suit, int value) {
        super();

        this.color = color;
        this.suit = suit;
        this.value = value;
    }
}
