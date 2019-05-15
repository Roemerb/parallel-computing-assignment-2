package Roemerb.ParallelComputing.Assignment3;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<SortingJob> queue;

    @Override
    public void run() {
        while (true) {
            try {
                SortingJob sj = queue.take();
                if (sj.kill) {
                    System.out.println("Got KILL signal, dying");
                    break;
                }
                int[] arr = sj.slice;
                process(arr);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void process(int[] take) {
        System.out.println("[Consumer] Taken " + take.length + " elements to sort");

        HeapSortSerial hss = new HeapSortSerial();
        int[] sorted = hss.sort(take);
        System.out.println("[Consumer] Done");

        HeapSortParallel.complete(sorted);
    }

    public Consumer(BlockingQueue<SortingJob> queue) {
        this.queue = queue;
    }
}
