/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MergeSortClient2;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Stefan
 */
public class DaFak {

    private long ID;
    private Queue splitQueue;
    private Topic joinTopic;

    private QueueConnection qc;
    private QueueSession qs;
    
    private TopicConnection tc;
    private TopicSession ts;

    private QueueSender splitSender;
    private TopicPublisher joinPublisher;
    
    private MessageConsumer splitReceiver;

    public DaFak(long ID) throws NamingException, JMSException {
        this.ID = ID;

        InitialContext ctx = new InitialContext();

        QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("qcf");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("tcf");

        this.splitQueue = (Queue) ctx.lookup("SplitQueueFinalee");
        this.joinTopic = (Topic) ctx.lookup("JoinTopicFinalee");

        ctx.close();

        this.qc = qcf.createQueueConnection();
        this.qs = this.qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        this.tc = tcf.createTopicConnection();
        this.ts = this.tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        this.splitSender = this.qs.createSender(this.splitQueue);
        this.joinPublisher = this.ts.createPublisher(this.joinTopic);

        this.splitReceiver = this.qs.createConsumer(this.splitQueue, "", false);
        this.splitReceiver.setMessageListener(ml
                -> {
            try {
                ml.acknowledge();
                ObjectMessage objMsg = (ObjectMessage) ml;
                LinkedList<Integer> array = (LinkedList<Integer>) objMsg.getObject();
                long id = ml.getLongProperty("ID");
                String key = ml.getStringProperty("Key");

                System.out.println("Primio [" + key + "] od: " + id);
                for (int i : array) {
                    System.out.print(i + " ");
                }
                System.out.println();

                if (array.size() == 1) {
                    ObjectMessage topicMsg = this.ts.createObjectMessage();
                    topicMsg.setObject(array);
                    topicMsg.setLongProperty("ID", id);
                    topicMsg.setStringProperty("Key", key);
                    
                    this.joinPublisher.publish(topicMsg);
                    
                    System.out.println("Poslato ("+ array.getFirst() + ") sa key [" + key + "] i ID " + id);
                    
                } else {
                    int half = array.size() / 2;
                    LinkedList<Integer> left = new LinkedList<>(array.subList(0, half)),
                            right = new LinkedList<>(array.subList(half, array.size()));
                    String key1 = UUID.randomUUID().toString();
                    
                    TopicSubscriber recv = this.ts.createSubscriber(this.joinTopic, "Key = '" + key1 + "' AND ID = " + Long.toString(this.ID), false);
                    
                    ObjectMessage newObjMsg = this.qs.createObjectMessage();
                    
                    newObjMsg.setLongProperty("ID", this.ID);
                    newObjMsg.setStringProperty("Key", key1);
                    
                    newObjMsg.setObject(left);
                    this.splitSender.send(newObjMsg);
                    
                    newObjMsg.setObject(right);
                    this.splitSender.send(newObjMsg);
                    
                    ObjectMessage msgRet = (ObjectMessage) recv.receive();
                    left = (LinkedList<Integer>) msgRet.getObject();
                    
                    System.out.println("Primio left [" + key1 + "] za: " + this.ID);

                    msgRet = (ObjectMessage) recv.receive();
                    right = (LinkedList<Integer>) msgRet.getObject();
                    
                    System.out.println("Primio right [" + key1 + "] za: " + this.ID);
                    
                    ObjectMessage topicMsg = this.ts.createObjectMessage();
                    topicMsg.setObject(merge(left, right));
                    topicMsg.setLongProperty("ID", id);
                    topicMsg.setStringProperty("Key", key);
                    
                    this.joinPublisher.publish(topicMsg);
                    
                    System.out.println("Poslato merged sa key [" + key + "] i ID " + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.qc.start();
        this.tc.start();
    }

    private LinkedList<Integer> merge(LinkedList<Integer> a, LinkedList<Integer> b) {
        LinkedList<Integer> ret = new LinkedList<>();
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            ret.add(a.get(i) < b.get(j) ? a.get(i++) : b.get(j++));
        }
        while (i < a.size()) {
            ret.add(a.get(i++));
        }
        while (j < b.size()) {
            ret.add(b.get(j++));
        }

        return ret;
    }

    public LinkedList<Integer> mergeSort(LinkedList<Integer> array) throws JMSException {
        ObjectMessage msg = this.qs.createObjectMessage();
        String key = UUID.randomUUID().toString();
        msg.setObject(array);
        msg.setLongProperty("ID", this.ID);
        msg.setStringProperty("Key", key);
        
        TopicSubscriber recv = this.ts.createSubscriber(this.joinTopic, "Key = '" + key + "' AND ID = " + Long.toString(this.ID), false);
        
        this.splitSender.send(msg);
        
        ObjectMessage msgRet = (ObjectMessage) recv.receive();
        msgRet.acknowledge();
        return (LinkedList<Integer>) msgRet.getObject();
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Scanner s = new Scanner(System.in);

        System.out.print("Unesite svoj ID: ");
        DaFak c = new DaFak(Integer.parseInt(s.nextLine().trim()));

        LinkedList<Integer> lista = new LinkedList<>();
        String temp = "";

        System.out.println("Unesite brojeve ili quit za kraj");
        while (true) {
            temp = s.nextLine().trim();
            if (temp.equals("quit")) {
                break;
            }
            lista.add(Integer.parseInt(temp));
        }
        lista = c.mergeSort(lista);
        
        System.out.println("Sortirana lista:");
        for(int i:lista)
            System.out.print(i + " ");
        System.out.println();

        s.nextLine();
        System.exit(0);
    }
}
