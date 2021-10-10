package Januar2019;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

public class GymReservationClient {

    private GymReservationManager manager;
    private HashMap<Integer, Reservation> myReservations;

    public GymReservationClient() throws MalformedURLException, RemoteException, NotBoundException {
        this.manager = (GymReservationManager) Naming.lookup("rmi://127.0.0.1:1099/GymReservationService");
        this.myReservations = new HashMap<>();
    }

    public void makeReservation(int month, int day, int hour, int numHours) throws RemoteException {
        Reservation res = this.manager.makeReservation(new Reservation(-1, day, month, hour, numHours));
        if (res != null) {
            System.out.println("Uspesno izvrsena rezervacija!");
            this.myReservations.put(res.id, res);
        } else
            System.out.println("Neuspesno izvrsena rezervacija!");
    }
    
    public void extendReservation(int id, int extraHours) throws RemoteException {
        Reservation res = this.myReservations.get(id);

        if(res == null)
        {
            System.out.println("Rezervacija sa tim ID-em ne postoji!");
            return;
        }

        res = this.manager.extendReservation(res, extraHours);

        if(res != null)
        {
            System.out.println("Uspesno produzenje registracije!");
            this.myReservations.replace(id, res);
        }
        else
            System.out.println("Neuspesno produzenje rezervacije!");
    }

    public void cancelReservation(int id) throws RemoteException {
        if (!this.myReservations.containsKey(id)) {
            System.out.println("Rezervacija sa tim ID-em ne postoji!");
            return;
        }

        this.manager.cancelReservation(this.myReservations.remove(id));
        System.out.println("Uspesno ponistenje rezervacije!");
    }
    
    // Mrzi me dalje....
    public static void main(String[] args)
    {
        try{

        }
        catch(Exception e)
        {

        }
    }
}
