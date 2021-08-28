
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

public class Klijent {

    private Topic topic;
    private TopicConnection tc;
    private TopicSession ts;
    private TopicPublisher publisher;
    private LinkedList<TopicSubscriber> subscribed;

    public Klijent() throws NamingException, JMSException {
        InitialContext ic = new InitialContext();

        TopicConnectionFactory tcf = (TopicConnectionFactory) ic.lookup("tcf");
        this.topic = (Topic) ic.lookup("tJun2021");

        ic.close();

        this.tc = tcf.createTopicConnection();
        this.ts = this.tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        this.publisher = this.ts.createPublisher(this.topic);
        this.subscribed = new LinkedList<>();
    }

    public void start(List<Prodavnica> prodavnice) throws JMSException {
        for (Prodavnica p : prodavnice) {
            this.subscribed.add(this.ts.createSubscriber(this.topic, "Naziv = '" + p.naziv + "' AND Popust >= " + p.popust, true));
            this.subscribed.getLast().setMessageListener(ml
                    -> {
                try {
                    String naziv = ml.getStringProperty("Naziv");
                    int popust = ml.getIntProperty("Popust");

                    System.out.println("RASPRODAJA u prodavnici '" + naziv + "' sa popustom " + popust + "%!");

                } catch (JMSException ex) {
                    Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        this.tc.start();
        System.out.println("Klijent osluskuje popuste za prosledjene prodavnice...");
    }
    
    public void stop() throws JMSException
    {
        for(TopicSubscriber sub : this.subscribed)
            sub.close();
        
        this.ts.close();
        this.tc.close();
    }

    public void rasprodaja(Prodavnica p) throws JMSException {
        Message m = this.ts.createMessage();
        m.setStringProperty("Naziv", p.naziv);
        m.setIntProperty("Popust", p.popust);

        this.publisher.send(m);
    }

    public static void main(String[] args) {
        try {

            Klijent k = new Klijent();
            Scanner s = new Scanner(System.in);

            String input1, input2;
            List<Prodavnica> lista = new LinkedList<>();

            while (true) {
                System.out.println("Unesite naziv prodavnice, a zatim popust za koji ste zainteresovani ili kraj");
                input1 = s.nextLine().trim();
                if (input1.equals("kraj")) {
                    break;
                }
                input2 = s.nextLine().trim();
                
                lista.add(new Prodavnica(input1, Integer.parseInt(input2)));
            }
            
            k.start(lista);
            
            while(true)
            {
                System.out.println("Unesite naziv prodavnice, a zatim popust RASPRODAJE ili kraj");
                input1 = s.nextLine().trim();
                if (input1.equals("kraj")) {
                    break;
                }
                input2 = s.nextLine().trim();
                
                k.rasprodaja(new Prodavnica(input1, Integer.parseInt(input2)));
            }
            
            k.stop();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
