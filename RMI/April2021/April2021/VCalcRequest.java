package April2021;

import java.io.Serializable;
import java.util.Vector;

public class VCalcRequest implements Serializable {
    public int cId;
    public Vector<Double> a;
    public Vector<Double> b;
    public VCalcCallback cb;

    public VCalcRequest(int cId, Vector<Double> a, Vector<Double> b, VCalcCallback cb) {
        super();
        
        this.cId = cId;
        this.a = new Vector<Double>(a);
        this.b = new Vector<Double>(b);
        this.cb = cb;
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