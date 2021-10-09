import java.io.Serializable;

public class Car implements Serializable {
    public int id;
    public String address;
    public boolean isFree;
    public int roundNumb;
    public CarCallback callback;

    public Car(CarCallback callback)
    {
        super();

        this.id = -1;
        this.address = "";
        this.isFree = true;
        this.roundNumb = 0;
        this.callback = callback;
    }

    // Ovo dole nije neophodno
    public Car(int id, String address, boolean isFree, int roundNumb, CarCallback callback)
    {
        super();
        this.id = id;
        this.address = address;
        this.isFree = isFree;
        this.roundNumb = roundNumb;
        this.callback = callback;
    }

    public Car()
    {
        super();
        this.id = -1;
        this.address = "";
        this.isFree = true;
        this.roundNumb = 0;
        this.callback = null;
    }
}