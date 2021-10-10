package April2021;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VCalcManager extends Remote{
    int sendVCalcRequest(VCalcRequest req) throws RemoteException;

    boolean runNextVCalc() throws RemoteException;
}
