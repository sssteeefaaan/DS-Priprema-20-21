import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

public class Klijent {

    private HashMap<Integer, MergeSort> queue;
    private int ID;

    private Queue qSplit;
    private Queue qJoin;

    private QueueConnection qc;
    private QueueSession qs;

    private MessageProducer splitProducer;
    private MessageProducer joinProducer;

    public Klijent(int ID) throws NamingException, JMSException {
        this.ID = ID;
        this.queue = new HashMap<>();

        InitialContext ic = new InitialContext();
        QueueConnectionFactory qcf = (QueueConnectionFactory) ic.lookup("qcf");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ic.lookup("tcf");

        this.qSplit = (Queue) ic.lookup("qSplit1234567");
        this.qJoin = (Queue) ic.lookup("qJoin1234567");

        ic.close();

        this.qc = (QueueConnection) qcf.createQueueConnection();
        this.qs = (QueueSession) this.qc.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.splitProducer = this.qs.createProducer(qSplit);
        this.joinProducer = this.qs.createProducer(qJoin);
    }

    public void start() throws JMSException {
        this.qs.createConsumer(this.qSplit, "", true).setMessageListener(ml
                -> {
            try {
                SortStep step = (SortStep) ((ObjectMessage) ml).getObject();
                int ID = ml.getIntProperty("ID");
                System.out.println("Proces [" + this.ID + "] Poruka od procesa: " + ID);

                if (step.array.size() == 1) {
                    ObjectMessage om = this.qs.createObjectMessage();
                    om.setObject(step);
                    om.setIntProperty("ID", ID);

                    this.joinProducer.send(om);

                } else if (this.queue.containsKey(step.splitID)) {
                    this.splitProducer.send(ml);
                } else {
                    LinkedList<Integer> levi = new LinkedList<>();
                    int half = step.array.size() / 2;
                    int joinID = step.splitID;
                    int splitID = (int) (Math.random() * 1000);
                    
                    this.queue.put(joinID, new MergeSort(joinID));

                    int i = 0;
                    while (i < half) {
                        levi.add(step.array.get(i++));
                    }

                    ObjectMessage omLevi = this.qs.createObjectMessage();
                    SortStep newStepL = new SortStep(levi, step.originalSize, joinID, splitID);
                    omLevi.setObject(newStepL);
                    omLevi.setIntProperty("ID", this.ID);
                    this.splitProducer.send(omLevi);

                    splitID = (int) (Math.random() * 1000);
                    LinkedList<Integer> desni = new LinkedList<>();
                    while (i < step.array.size()) {
                        desni.add(step.array.get(i++));
                    }

                    ObjectMessage omDesni = this.qs.createObjectMessage();
                    SortStep newStepR = new SortStep(desni, step.originalSize, joinID, splitID);
                    omDesni.setObject(newStepR);
                    omDesni.setIntProperty("ID", this.ID);
                    this.splitProducer.send(omDesni);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.qs.createConsumer(qJoin, "ID = " + this.ID, true).setMessageListener(ml
                -> {
            try {
                SortStep step = (SortStep) ((ObjectMessage) ml).getObject();

                if (step.originalSize == step.array.size()) {
                    System.out.print("Sortiran niz:\n[\t");
                    for (int i : step.array) {
                        System.out.print(i + "\t");
                    }
                    System.out.println("]");
                } else {
                    int ID = ml.getIntProperty("ID");

                    MergeSort ref = this.queue.get(step.joinID);
                    if (ref.first == null) {
                        ref.first = step.array;
                    } else{
                        ref.second = step.array;
                        LinkedList<Integer> merged = (LinkedList) ref.sort();
                        //int prevSortID = ref.requestID;
                        this.queue.remove(step.joinID);

                        ObjectMessage om = this.qs.createObjectMessage();
                        SortStep newStep = new SortStep(merged, step.originalSize, step.splitID, -1);
                        om.setObject(newStep);
                        om.setIntProperty("ID", ID);

                        this.joinProducer.send(om);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.qc.start();
    }

    public void sortiraj(LinkedList<Integer> niz) throws JMSException {
        ObjectMessage om = this.qs.createObjectMessage();
        int sortID = (int)(Math.random() * 1000);
        om.setObject(new SortStep(niz, niz.size(), sortID, sortID));
        om.setIntProperty("ID", this.ID);
        
        System.out.println("splitID: " + sortID);

        this.splitProducer.send(om);
    }

    public void stop() throws JMSException {
        this.qs.close();
        this.qc.close();
    }

    public static void main(String[] args) {
        try {
            java.util.Scanner s = new java.util.Scanner(System.in);
            String input;

            System.out.println("Unesite ID klijenta");
            Klijent k = new Klijent(Integer.parseInt(s.nextLine().trim()));

            LinkedList<Integer> niz = new LinkedList<>();

            k.start();

            System.out.println("Unesite niz za sortiranje quit za kraj!");
            while (true) {
                input = s.nextLine().trim().toLowerCase();
                if (input.equals("quit")) {
                    break;
                }

                niz.add(Integer.parseInt(input));
            }

            k.sortiraj(niz);

            s.nextLine();

            s.close();
            k.stop();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
