
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public class Igrac {

    private QueueSession qs;
    private QueueSender qClickSender;
    private int ID;

    public Igrac(int ID) throws NamingException, JMSException {
        this.ID = ID;

        InitialContext ictx = new InitialContext();

        QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("qcf");

        Queue qClick = (Queue) ictx.lookup("qClick");

        ictx.close();

        QueueConnection qc = (QueueConnection) qcf.createConnection();
        this.qs = (QueueSession) qc.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.qClickSender = qs.createSender(qClick);

        qs.createReceiver(qClick, "ID = " + this.ID).setMessageListener(ml
                -> {
            try {
                int x = ml.getIntProperty("X"),
                        y = ml.getIntProperty("Y");
                boolean bomb = ml.getBooleanProperty("IsBomb");
                
                if (bomb) {
                    System.out.println("Polje (" + x + ", " + y + ") je bomba!");
                } else {
                    int bombsAround = ml.getIntProperty("BombsAround");
                    System.out.println("Polje (" + x + ", " + y + ") oko sebe ima broj bombi: " + bombsAround);
                }
            } catch (JMSException ex) {
                Logger.getLogger(Igrac.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        qc.start();
    }

    public void click(int x, int y) throws JMSException {
        Message m = this.qs.createMessage();
        m.setIntProperty("ID", this.ID);
        m.setIntProperty("X", x);
        m.setIntProperty("Y", y);

        this.qClickSender.send(m);
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.print("Unesite ID igraca: ");
            Igrac ig = new Igrac(s.nextInt());
            s.nextLine();

            boolean loop = true;
            while (loop) {
                System.out.println("***** Izaberite opciju *****");
                System.out.println("1 - Otvorite polje");
                System.out.println("2 - Kraj igre");

                switch (s.nextLine().trim()) {
                    case ("1"):
                        int x, y;
                        
                        System.out.print("Unesite x koordinatu polja: ");
                        x = s.nextInt();
                        
                        System.out.print("Unesite y koordinatu polja: ");
                        y = s.nextInt();
                        
                        s.nextLine();
                        
                        ig.click(x, y);
                        break;
                    case ("2"):
                        loop = false;
                        break;
                    default:
                        System.out.println("Unesite redni broj pored opcije!");
                        break;
                }
            }

            s.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
