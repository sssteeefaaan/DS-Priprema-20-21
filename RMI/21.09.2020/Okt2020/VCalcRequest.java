package Okt2020;
import java.io.Serializable;
import java.util.Vector;

public class VCalcRequest implements Serializable {
    
    public int cid;
    public Vector<Double> a;
    public Vector<Double> b;
    public VCalcCallback cb;

    public VCalcRequest()
    {
        this.cid = -1;
        this.a = new Vector<>();
        this.b = new Vector<>();
        this.cb = null;
    }

    public VCalcRequest(Vector<Double> a, Vector<Double> b, VCalcCallback cb) {
        this.cid = -1;
        this.a = a;
        this.b = b;
        this.cb = cb;
    }
    
    public VCalcRequest(int cid, Vector<Double> a, Vector<Double> b, VCalcCallback cb) {
        this.cid = cid;
        this.a = a;
        this.b = b;
        this.cb = cb;
    }
}
