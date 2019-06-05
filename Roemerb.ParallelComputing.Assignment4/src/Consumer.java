import javax.jms.*;
import java.util.concurrent.BlockingQueue;

public class Consumer {

    private static ActiveMQ queueServer;
    private static Destination queue;
    private static Session sesh;
    private static MessageConsumer consumer;

    public static class ChunkListener implements MessageListener
    {

        @Override
        public void onMessage(Message message)
        {
            System.out.println("Message received: " + message.toString());
        }
    }

    public static void main(String[] args) throws JMSException
    {
        queueServer = new ActiveMQ();
        queueServer.connect();
        queue = queueServer.getQueue(ActiveMQ.chunkQueue);
        consumer = queueServer.getActiveMQSession().createConsumer(queue);
        consumer.setMessageListener(new ChunkListener());
    }
}
