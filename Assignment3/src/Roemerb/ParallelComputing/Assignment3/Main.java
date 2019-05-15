package Roemerb.ParallelComputing.Assignment3;

public class Main {
    public static void main(String[] args) {
        int[] arr = Utils.createTestSet(100000, 5000);

        HeapSortParallel hsp = new HeapSortParallel(10, arr);
        int[] sorted = hsp.sort(arr);

        Utils.printArray(sorted, 25);
    }
}
