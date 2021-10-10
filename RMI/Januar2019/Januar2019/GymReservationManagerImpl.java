package Januar2019;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GymReservationManagerImpl extends UnicastRemoteObject implements GymReservationManager {

    private HashMap<Integer, Reservation> reservations;
    private int idGen;

    public GymReservationManagerImpl() throws RemoteException {
        super();

        this.reservations = new HashMap<>();
        this.idGen = 1;
    }

    @Override
    public Reservation makeReservation(Reservation r) throws RemoteException {
        if (r == null || r.hour + r.numHours > 24 || r.day > 31 || r.month > 12 || r.month < 1 || r.day < 1
                || r.hour < 0 || r.hour > 23 || r.numHours < 0 || r.numHours > 23)
            return null;

        for (Reservation r1 : this.reservations.values()) {
            if (r1.month == r.month && r1.day == r.day)
                if (r1.hour + r1.numHours < r.hour && r.hour + r.numHours > r1.hour)
                    return null;
        }

        r.id = this.idGen++;
        this.reservations.put(r.id, r);

        return r;
    }

    @Override
    public Reservation extendReservation(Reservation r, int extraHours) throws RemoteException {
        if (r == null)
            return null;

        Reservation res = this.reservations.get(r.id);

        if (res == null || res.hour + res.numHours + extraHours > 24)
            return null;

        res.numHours += extraHours;

        return res;
    }

    @Override
    public void cancelReservation(Reservation r) throws RemoteException {
        if (r != null)
            this.reservations.remove(r.id);
    }

}
