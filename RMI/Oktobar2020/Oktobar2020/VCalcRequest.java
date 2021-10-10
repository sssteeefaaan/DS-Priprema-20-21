package Oktobar2020;

import java.io.Serializable;
import java.util.Vector;

public class VCalcRequest implements Serializable {
    public int cId;
    public Vector<Double> a;
    public Vector<Double> b;
    public VCalcCallback callback;

    public VCalcRequest(int cId, Vector<Double> a, Vector<Double> b, VCalcCallback callback) {
        super();

        this.cId = cId;
        this.a = new Vector<>(a);
        this.b = new Vector<>(b);
        this.callback = callback;
    }

    public double runCalc() {
        double rez = 0;
        
        for (int i = 0; i < a.size(); i++)
            rez += a.get(i) * b.get(i);

        return rez;
    }
}
