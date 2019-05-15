package Roemerb.ParallelComputing.Assignment3;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<int[]> queue;

    @Override
    public void run() {
        while (true) {
            try {
                int[] arr = queue.take();
                process(arr);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void process(int[] take) throws InterruptedException {
        System.out.println("[Consumer] Taken " + take.length + " elements to sort");

        HeapSortSerial hss = new HeapSortSerial();
        hss.sort(take);
        System.out.println("[Consumer] Done");
    }

    public Consumer(BlockingQueue<int[]> queue) {
        this.queue = queue;
    }
}
