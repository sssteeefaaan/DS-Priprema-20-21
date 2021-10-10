package Oktobar2020;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface VCalcManager extends Remote{
    int sendVCalcRequest(VCalcRequest request) throws RemoteException;

    boolean runNextVCalc() throws RemoteException;
}
