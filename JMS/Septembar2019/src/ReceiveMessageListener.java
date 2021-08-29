
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class ReceiveMessageListener implements MessageListener {

    private Klijent klijent;
    public ReceiveMessageListener(Klijent aThis) {
        this.klijent = aThis;
    }
    
    @Override
    public void onMessage(Message msg)
    {
        try{
            ObjectMessage objMsg = (ObjectMessage) msg;
            Recept r = (Recept) objMsg.getObject();
            int IDPoslao = objMsg.getIntProperty("IDSalje");
            
            this.klijent.print("Recept '" + r.naziv + "' je poslao klijent sa ID-em " + IDPoslao);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
