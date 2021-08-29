
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class RequestMessageListener implements MessageListener {

    private MergeSortClient client;

    public RequestMessageListener(MergeSortClient client) {
        this.client = client;
    }

    @Override
    public void onMessage(Message msg) {
        try {
            ObjectMessage objMsg = (ObjectMessage) msg;
            int reqId = objMsg.getIntProperty("reqId");
            ArrayList<Integer> reqList = (ArrayList<Integer>) objMsg.getObject();
            System.out.print("reqId = " + reqId + ": [");
            for (int i : reqList) {
                System.out.print("\t" + i);
            }
            System.out.println("\t]");

            ObjectMessage objMsg1 = this.client.getQueueSession().createObjectMessage();
            reqList = client.requestSorting(reqList, reqId);
            objMsg1.setObject(reqList);
            objMsg1.setIntProperty("reqId", reqId);
            this.client.getResponseSender().send(objMsg1);
        } catch (JMSException ex) {
            Logger.getLogger(RequestMessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
