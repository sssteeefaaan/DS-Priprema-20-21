
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import javax.jms.*;
import javax.naming.*;

public class Klijent {
    private int ID;
    private HashMap<String, Recept> recepti;
    
    private Queue queue;
//    private Queue queue2;
//    private Queue queue3;
    private Topic topic;
    
    private QueueConnection qc;
    private TopicConnection tc;
    
    private QueueSession qs;
    private TopicSession ts;
    
    private QueueSender sender;
//    private QueueSender sender2;
//    private QueueSender sender3;
    
    private HashMap<String, QueueReceiver> receivers;
    private QueueReceiver receiver2;
    private QueueReceiver receiver3;
    
    private TopicPublisher publisher;
    private TopicSubscriber subscriber;
 
    public Klijent(int ID) throws NamingException, JMSException
    {
        this.ID = ID;
        this.recepti = new HashMap<>();
        this.receivers = new HashMap<>();
        
        InitialContext ic = new InitialContext();
        
        QueueConnectionFactory qcf = (QueueConnectionFactory) ic.lookup("qcf");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ic.lookup("tcf");
        
        this.queue = (Queue) ic.lookup("qSeptembar2019");
//        this.queue2 = (Queue) ic.lookup("q2Septembar2019");
//        this.queue3 = (Queue) ic.lookup("q3Septembar2019");
        this.topic = (Topic) ic.lookup("tSeptembar2019");
        
        ic.close();
        
        this.qc = (QueueConnection) qcf.createConnection();
        this.qs = (QueueSession) this.qc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        this.tc = (TopicConnection) tcf.createConnection();
        this.ts = (TopicSession) this.tc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        this.sender = (QueueSender) this.qs.createSender(this.queue);
//        this.sender2 = (QueueSender) this.qs.createSender(this.queue2);
//        this.sender3 = (QueueSender) this.qs.createSender(this.queue3);

        this.publisher = (TopicPublisher) this.ts.createPublisher(this.topic);
    }
    
    public void start(LinkedList<Recept> recepti) throws JMSException
    {   
        this.subscriber = this.ts.createSubscriber(topic, "", true);
        this.subscriber.setMessageListener(new LoginMessageListener(this));
        
        this.receiver2 = this.qs.createReceiver(this.queue, "IDPrima = " + this.ID);
        this.receiver2.setMessageListener(new ReceiveMessageListener(this));
        
        this.receiver3 = this.qs.createReceiver(queue, "IDLogRes = " + this.ID);
        this.receiver3.setMessageListener(new OnlineMessageListener(this));
        
        for(Recept r : recepti)
            addRecept(r);
        
        this.qc.start();
        this.tc.start();
        
        Message m = this.ts.createMessage();
        m.setIntProperty("IDNovi", this.ID);
        this.publisher.send(m);
    }
    
    public void preuzmiRecept(String nazivRecepta) throws JMSException
    {
        Message m = this.qs.createMessage();
        m.setStringProperty("nazivRecepta", nazivRecepta);
        m.setIntProperty("IDTrazio", this.ID);
        this.sender.send(m);
    }
    
    public void addRecept(Recept r) throws JMSException{
        this.receivers.put(r.naziv, this.qs.createReceiver(this.queue, "nazivRecepta = '" + r.naziv + "' AND NOT IDTrazio = " + this.ID));
        this.receivers.get(r.naziv).setMessageListener(new ReceptMessageListener(this));
        this.recepti.put(r.naziv, r);
    }
    
    public void removeRecept(String naziv) throws JMSException{
        this.receivers.get(naziv).close();
        this.receivers.remove(naziv);
        this.recepti.remove(naziv);
    }
    
    public void close() throws JMSException
    {
        this.qc.close();
        this.tc.close();
    }
    public void print(String s)
    {
        System.out.println(s);
    }
    public int getID() {
        return ID;
    }

    public HashMap<String, Recept> getRecepti() {
        return recepti;
    }

    public QueueSession getQueueSession() {
        return qs;
    }

    public TopicSession getTopicSession() {
        return ts;
    }

    public QueueSender getSender() {
        return sender;
    }

    public TopicPublisher getPublisher() {
        return publisher;
    }
    
    public static void main(String[] args)
    {
        try{
            Scanner s = new Scanner(System.in);
            String input;
            
            System.out.println("Unesite ID");
            Klijent k = new Klijent(Integer.parseInt(s.nextLine().trim()));
            
            LinkedList<Recept> lista = new LinkedList<>();
            lista.add(new Recept("Puding"));
            lista.add(new Recept("Palacinke"));
            lista.add(new Recept("Sutlijas"));
            lista.add(new Recept("Plazma i mleko"));

            k.start(lista);
            
            while(true)
            {
                System.out.println("***** Glavni Menu *****");
                System.out.println("1 - Dodaj novi recept");
                System.out.println("2 - Ukloni recept");
                System.out.println("3 - Potrazi recept");
                System.out.println("4 - Exit");
                
                input = s.nextLine().trim();
                
                if(input.equals("1"))
                {
                    System.out.println("Unesite naziv recepta");
                    k.addRecept(new Recept(s.nextLine().trim()));
                }
                else if(input.equals("2"))
                {
                    System.out.println("Unesite naziv recepta");
                    k.removeRecept(s.nextLine().trim());
                }
                else if(input.equals("3"))
                {
                    System.out.println("Unesite naziv recepta");
                    k.preuzmiRecept(s.nextLine().trim());
                }
                else if(input.equals("4"))
                    break;
                else
                    System.out.println("Unesite broj pored opcije!");
            }
            
            k.close();
            s.close();
            
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
