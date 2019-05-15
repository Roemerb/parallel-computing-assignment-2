package Roemerb.ParallelComputing.Assignment3;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<int[]> queue;
    private final int[] arr;
    private final int SLICE_SIZE = 10000;
    private final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    @Override
    public void run() {
        try {
            process();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void process() throws InterruptedException {
        for (int i = 0; i < Math.ceil(arr.length / SLICE_SIZE); i++) {
            int start = i * SLICE_SIZE;
            int end = start + SLICE_SIZE;

            System.out.println("[Producer] putting indexes " + start + " to " + end);

            int[] slice = Arrays.copyOfRange(arr, start, end);
            queue.put(slice);
            System.out.println("Remaining capacity: " + queue.remainingCapacity());
        }
    }

    public Producer(BlockingQueue<int[]> queue, int[] arr) {
        this.queue = queue;
        this.arr = arr;
    }
}
