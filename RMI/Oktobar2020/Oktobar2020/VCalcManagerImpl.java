package Oktobar2020;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class VCalcManagerImpl extends UnicastRemoteObject implements VCalcManager {

    private LinkedList<VCalcRequest> requests;
    private int idGen;

    public VCalcManagerImpl() throws RemoteException {
        super();

        this.requests = new LinkedList<>();
        this.idGen = 1;
    }

    @Override
    public int sendVCalcRequest(VCalcRequest request) throws RemoteException {
        if (request == null || request.a == null || request.b == null || request.callback == null
                || request.a.size() != request.b.size())
            return -1;

        this.requests.addLast(new VCalcRequest(this.idGen, request.a, request.b, request.callback));
        return this.idGen++;
    }

    @Override
    public boolean runNextVCalc() throws RemoteException {
        if (this.requests.size() == 0)
            return false;

        VCalcRequest req = this.requests.pollFirst();
        req.callback.onDone(req.cId, req.runCalc());

        return true;
    }

}
