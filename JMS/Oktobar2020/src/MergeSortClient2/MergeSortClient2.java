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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
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
    private HashMap<String, LinkedList<Integer>> sortingQueue;
    
    private Queue splitQueue;
    private Queue joinQueue;
    private Queue IDQueue;
    
    private QueueConnection qc;
    private QueueSession qs;
    
    private QueueSender splitSender;
    private QueueSender joinSender;
    private QueueSender IDSender;
    
    private QueueReceiver splitReceiver;
    private QueueReceiver IDReceiver;
    private HashMap<String, MessageConsumer> sortingReceivers;
    
    public MergeSortClient2(long ID) throws NamingException, JMSException {
        this.ID = ID;
        this.sortingQueue = new HashMap<>();
        this.sortingReceivers = new HashMap<>();
        
        InitialContext ctx = new InitialContext();
        
        QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("qcf");
        
        this.splitQueue = (Queue) ctx.lookup("SplitQueue03102021");
        this.joinQueue = (Queue) ctx.lookup("JoinQueue03102021");
        this.IDQueue = (Queue) ctx.lookup("IDQueue03102021");
        
        ctx.close();
        
        this.qc = qcf.createQueueConnection();
        this.qs = this.qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        this.splitSender = this.qs.createSender(this.splitQueue);
        this.joinSender = this.qs.createSender(this.joinQueue);
        this.IDSender = this.qs.createSender(this.IDQueue);
        
        this.IDReceiver = this.qs.createReceiver(this.IDQueue, "ID = " + Long.toString(this.ID));
        this.splitReceiver = this.qs.createReceiver(this.splitQueue);
        this.splitReceiver.setMessageListener(ml
                -> {
            try {
                ObjectMessage objMsg = (ObjectMessage) ml;
                LinkedList<Integer> array = (LinkedList<Integer>) objMsg.getObject();
                long fromID = ml.getLongProperty("FromID");
                String splitKey = ml.getStringProperty("SplitKey");
                
                System.out.println("Primio [" + splitKey + "] od: " + fromID);
                for (int i : array) {
                    System.out.print(i + " ");
                }
                System.out.println();
                
                if (array.size() == 1) {
                    ObjectMessage newObjectMessage = this.qs.createObjectMessage(array);
                    newObjectMessage.setLongProperty("FromID", this.ID);
                    newObjectMessage.setLongProperty("ToID", fromID);
                    newObjectMessage.setStringProperty("JoinKey", splitKey);
                    
                    this.joinSender.send(newObjectMessage);
                    System.out.println("Poslato (" + array.getFirst() + ") sa key [" + splitKey + "] i ID " + fromID);
                    
                } else {
                    int half = array.size() / 2;
                    LinkedList<Integer> left = new LinkedList<>(array.subList(0, half)),
                            right = new LinkedList<>(array.subList(half, array.size()));
                    String joinKey = this.getKey();
                    
                    this.sortingReceivers.put(joinKey, this.qs.createConsumer(this.joinQueue, "JoinKey = '" + joinKey + "' AND ToID = " + Long.toString(this.ID), false));
                    this.sortingReceivers.get(joinKey).setMessageListener(ml1
                            -> {
                        try {
                            System.out.println("Join [" + joinKey + "] ID [" + this.ID + "]");
                            
                            LinkedList<Integer> array1 = (LinkedList<Integer>) ((ObjectMessage) ml1).getObject();
                            
                            if (this.sortingQueue.containsKey(joinKey)) {
                                array1 = this.merge(array1, this.sortingQueue.get(joinKey));
                                
                                ObjectMessage newObjMessage = this.qs.createObjectMessage(array1);
                                newObjMessage.setLongProperty("ToID", fromID);
                                newObjMessage.setLongProperty("FromID", this.ID);
                                newObjMessage.setStringProperty("JoinKey", splitKey);
                                
                                this.joinSender.send(newObjMessage);
                                this.sortingReceivers.remove(joinKey);
                            } else {
                                this.sortingQueue.put(joinKey, array);                                
                            }
                        } catch (JMSException ex) {
                            Logger.getLogger(MergeSortClient2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    this.qc.start();
                    
                    ObjectMessage newObjMsgLeft = this.qs.createObjectMessage(left);
                    newObjMsgLeft.setLongProperty("FromID", this.ID);
                    newObjMsgLeft.setStringProperty("SplitKey", splitKey);
                    this.splitSender.send(newObjMsgLeft);
                    
                    ObjectMessage newObjMsgRight = this.qs.createObjectMessage(right);
                    newObjMsgRight.setLongProperty("FromID", this.ID);
                    newObjMsgRight.setStringProperty("SplitKey", splitKey);
                    this.splitSender.send(newObjMsgRight);
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
        String key = this.getKey();
        msg.setObject(array);
        msg.setLongProperty("FromID", this.ID);
        msg.setStringProperty("SplitKey", key);
        
        MessageConsumer recv = this.qs.createConsumer(this.joinQueue, "JoinKey = '" + key + "' AND ToID = " + Long.toString(this.ID), false);
        
        this.splitSender.send(msg);
        
        ObjectMessage msgRet = (ObjectMessage) recv.receive();
        return (LinkedList<Integer>) msgRet.getObject();
    }
    private String getKey() throws JMSException
    {
        Message m = this.qs.createMessage();
        m.setLongProperty("ID", this.ID);
        
        this.IDSender.send(m);
        return this.IDReceiver.receive().getStringProperty("Key");
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
        for (int i : lista) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        s.nextLine();
        System.exit(0);
    }
}
