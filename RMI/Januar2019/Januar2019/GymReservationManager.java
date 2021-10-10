package Januar2019;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface GymReservationManager extends Remote {
    Reservation makeReservation(Reservation r) throws RemoteException;

    Reservation extendReservation(Reservation r, int extraHours) throws RemoteException;

    void cancelReservation(Reservation r) throws RemoteException;
}
