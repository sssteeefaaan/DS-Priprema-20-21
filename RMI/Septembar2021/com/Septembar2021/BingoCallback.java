package com.Septembar2021;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface BingoCallback extends Remote {
    void isWinner(Ticket t) throws RemoteException;

    void isNotWinner(Ticket t) throws RemoteException;
}
