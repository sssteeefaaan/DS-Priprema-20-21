/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MergeSortClient2;

import java.util.Scanner;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Stefan
 */
public class IDGen {
    
    private long ID;
    
    private Queue IDQueue;
    private QueueConnection qc;
    private QueueSession qs;
    private QueueSender IDSender;

    public IDGen() throws NamingException, JMSException {
        this.ID = 1;

        InitialContext ctx = new InitialContext();

        QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("qcf");

        this.IDQueue = (Queue) ctx.lookup("IDQueue03102021");

        ctx.close();

        this.qc = qcf.createQueueConnection();
        this.qs = this.qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        this.IDSender = this.qs.createSender(this.IDQueue);

        this.qs.createConsumer(this.IDQueue, "")
                .setMessageListener(ml -> {
            try {
                long id = ml.getLongProperty("ID");
                
                Message m = this.qs.createMessage();
                m.setLongProperty("ID", id);
                m.setStringProperty("Key", Long.toString(this.ID++));
                
                this.IDSender.send(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.qc.start();
    }

    public static void main(String[] args) throws NamingException, JMSException {
        IDGen gen = new IDGen();
        System.out.println("ID gen is running, press Enter to exit...");

        Scanner s = new Scanner(System.in);
        s.nextLine();
        s.close();
        
        System.exit(0);
    }
}
