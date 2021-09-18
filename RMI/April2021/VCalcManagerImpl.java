import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class VCalcManagerImpl extends UnicastRemoteObject implements VCalcManager {

    private LinkedList<VCalcRequest> requests;
    private int ids;
    public VCalcManagerImpl() throws RemoteException
    {
        super();
        this.requests = new LinkedList<>();
        ids = 1;
    }

    @Override
    public int sendVCalcRequest(VCalcRequest req) throws RemoteException {
        if (req.getA() == null || req.getB() == null || req.getA().size() != req.getB().size())
            return -1;
            
        this.requests.push(new VCalcRequest(ids++, req.getA(), req.getB(), req.getCb()));
        this.requests.getFirst().print();
        return ids;
    }

    @Override
    public boolean runNextVCalc() throws RemoteException {
        if(this.requests.size() == 0)
            return false;
        
        VCalcRequest temp = this.requests.pollLast();
        Double res = temp.operate();

        if(res == null)
            return false;

        temp.getCb().onDone(temp.getCId(), res);

        return true;
    }
    
}
