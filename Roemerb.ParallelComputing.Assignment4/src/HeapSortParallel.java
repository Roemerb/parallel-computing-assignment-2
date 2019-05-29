import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class HeapSortParallel extends HeapSortSerial {

    public final int QUEUE_SIZE;

    private int[] arr;
    private static List<int[]> finishedSlices = new ArrayList<>();

//    @Override
//    public int[] sort(int[] arr) {
//        this.arr = arr;
//
//        try {
//            Thread sortThread = new Thread(new HeapSortParallel(10, arr));
//            sortThread.start();
//            sortThread.join();
//        } catch (InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
//
//        return MergeKArrays.mergeKSortedArray(finishedSlices);
//    }
//
//    public static void complete(int[] sorted) {
//        finishedSlices.add(sorted);
//    }
//
//    @Override
//    public void run() {
//        BlockingQueue<SortingJob> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);
//
//        Thread producerThread = new Thread(new Producer(queue, arr));
//        Thread consumerThread = new Thread(new Consumer(queue));
//
//        producerThread.start();
//        consumerThread.start();
//
//        try {
//            producerThread.join();
//            consumerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public HeapSortParallel(int queueSize, int[] arr) {
        this.QUEUE_SIZE = queueSize;
        this.arr = arr;
    }
}
