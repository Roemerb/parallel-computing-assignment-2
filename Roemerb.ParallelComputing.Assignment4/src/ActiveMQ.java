import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQ
{
    public final static String serverUrl = "tcp://localhost:61616";
    public final static String chunkQueue = "chunks";
    public final static String sortedQueue = "sorted";

    private Connection activeMQCon;
    private Session activeMQSession;

    public void connect()
    {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(serverUrl);
        try
        {
            Connection con = connectionFactory.createConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            activeMQCon = con;
            activeMQSession = session;
        } catch (JMSException e)
        {
            System.out.println("COULD NOT CONNECT TO '" + serverUrl + "'");
            return;
        }

        System.out.println("Connected to '" + serverUrl + "'");
    }

    public Destination getQueue(String queue) throws JMSException
    {
        return activeMQSession.createQueue(queue);
    }

    public void sendMessageOnQueue(Session session, String queue, String message)
    {
        try
        {
            Destination dest = session.createQueue(queue);
            MessageProducer msgProd = session.createProducer(dest);
            TextMessage msg = session.createTextMessage(message);
            msgProd.send(msg);
        } catch (JMSException e)
        {
            System.out.println("Failed to sent message: '" + e.getMessage() + "'");
        }

        System.out.println("Sent message: '" + message + "'");
    }

    public Connection getActiveMQCon()
    {
        return activeMQCon;
    }

    public void setActiveMQCon(Connection activeMQCon)
    {
        this.activeMQCon = activeMQCon;
    }

    public Session getActiveMQSession()
    {
        return activeMQSession;
    }

    public void setActiveMQSession(Session activeMQSession)
    {
        this.activeMQSession = activeMQSession;
    }
}
