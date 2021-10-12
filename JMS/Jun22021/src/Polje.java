
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
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
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
public class Polje {

    private int x;
    private int y;
    private boolean isBomb;
    private int bombsAround;

    public Polje(int x, int y, boolean isBomb) throws NamingException, JMSException {

        this.x = x;
        this.y = y;
        this.isBomb = isBomb;
        this.bombsAround = 0;

        InitialContext ictx = new InitialContext();

        QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("qcf");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ictx.lookup("tcf");

        Queue qClick = (Queue) ictx.lookup("qClick");
        Topic tInfo = (Topic) ictx.lookup("tInfo");
        Queue qInfo = (Queue) ictx.lookup("qInfo");

        ictx.close();

        QueueConnection qc = (QueueConnection) qcf.createConnection();
        TopicConnection tc = (TopicConnection) tcf.createConnection();

        QueueSession qs = (QueueSession) qc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession ts = (TopicSession) tc.createSession(false, Session.AUTO_ACKNOWLEDGE);

        QueueSender qClickSender = qs.createSender(qClick);
        TopicPublisher tInfoSender = ts.createPublisher(tInfo);
        QueueSender qInfoSender = qs.createSender(qInfo);

        qs.createReceiver(qClick, "X = " + this.x + " AND Y = " + this.y)
                .setMessageListener(ml
                        -> {

                    try {
                        Message m = qs.createMessage();
                        m.setIntProperty("ID", ml.getIntProperty("ID"));
                        m.setIntProperty("X", this.x);
                        m.setIntProperty("Y", this.y);
                        m.setBooleanProperty("IsBomb", this.isBomb);
                        m.setIntProperty("BombsAround", this.bombsAround);

                        qClickSender.send(m);
                    } catch (JMSException ex) {
                        Logger.getLogger(Polje.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

        ts.createSubscriber(tInfo, "X IN ('"
                + Integer.toString(this.x - 1) + "', '"
                + Integer.toString(this.x) + "', '"
                + Integer.toString(this.x + 1) + "') AND Y IN('"
                + Integer.toString(this.y - 1) + "', '"
                + Integer.toString(this.y) + "', '"
                + Integer.toString(this.y + 1) + "')", true)
                .setMessageListener(ml
                        -> {
                    try {
                        if (ml.getBooleanProperty("IsBomb")) {
                            this.bombsAround++;
                        }

                        if (this.isBomb) {

                            Message m = ts.createMessage();
                            m.setIntProperty("X", Integer.parseInt(ml.getStringProperty("X")));
                            m.setIntProperty("Y", Integer.parseInt(ml.getStringProperty("Y")));

                            qInfoSender.send(m);
                        }
                    } catch (JMSException ex) {
                        Logger.getLogger(Polje.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

        qs.createReceiver(qInfo, "X = " + this.x + " AND Y = " + this.y)
                .setMessageListener(ml
                        -> {
                    this.bombsAround++;
                });

        qc.start();
        tc.start();

        Message m = ts.createMessage();
        m.setStringProperty("X", Integer.toString(this.x));
        m.setStringProperty("Y", Integer.toString(this.y));
        m.setBooleanProperty("IsBomb", this.isBomb);

        tInfoSender.send(m);
    }

    public static void main(String[] args)
    {
        try{
            Scanner s = new Scanner(System.in);
            
            System.out.print("Unesite x koordinatu polja: ");
            int x = s.nextInt();
            
            System.out.print("Unesite y koordinatu polja: ");
            int y = s.nextInt();
            
            s.nextLine();
            
            System.out.print("Unesite 'bomba' ukoliko se radi o polju koje je bomba: ");
            new Polje(x, y, s.nextLine().trim().toLowerCase().equals("bomba"));
            
            System.out.println("Polje je aktivno!");
            
            s.nextLine();
            s.close();
            
            System.out.close();
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
