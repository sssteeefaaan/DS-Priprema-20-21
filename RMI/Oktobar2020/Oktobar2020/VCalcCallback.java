package Oktobar2020;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VCalcCallback extends Remote{
    void onDone(int cId, double result) throws RemoteException;
}
