/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MergeSortClient2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
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

/**
 *
 * @author Stefan
 */
public class MergeSortClient2 {

    private long ID;
    private Queue splitQueue;
    private Queue joinQueue;

    private QueueConnection qc;
    private QueueSession qs;

    private QueueSender splitSender;
    private MessageProducer joinSender;
    
    private QueueReceiver splitReceiver;

    public MergeSortClient2(long ID) throws NamingException, JMSException {
        this.ID = ID;

        InitialContext ctx = new InitialContext();

        QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("qcf");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("tcf");

        this.splitQueue = (Queue) ctx.lookup("SplitQueue333");
        this.joinQueue = (Queue) ctx.lookup("JoinQueue333");

        ctx.close();

        this.qc = qcf.createQueueConnection();
        this.qs = this.qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);

        this.splitSender = this.qs.createSender(this.splitQueue);
        this.joinSender = this.qs.createProducer(this.joinQueue);

        this.splitReceiver = this.qs.createReceiver(this.splitQueue);
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
                    this.joinSender.send(objMsg);
                    System.out.println("Poslato ("+ array.getFirst() + ") sa key [" + key + "] i ID " + id);
                    
                } else {
                    int half = array.size() / 2;
                    LinkedList<Integer> left = new LinkedList<>(array.subList(0, half)),
                            right = new LinkedList<>(array.subList(half, array.size()));
                    String key1 = UUID.randomUUID().toString();
                    
                    MessageConsumer recv = this.qs.createConsumer(this.joinQueue, "Key = '" + key1 + "' AND ID = " + Long.toString(this.ID), false);
                    
                    ObjectMessage newObjMsg = this.qs.createObjectMessage();
                    
                    newObjMsg.setLongProperty("ID", this.ID);
                    newObjMsg.setStringProperty("Key", key1);
                    
                    newObjMsg.setObject(left);
                    this.splitSender.send(newObjMsg);
                    
                    newObjMsg.setObject(right);
                    this.splitSender.send(newObjMsg);
                    
                    ObjectMessage msgRet = null;
                    while(msgRet == null)
                        msgRet = (ObjectMessage) recv.receive(100);
                    msgRet.acknowledge();
                    left = (LinkedList<Integer>) msgRet.getObject();
                    
                    System.out.println("Primio left [" + key1 + "] za: " + this.ID);

                    msgRet = null;
                    while(msgRet == null)
                        msgRet = (ObjectMessage) recv.receive(100);
                    msgRet.acknowledge();
                    right = (LinkedList<Integer>) msgRet.getObject();
                    
                    System.out.println("Primio right [" + key1 + "] za: " + this.ID);
                    
                    objMsg.setObject(merge(left, right));
                    this.joinSender.send(objMsg);
                    
                    System.out.println("Poslato merged sa key [" + key + "] i ID " + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.qc.start();
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
        
        MessageConsumer recv = this.qs.createConsumer(this.joinQueue, "Key = '" + key + "' AND ID = " + Long.toString(this.ID), false);
        
        this.splitSender.send(msg);
        
        ObjectMessage msgRet = (ObjectMessage) recv.receive();
        msgRet.acknowledge();
        return (LinkedList<Integer>) msgRet.getObject();
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Scanner s = new Scanner(System.in);

        System.out.print("Unesite svoj ID: ");
        MergeSortClient2 c = new MergeSortClient2(Integer.parseInt(s.nextLine().trim()));

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
