import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

public class VCalcRequest implements Serializable {
    private int cId;
    private Vector<Double> a;
    private Vector<Double> b;
    private VCalcCallback cb;

    public VCalcRequest(int cId, Vector<Double> a, Vector<Double> b, VCalcCallback cb) {
        this.cId = cId;
        this.a = new Vector<Double>(a);
        this.b = new Vector<Double>(b);
        this.setCb(cb);
    }

    public VCalcCallback getCb() {
        return cb;
    }

    public void setCb(VCalcCallback cb) {
        this.cb = cb;
    }

    public Vector<Double> getB() {
        return b;
    }

    public Vector<Double> getA() {
        return a;
    }

    public int getCId() throws RemoteException {
        return this.cId;
    }

    public Double operate() {
        if (this.a == null || this.b == null || this.a.size() != this.b.size())
            return null;

        Double res = 0.0;
        for (int i = 0; i < this.a.size(); i++)
            res += this.a.get(i) * this.b.get(i);

        return res;
    }

    public void print()
    {
        System.out.printf("CID: %d\n", this.cId);

        System.out.printf("a: [");
        for(double d : this.a)
            System.out.printf("\t%f", d);
        System.out.printf("\t]\n");

        System.out.printf("b: [");
        for(double d : this.b)
            System.out.printf("\t%f", d);
        System.out.printf("\t]\n");
    }

}