
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class OnlineMessageListener implements MessageListener {

    private Klijent klijent;
    public OnlineMessageListener(Klijent aThis) {
        this.klijent = aThis;
    }

    @Override
    public void onMessage(Message msg) {
        try {
            int ID = msg.getIntProperty("IDOnline");
            this.klijent.print("Klijent sa ID-em " + ID + " je online!");
        } catch (JMSException ex) {
            Logger.getLogger(OnlineMessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
