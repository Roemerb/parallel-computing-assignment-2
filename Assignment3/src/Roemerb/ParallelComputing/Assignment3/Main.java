package Roemerb.ParallelComputing.Assignment3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static final int MAX_VALUE = 10000000;

    public static void main(String[] args) throws Exception {
        BlockingQueue<int[]> queue = new LinkedBlockingQueue<>(10);
        int[] arr = Utils.createTestSet(100000, 1000000);

        Thread producterThread = new Thread(new Producer(queue, arr));
        Thread consumerThread = new Thread(new Consumer(queue));
        Thread consumerThread2 = new Thread(new Consumer(queue));

        producterThread.start();
        consumerThread.start();
        consumerThread2.start();
    }
}
