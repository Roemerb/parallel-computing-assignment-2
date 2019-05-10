public class HeapSortParallel extends HeapSortSerial {

    public int[][] sortedChunks;
    public int[] finalSortedArr;
    public Thread[] threads;

    public synchronized void complete(int coreId, int[] result)
    {
        sortedChunks[coreId] = result;
    }

    class SortingThread implements Runnable {
        int coreId;
        int[] arr;

        public SortingThread(int coreId, int[] arr)
        {
            this.coreId = coreId;
            this.arr = arr;
        }

        @Override
        public void run() {
            HeapSortSerial hss = new HeapSortSerial();
            complete(this.coreId, hss.sort(this.arr));
        }
    }

    @Override
    public int[] sort(int[] arr)
    {
        int cores = Runtime.getRuntime().availableProcessors();
        int usableCores = cores-1;

        int[][] divided = Utils.divideArray(arr, usableCores);
        for (int i = 0; i < usableCores; i++)
        {
            SortingThread st = new SortingThread(i, divided[i]);
            threads[i] = new Thread(st);
            threads[i].start();
        }

        return finalSortedArr;
    }
}
