package Roemerb.ParallelComputing.Assignment2;

import java.util.concurrent.*;

public class HeapSortParallel extends HeapSortSerial {

    private ThreadPoolExecutor exec;
    private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    public int[][] sortedChunks;

    public synchronized void complete(int coreId, int[] result)
    {
        sortedChunks[coreId] = result;
    }

    @Override
    public int[] sort(int[] arr)
    {
        sortedChunks = new int[NUMBER_OF_CORES][];

        exec = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        ) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
            }

            @Override
            protected void terminated() {
                super.terminated();
            }
        };


        int[][] divided = Utils.divideArray(arr, NUMBER_OF_CORES);
        for (int i = 0; i < NUMBER_OF_CORES; i++)
        {
            int finalI = i;
            sortedChunks[finalI] = new int[divided[finalI].length];
            exec.execute(() -> {
                HeapSortSerial hss = new HeapSortSerial();
                complete(finalI, hss.sort(divided[finalI]));
            });
        }
        exec.shutdown();

        return MergeKArrays.mergeKSortedArray(sortedChunks);
    }
}
