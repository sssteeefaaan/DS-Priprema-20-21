package April2021;

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
        if (req == null || req.cb == null || req.a == null || req.b == null || req.a.size() != req.b.size())
            return -1;
            
        this.requests.addLast(new VCalcRequest(this.ids, req.a, req.b, req.cb));
        return this.ids++;
    }

    @Override
    public boolean runNextVCalc() throws RemoteException {
        if(this.requests.size() == 0)
            return false;
        
        VCalcRequest temp = this.requests.pollFirst();
        Double res = temp.operate();

        if(res == null)
            return false;

        temp.cb.onDone(temp.cId, res);

        return true;
    }
    
}
