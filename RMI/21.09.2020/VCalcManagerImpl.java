import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class VCalcManagerImpl extends UnicastRemoteObject implements VCalcManager {

    private LinkedList<VCalcRequest> queue;
    
    public VCalcManagerImpl() throws RemoteException
    {
        super();
        this.queue = new LinkedList<>();
    }

    @Override
    public int sendVCalcRequest(VCalcRequest req) throws RemoteException {
        this.queue.addLast(req);
        return req.cid = this.queue.size();
    }

    @Override
    public boolean runNextVCalc() throws RemoteException {
        if(this.queue.size() > 0)
        {
            VCalcRequest req = this.queue.removeFirst();
            int size = req.a.size();
            if (size != req.b.size())
                return false;

            double rez = 0;
            for (int i = 0; i < size; i++)
                rez += req.a.get(i) * req.b.get(i);

            req.cb.onDone(req.cid, rez);
            return true;
        }
        return false;
    }
    
}
