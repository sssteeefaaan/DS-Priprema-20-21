package com.Septembar2021;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface BingoManager extends Remote {
    Ticket playTicket(Vector<Integer> numbers, BingoCallback callback) throws RemoteException;

    int drawNumber() throws RemoteException;
}
