import java.io.Serializable;

public class Car implements Serializable{
    private int id;
    private String address;
    private boolean isFree;
    private CarCallback callback;

    public Car(int id, String address, boolean isFree, CarCallback callback)
    {
        this.id = id;
        this.address = address;
        this.isFree = isFree;
        this.callback = callback;
    }

    public boolean isFree() {
        return this.isFree;
    }

    public int getID() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public CarCallback getCallback() {
        return this.callback;
    }

    public void setAddress(String newAddress)
    {
        this.address = newAddress;
    }

    public void setFree(boolean isFree)
    {
        this.isFree = isFree;
    }
}
