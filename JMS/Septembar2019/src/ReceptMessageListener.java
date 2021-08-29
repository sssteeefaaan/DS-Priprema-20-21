import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ReceptMessageListener implements MessageListener {

    private Klijent klijent;
    public ReceptMessageListener(Klijent aThis) {
        this.klijent = aThis;
    }
    @Override
    public void onMessage(Message m)
    {
        try{
            int IDTrazio = m.getIntProperty("IDTrazio");
            String naziv = m.getStringProperty("nazivRecepta");
            
            System.out.println("Klijent sa ID-em " + IDTrazio + " je trazio recept '" + naziv + "'");
            Recept temp = this.klijent.getRecepti().get(naziv);
            
            ObjectMessage msg = this.klijent.getQueueSession().createObjectMessage();
            msg.setObject(temp);
            msg.setIntProperty("IDPrima", IDTrazio);
            msg.setIntProperty("IDSalje", this.klijent.getID());
            this.klijent.getSender().send(msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
