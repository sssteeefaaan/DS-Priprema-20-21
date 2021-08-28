
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
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


public class Stanica
{
	private String naziv; 	//ovo je lokacija stanice
	
	private Topic tStigao;
	private Topic tKvar;
	
	private TopicConnection tc;
	private TopicSession ts;
	
	private TopicPublisher senderStigao;
	private TopicPublisher senderKvar;
	
	private TopicSubscriber subStigao;
	private TopicSubscriber subKvar;
	
	
	public Stanica(String naziv) throws NamingException, JMSException
	{
		this.naziv = naziv;
		
		InitialContext ictx = new InitialContext();
		
		TopicConnectionFactory tcf = (TopicConnectionFactory) ictx.lookup("tcfStanica");
		
		tStigao = (Topic) ictx.lookup("tStigao");
		tKvar = (Topic) ictx.lookup("tKvar");
		
		ictx.close();
		
		tc = (TopicConnection) tcf.createConnection();
		ts = (TopicSession) tc.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		senderStigao = ts.createPublisher(tStigao);
		senderKvar = ts.createPublisher(tKvar);
		
		subStigao = ts.createSubscriber(tStigao, "LinijaStanica LIKE '%" + this.naziv + "%'", false);
		subStigao.setMessageListener(new MessageListener(){
			@Override
			public void onMessage(Message msg)
			{
				TextMessage txt = (TextMessage)msg;
				String linija;
                            try {
                                linija = txt.getStringProperty("LinijaStanica");
                            
				String trenutnaLokacija = txt.getText();
				
				List<String> stanice = Arrays.asList(linija.split(","));
				
				System.out.println("Voz je stigao u stanicu " + trenutnaLokacija);
				if(stanice.indexOf(naziv) >= stanice.indexOf(trenutnaLokacija))
					System.out.println(txt.getIntProperty("BrojPutnika"));
                                
                                } catch (JMSException ex) {
                                Logger.getLogger(Stanica.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
                
		subKvar = ts.createSubscriber(tKvar);
		subKvar.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg)
			{
                            try {
                                String nazivVoza = msg.getStringProperty("naziv");
                                System.out.println("Voz " + nazivVoza + "se pokvario!");
                            } catch (JMSException ex) {
                                Logger.getLogger(Stanica.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
		
		tc.start();
		//gotova inicijalizacija
	}
	
	
	
	public void Stigao(String voz, ArrayList<String> stanice, int brPutnika) throws JMSException
	{
            String temp = "";
            for(String stanica : stanice)
            {
                    temp += stanica + ",";
                    //na kraju ne treba ,
            }

            TextMessage txt = ts.createTextMessage();
            txt.setText(this.naziv);
            txt.setStringProperty("LinijaStanica", temp);
            txt.setIntProperty("BrojPutnika", brPutnika);

            senderStigao.send(txt);
	}
	
	
	
	public void KvarVoza(String nazivVoza) throws JMSException
	{
		TextMessage msg = ts.createTextMessage();
		msg.setStringProperty("naziv", nazivVoza);
		
		senderKvar.send(msg);
	}
        
        public void kraj() throws JMSException{
            tc.close();
            ts.close();
        }
	
	
	public static void main(String[] args)
	{
            try {
                Scanner sc = new Scanner(System.in);
                
                System.out.println("unesite naziv stanice");
                String stanica = sc.nextLine().trim();
                Stanica s = new Stanica(stanica);
                ArrayList<String> stanice = new ArrayList<String>();
                
                System.out.println("unesite stanice na liniji, za zavrsetak unesite 'quit'");
                String unos = sc.nextLine().trim();
                
                while(!unos.equals("quit"))
                {
                    stanice.add(unos);
                    unos = sc.nextLine().trim();
                }
                
                s.Stigao("Voz1", stanice, 10);
                s.Stigao("Voz2", stanice, 15);
                
                sc.nextLine();
                
                s.KvarVoza("Voz1");
                
                s.kraj();
            } catch (NamingException ex) {
                Logger.getLogger(Stanica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JMSException ex) {
                Logger.getLogger(Stanica.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}