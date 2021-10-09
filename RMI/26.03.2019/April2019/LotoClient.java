package April2019;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Vector;

public class LotoClient {
    private LotoManager proxy;

    public LotoClient() throws MalformedURLException, RemoteException, NotBoundException {
        proxy = (LotoManager) Naming.lookup("rmi://127.0.0.1:1099/LotoService");
    }

    public void playTicket(Vector<Integer> numbers) throws RemoteException {
        Ticket t = proxy.playTicket(numbers);
        if (t != null)
            System.out.println("Ticket ima ID: " + t.id);
        else
            System.out.println("Ticket nije odigran!");
    }

    public void getWinners() throws RemoteException {
        Vector<Integer> winnerTickets = proxy.getWinners();
        if (winnerTickets != null) {
            System.out.print("Pobednicki tiketi: [");
            for (int id : winnerTickets)
                System.out.print("\t" + id);
            System.out.println("]");
        }
        else
            System.out.println("Izvlacenje nije zavrseno!");
    }

    public void drawNumbers() throws RemoteException {
        this.proxy.drawNumbers();
    }
    
    public static void main(String[] args)
    {
        try{
            LotoClient klijent = new LotoClient();
            Scanner s = new Scanner(System.in);
            String input;

            while (true)
            {
                System.out.println("***** Main Menu *****");
                System.out.println("1 - Play Ticket");
                System.out.println("2 - Draw Numbers");
                System.out.println("3 - Get Winners");
                System.out.println("4 - Exit");

                input = s.nextLine().trim();

                if(input.equals("1"))
                {
                    System.out.println("Unesite 7 brojeva u opsegu (0 - 40) odnosno [1 - 39]:");
                    Vector<Integer> temp = new Vector<>(7);
                    for(int i = 0 ; i < 7; i++)
                        temp.add(s.nextInt());
                    s.nextLine();
                    klijent.playTicket(temp);
                }
                else if(input.equals("2"))
                {
                    klijent.drawNumbers();
                }
                else if(input.equals("3"))
                {
                    klijent.getWinners();
                }
                else if(input.equals("4"))
                {
                    break;
                }
                else
                {
                    System.out.println("Unesite redni broj pored opcije");
                }
            }

            s.close();
            System.exit(0);
        }catch(Exception e)
        {e.printStackTrace();}
    }
}
