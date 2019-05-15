package Roemerb.ParallelComputing.Assignment3;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<SortingJob> queue;
    private final int[] arr;
    private int SLICE_SIZE;

    @Override
    public void run() {
        SLICE_SIZE = (int) Math.ceil(arr.length/Runtime.getRuntime().availableProcessors());
        try {
            process();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void process() throws InterruptedException {
        for (int i = 0; i < Math.ceil(arr.length / SLICE_SIZE); i++) {
            int start = i * SLICE_SIZE;
            int end = start + SLICE_SIZE - 1;

            //System.out.println("[Producer] putting indexes " + start + " to " + end);

            int[] slice = Arrays.copyOfRange(arr, start, end);
            queue.put(new SortingJob(slice));
            //System.out.println("Remaining capacity: " + queue.remainingCapacity());
        }
        // Fill with kill signals to kill the consumers
        for (int i = 0; i < queue.size(); i++) {
            queue.put(new SortingJob(true));
        }
    }

    public Producer(BlockingQueue<SortingJob> queue, int[] arr) {
        this.queue = queue;
        this.arr = arr;
    }
}
