import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.Arrays;

public class Producer {

    private static ActiveMQ queueServer;
    private static Destination queue;
    private static int[] arr;
    private static int SLICE_SIZE = 100;

    public static void sendMessages() {
        for (int i = 0; i < Math.ceil((arr.length / SLICE_SIZE)); i++) {
            int start = i * SLICE_SIZE;
            int end = start + SLICE_SIZE - 1;


            int[] slice = Arrays.copyOfRange(arr, start, end);

            String msg = sliceToMessage(slice);
            System.out.println("Sending message to queue");
            queueServer.sendMessageOnQueue(
                    queueServer.getActiveMQSession(),
                    ActiveMQ.chunkQueue,
                    msg
            );
        }
    }

    private static String sliceToMessage(int[] slice)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < slice.length; i++)
        {
            if ((i-1) == slice.length)
            {
                sb.append(slice[i]);
            }
            else
            {
                String t = slice[i]+",";
                sb.append(t);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws JMSException
    {
        queueServer = new ActiveMQ();
        queueServer.connect();
        queue = queueServer.getQueue(ActiveMQ.chunkQueue);

        arr = Utils.createTestSet(100000, 100000);

        sendMessages();
    }
}
