import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface LotoManager extends Remote{
    Ticket playTicket(Vector<Integer> numbers) throws RemoteException;

    Vector<Integer> getWinners() throws RemoteException;

    void drawNumbers() throws RemoteException;
}
