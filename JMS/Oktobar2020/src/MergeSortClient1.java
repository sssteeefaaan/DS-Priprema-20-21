
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MergeSortClient1 {

    private Queue request, response; // dva reda - jedan za zahteve za sortiranje, drugi za odgovore
    private QueueSender requestSender, responseSender;
    private QueueReceiver requestReceiver; // odgovore primam sinhrono
    private QueueConnection qc;
    private QueueSession qs;
    
    private HashMap<Integer, WaitList> waitList;

    public MergeSortClient1() throws NamingException, JMSException, JMSException, JMSException {
        this.waitList = new HashMap<>();
        
        InitialContext ictx = new InitialContext();
        QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("qcfMergeSort");
        this.request = (Queue) ictx.lookup("requestSorting101234567890");
        this.response = (Queue) ictx.lookup("sortedResponse101234567890");
        ictx.close();

        this.qc = (QueueConnection) qcf.createQueueConnection();
        this.qs = (QueueSession) qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        this.requestSender = (QueueSender) this.qs.createSender(this.request);
        this.responseSender = (QueueSender) this.qs.createSender(this.response);

        this.requestReceiver = this.qs.createReceiver(request);
        this.requestReceiver.setMessageListener(ml
                -> {
            try {
                ObjectMessage objMsg = (ObjectMessage) ml;
                int reqId = objMsg.getIntProperty("reqId");
                ArrayList<Integer> reqList = (ArrayList<Integer>) objMsg.getObject();
                System.out.print("reqId = " + reqId + ": [");
                for (int i : reqList) {
                    System.out.print("\t" + i);
                }
                System.out.println("\t]");

                if (reqList.size() == 1) {
                    ObjectMessage objMsg1 = this.qs.createObjectMessage();
                    objMsg1.setObject(reqList);
                    objMsg1.setIntProperty("respId", reqId);
                    this.responseSender.send(objMsg1);
                }
                else{
                    requestSorting(reqList, reqId);
                }
            } catch (JMSException ex) {
                Logger.getLogger(MergeSortClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        qc.start();
    }

    public QueueSender getResponseSender() {
        return this.responseSender;
    }

    public QueueSession getQueueSession() {
        return this.qs;
    }

    public void requestSorting(ArrayList<Integer> list, int reqId) throws JMSException { // svaki zahtev ima id, za inicijalni zahtev je id = 0
        int n = list.size();
        ArrayList<Integer> firstResp = null, secondResp = null;
        QueueReceiver recv = qs.createReceiver(response, "respId = " + (reqId * 2 + 1) + " OR respId = " + (reqId * 2 + 2));
        recv.setMessageListener(ml->
            {
                try {
                ObjectMessage objMsg = (ObjectMessage) ml;
                int respId = objMsg.getIntProperty("respId");
                if(this.waitList.get(respId).first == null)
                    this.waitList.get(respId).first = (ArrayList<Integer>) objMsg.getObject();
                else
                {
                    secondResp =(ArrayList<Integer>) objMsg.getObject();
                    ObjectMessage objMsg1 = this.qs.createObjectMessage();
                    objMsg1.setObject(merge2(firstResp, secondResp));
                    objMsg1.setIntProperty("respId", respId);
                    this.responseSender.send(objMsg1);
                }
            } catch (JMSException ex) {
                Logger.getLogger(MergeSortClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            });

        ArrayList<Integer> first = new ArrayList<>(list.subList(0, n / 2)),
                second = new ArrayList<>(list.subList(n / 2, n));

        ObjectMessage objMsg1 = qs.createObjectMessage();
        objMsg1.setObject(first);
        objMsg1.setIntProperty("reqId", reqId * 2 + 1);
        requestSender.send(objMsg1);

        ObjectMessage objMsg2 = qs.createObjectMessage();
        objMsg2.setObject(second);
        objMsg2.setIntProperty("reqId", reqId * 2 + 2);
        requestSender.send(objMsg2);
    }

    public int[] merge(int[] a, int[] b) {
        int i, j, k;
        int[] c = new int[a.length + b.length];
        for (i = j = k = 0; i < a.length && j < b.length;) {
            c[k++] = b[j] < a[i] ? b[j++] : a[i++];
        }
        while (i < a.length) {
            c[k++] = a[i++];
        }
        while (j < b.length) {
            c[k++] = b[j++];
        }
        return c;
    }

    private ArrayList<Integer> merge2(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> c = new ArrayList<>();
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            c.add(a.get(i) < b.get(j) ? a.get(i++) : b.get(j++));
        }
        while (i < a.size()) {
            c.add(a.get(i++));
        }
        while (j < b.size()) {
            c.add(b.get(j++));
        }
        return c;
    }

    public static void main(String[] args) {
        try {
            MergeSortClient1 msc = new MergeSortClient1();
            Scanner s = new Scanner(System.in);
            String input;
            ArrayList<Integer> lista = new ArrayList<>();
            System.out.println("Unesite elemente liste ili quit za kraj");

            while (true) {
                input = s.nextLine().trim().toLowerCase();
                if (input.equals("quit")) {
                    break;
                }
                lista.add(Integer.parseInt(input));
            }

            lista = msc.requestSorting(lista, 0);
            System.out.println("Sortirana lista : [");
            for (int i : lista) {
                System.out.print("\t" + i);
            }
            System.out.println("\t]");

            s.nextLine();
            s.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
