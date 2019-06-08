import javax.jms.Destination;
import javax.jms.JMSException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class Producer {

    private static ActiveMQ queueServer;
    private static Destination queue;
    private static int[] arr;
    private static int SLICE_SIZE = 100;

    public static void sendMessages() {
        int slices = (int) Math.ceil(arr.length /  SLICE_SIZE);
        String jobID = generateJobID();

        for (int i = 0; i < slices; i++) {
            int start = i * SLICE_SIZE;
            int end = start + SLICE_SIZE - 1;


            int[] slice = Arrays.copyOfRange(arr, start, end);

            SortMessage sortMessage = new SortMessage();
            sortMessage.setJobId(jobID);
            sortMessage.setTotalParts(slices);
            sortMessage.setPart(i+1);
            sortMessage.setArr(slice);

            System.out.println("Sending message to queue");
            queueServer.sendSortMessageOnQueue(
                    queueServer.getActiveMQSession(),
                    ActiveMQ.chunkQueue,
                    sortMessage
            );
        }
    }

    public static String generateJobID()
    {
        MessageDigest salt = null;
        String digest = "";
        try
        {
            salt = MessageDigest.getInstance("SHA-256");
            salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
            digest = bytesToHex(salt.digest());
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return digest;
    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashInBytes.length; i++) {
            sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
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
