import javax.jms.*;
import java.util.*;

public class MergeConsumer
{
    private static ActiveMQ queueServer;
    private static Destination queue;
    private static Session sesh;
    private static MessageConsumer consumer;

    private static HeapSortSerial sort;

    public static class SortedChunkListener implements MessageListener
    {

        private Map<String, LinkedList<SortMessage>> jobs = new HashMap<>();

        @Override
        public void onMessage(Message message)
        {
            acknowledgeMessage(message);
            SortMessage sortMsg = msgToSortMessage(message);
            storeMessage(sortMsg);

            if (messageCompletesJob(sortMsg))
            {
                System.out.println("Job completed! Combining sorted chunks");
                int[] sorted = combineSortedChunks(sortMsg.getJobId());
                System.out.println("Successfully combined " + sorted.length + " elements.");
            }
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

        SortMessage msgToSortMessage(Message message)
        {
            ObjectMessage objMessage;
            SortMessage sortMsg = new SortMessage();
            // Cast to SortMessage
            if (message instanceof ObjectMessage) {
                objMessage = (ObjectMessage) message;
                sortMsg = (SortMessage) objMessage;
            }

            return sortMsg;
        }

        /**
         * Stores the received message in the correct place in the jobs map
         *
         * @param message
         */
        void storeMessage(SortMessage message)
        {
            LinkedList<SortMessage> list;
            if (!jobs.containsKey(message.getJobId()))
            {
                list = new LinkedList<SortMessage>();
                ((LinkedList<SortMessage>) list).push(message);
                jobs.put(message.getJobId(), list);
            }
            else
            {
                list = jobs.get(message.getJobId());
                list.push(message);
            }
        }

        /**
         * Indicates if all parts of a job have been received
         *
         * @param msg
         * @return
         */
        boolean messageCompletesJob(SortMessage msg)
        {
            return jobs.get(msg.getJobId()).size() == msg.getTotalParts();
        }

        int[] combineSortedChunks(String jobID)
        {
            LinkedList<SortMessage> chunks = jobs.get(jobID);

            return MergeKArrays.mergeKSortedArray(chunks);
        }
    }

    public static void main(String[] args) throws JMSException
    {
        sort = new HeapSortSerial();
        queueServer = new ActiveMQ();
        queueServer.connect();
        queue = queueServer.getQueue(ActiveMQ.chunkQueue);
        consumer = queueServer.getActiveMQSession().createConsumer(queue);
        consumer.setMessageListener(new MergeConsumer.SortedChunkListener());
        queueServer.getActiveMQCon().start();
    }
}
