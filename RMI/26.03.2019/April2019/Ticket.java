package April2019;

import java.io.Serializable;
import java.util.Vector;

public class Ticket implements Serializable{
    public int id;
    public Vector<Integer> numbers;

    public Ticket() {
        this.numbers = new Vector<>();
        this.id = -1;
    }

    public Ticket(Vector<Integer> numbers) {
        this.numbers = numbers;
        this.id = -1;
    }
    
    public Ticket(int id, Vector<Integer> numbers) {
        this.numbers = numbers;
        this.id = id;
    }
}
