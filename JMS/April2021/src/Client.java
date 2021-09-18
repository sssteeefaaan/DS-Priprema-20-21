
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Queue;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public class Client {

    private final int ID;
    private final Topic tID;
    private final Queue qVG;
    private final TopicConnection tc;
    private final QueueConnection qc;
    private final QueueSession qs;
    private final TopicSession ts;
    private final Queue qID;
    private final QueueSender qIDSender;
    private final QueueSender qVGSender;
    private final HashMap<String, QueueReceiver> receivers;

    public Client(int ID) throws NamingException, JMSException {
        this.ID = ID;
        this.receivers = new HashMap<>();

        
        // Hvatanje komunikacionih kanala
        InitialContext ctx = new InitialContext();

        TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("tcf");
        QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("qcf");

        this.tID = (Topic) ctx.lookup("tIDApril2021");
        this.qID = (Queue) ctx.lookup("qIDApril2021");
        this.qVG = (Queue) ctx.lookup("qVGApril2021");

        ctx.close();
        //

        // Kreiranje konekcija
        this.tc = tcf.createTopicConnection();
        this.qc = qcf.createQueueConnection();
        //

        // Kreiranje sesija iz konekcija
        this.ts = this.tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        this.qs = this.qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        //
        
        // Kreiranje sendera/publishera
        this.qIDSender = this.qs.createSender(this.qID);
        this.qVGSender = this.qs.createSender(this.qVG);
        //
        
        // Kreiranje nekih receivera/subscribera
        this.ts.createSubscriber(this.tID, "", true)
                .setMessageListener(ml
                        -> {
                    try {

                        int newID = ml.getIntProperty("ID");
                        System.out.println("Novi korisnik [" + newID + "]");

                        Message m = this.ts.createMessage();
                        m.setIntProperty("ID", newID);
                        m.setIntProperty("OnlineID", this.ID);

                        this.qIDSender.send(m);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        this.qs.createReceiver(this.qID, "ID = " + this.ID)
                .setMessageListener(ml
                        -> {
                    try {
                        int onlineID = ml.getIntProperty("OnlineID");
                        System.out.println("Korisnik [" + onlineID + "] je online!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        this.qs.createReceiver(this.qVG, "TrazioID = " + this.ID)
                .setMessageListener(ml
                        -> {
            try {
                int pomogaoID = ml.getIntProperty("PomogaoID");
                String igrica = ((TextMessage)ml).getText();
                
                System.out.println("Korisnik [" + pomogaoID + "] vam je poslao kopiju igrice '" + igrica + "'");
            } catch (JMSException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
                });
        //
        
        // Pokreatnje konekcije, BITNO i mora da se pokrene nakon postavljanja receivera/subscribera (onih bitnih)
        // ako se ne odradi, receiveri/subscriberi ne funkcionisu
        this.qc.start();
        this.tc.start();
        //
        
        Message m = this.ts.createMessage();
        m.setIntProperty("ID", this.ID);
        this.ts.createPublisher(this.tID).send(m);
    }

    public void setUp(List<String> videoGames) throws JMSException {
        for (String s : videoGames) {
            this.receivers.put(s, this.qs.createReceiver(this.qVG, "VideoGameName = '" + s + "' AND NOT TraziID = " + this.ID));
            this.receivers.get(s).setMessageListener(ml
                    -> {
                try {
                    int traziID = ml.getIntProperty("TraziID");
                    System.out.println("Korisnik [" + traziID + "] je u potrazi za igricom '" + s + "'");
                    
                    TextMessage m = this.qs.createTextMessage();
                    m.setIntProperty("PomogaoID", this.ID);
                    m.setIntProperty("TrazioID", traziID);
                    m.setText(s);
                    
                    this.qVGSender.send(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    public void download(String videoGameName) throws JMSException
    {
        Message m = this.qs.createMessage();
        m.setStringProperty("VideoGameName", videoGameName);
        m.setIntProperty("TraziID", this.ID);
        
        this.qVGSender.send(m);
    }
    
    public static void main(String[] args)
    {
        try{
            Scanner s  = new Scanner(System.in);
            System.out.println("Unesite ID klijenta!");
            String temp = s.nextLine().trim();
            
            Client k = new Client(Integer.parseInt(temp));
            
            List<String> igre = new ArrayList();
            System.out.println("Unesite imena igrica koje zelite da delite/kraj:");
            while(true)
            {
                temp = s.nextLine().trim();
                if(temp.toLowerCase().equals("kraj"))
                    break;
                
                igre.add(temp);
            }
            
            k.setUp(igre);
            
            while(true)
            {
                System.out.println("Unesite ime igrice koje zelite da skinete/kraj:");
                temp = s.nextLine().trim();
                if(temp.toLowerCase().equals("kraj"))
                    break;
                
                k.download(temp);
            }
            
            s.close();
            
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
