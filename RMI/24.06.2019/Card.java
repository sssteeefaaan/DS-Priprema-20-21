import java.io.Serializable;

public class Card implements Serializable {
    public String color;
    public int value;

    public Card(String color, int  value) {
        super();
        this.color = color;
        this.value = value;
    }
}
