
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
public class LoginMessageListener implements MessageListener {

    private Klijent klijent;

    public LoginMessageListener(Klijent aThis) {
        this.klijent = aThis;
    }

    @Override
    public void onMessage(Message msg) {

        try {
            int ID = msg.getIntProperty("IDNovi");
            System.out.println("Klijent sa ID-em " + ID + " se ulogovao!");
            
            Message m = this.klijent.getQueueSession().createMessage();
            m.setIntProperty("IDLogRes", ID);
            m.setIntProperty("IDOnline", this.klijent.getID());
            this.klijent.getSender().send(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
