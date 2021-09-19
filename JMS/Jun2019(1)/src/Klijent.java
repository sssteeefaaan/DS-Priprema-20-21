
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
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
public class Klijent {
    
    private final Topic tStigao;
    private final Topic tUKvaru;
    
    private final TopicConnection tc;
    private final TopicSession ts;
    
    private TopicPublisher pStigao;
    private TopicPublisher pUKvaru;
    
    private String lokacija;
    
    public Klijent() throws NamingException, JMSException
    {
        InitialContext ctx = new InitialContext();
        
        TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("tcf");
        
        this.tStigao = (Topic) ctx.lookup("tStigao2019_1");
        this.tUKvaru = (Topic) ctx.lookup("tUKvaru2019_1");
        
        ctx.close();
        
        this.tc = (TopicConnection) tcf.createConnection();
        this.ts = (TopicSession) this.tc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        this.pStigao = this.ts.createPublisher(this.tStigao);
        this.pUKvaru = this.ts.createPublisher(this.tUKvaru);
        
        this.lokacija = null;
    }
    
    public void Pokreni(String lokacija, boolean voz) throws JMSException
    {
        this.lokacija = lokacija;
        if(!voz){
            this.ts.createSubscriber(this.tStigao, "Stanica = '" + lokacija + "'", true)
                .setMessageListener(ml->
                {
            try {
                LinkedList<String> linija = (LinkedList<String>)((ObjectMessage)ml).getObject();
                String trenutnaLokacija = ml.getStringProperty("Lokacija");
                
                if(linija.indexOf(this.lokacija) >= linija.indexOf(trenutnaLokacija))
                    System.out.println("Voz '" + ml.getStringProperty("NazivVoza") + "' se nalazi na lokaciji: '" + trenutnaLokacija + "' i ima: " + ml.getIntProperty("BrojPutnika") + " putnika!");
            
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
                });
            
            this.ts.createSubscriber(this.tUKvaru).setMessageListener(ml->
            {
                try {
                    System.out.println("Voz '" + ml.getStringProperty("NazivVoza") + "' se pokvario na lokaciji '" + ml.getStringProperty("Lokacija") + "'!");
                } catch (JMSException ex) {
                    Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        this.tc.start();
    }
    
    public void Stigao(String nazivVoza, LinkedList<String> stanice, int brojPutnika) throws JMSException
    {
        ObjectMessage msg = this.ts.createObjectMessage();
        msg.setObject(stanice);
        msg.setStringProperty("Lokacija", this.lokacija);
        msg.setStringProperty("NazivVoza", nazivVoza);
        msg.setIntProperty("BrojPutnika", brojPutnika);
        
        for(String s : stanice)
        {
            msg.setStringProperty("Stanica", s);
            this.pStigao.send(msg);
        }
    }
    
    public void Ukvaru(String nazivVoza) throws JMSException
    {
        Message m = this.ts.createMessage();
        m.setStringProperty("NazivVoza", nazivVoza);
        m.setStringProperty("Lokacija", this.lokacija);
        
        this.pUKvaru.send(m);
    }
    
    public static void main(String[] args)
    {
        try{
            Scanner s = new Scanner(System.in);
            
            System.out.print("Klijent je voz/stanica? ");
            String temp = s.nextLine().trim();
            
            System.out.print("Unesite trenutnu lokaciju: ");
            
            Klijent k = new Klijent();
            
            if(temp.equals("voz"))
            {
                k.Pokreni(s.nextLine().trim(), true);
                LinkedList<String> lokacije = new LinkedList<>();
                
                System.out.println("Unesite stanice na liniji ili kraj");
                while(true)
                {
                    temp = s.nextLine().trim();
                    if(temp.equals("kraj"))
                        break;
                    
                    lokacije.add(temp);
                }
                
                System.out.println("Unesite naziv voza i njegov broj putnika");
                k.Stigao(s.nextLine().trim(), lokacije, Integer.parseInt(s.nextLine().trim()));
                
                System.out.print("Unesite naziv voza koji se pokvario: ");
                k.Ukvaru(s.nextLine().trim());
            }
            else
                k.Pokreni(s.nextLine().trim(), false);
            
            s.nextLine();
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
