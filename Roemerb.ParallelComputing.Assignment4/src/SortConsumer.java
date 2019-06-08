import javax.jms.*;

@SuppressWarnings("ALL")
public class SortConsumer
{
    private static ActiveMQ queueServer;
    private static Destination queue;
    private static Session sesh;
    private static MessageConsumer consumer;

    private static HeapSortSerial sort;

    public static class ChunkListener implements MessageListener
    {

        @Override
        public void onMessage(Message message)
        {
            // Acknowledge, sort and send on
            acknowledgeMessage(message);
            SortMessage sorted = this.sortReceivedMessage(message);
            sendSortedArrayOnSortedQueue(sorted);
        }

        /**
         * Sort the array in the received message
         *
         * @param message
         * @return
         */
        SortMessage sortReceivedMessage(Message message)
        {
            ObjectMessage objMessage;
            SortMessage sortMsg = new SortMessage();
            // Cast to SortMessage
            if (message instanceof ObjectMessage) {
                objMessage = (ObjectMessage) message;
                try
                {
                    sortMsg = (SortMessage) objMessage.getObject();
                } catch (JMSException e)
                {
                    e.printStackTrace();
                }
            }

            // Update the embedded array to a sorted version
            sortMsg.setArr(sort.sort(sortMsg.getArr()));

            return sortMsg;
        }

        /**
         * Acknowledge to ActiveMQ that the message was received without problems.
         *
         * @param msg
         */
        void acknowledgeMessage(Message msg)
        {
            try
            {
                msg.acknowledge();
            } catch (JMSException e)
            {
                System.out.println("Error while acknowledging message");
                e.printStackTrace();
            }
        }

        /**
         * Send the sorted message on to the next queue
         *
         * @param msg
         */
        void sendSortedArrayOnSortedQueue(SortMessage msg)
        {
            queueServer.sendSortMessageOnQueue(
                    queueServer.getActiveMQSession(),
                    ActiveMQ.sortedQueue,
                    msg
            );
        }
    }

    public static void main(String[] args) throws JMSException
    {
        sort = new HeapSortSerial();
        queueServer = new ActiveMQ();
        queueServer.connect();
        queue = queueServer.getQueue(ActiveMQ.chunkQueue);
        consumer = queueServer.getActiveMQSession().createConsumer(queue);
        consumer.setMessageListener(new ChunkListener());
        queueServer.getActiveMQCon().start();
    }
}
