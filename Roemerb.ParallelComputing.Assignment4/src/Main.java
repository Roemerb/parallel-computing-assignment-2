public class Main {

    public static final int MAX_SIZE = 1000000;

    public static void main(String[] args) {
//        int[] arr = Utils.createTestSet(100000, 5000);
//
//        HeapSortParallel hsp = new HeapSortParallel(10, arr);
//        int[] sorted = hsp.sort(arr);

        testWithNElementsParallel(1000);
        testWithNElementsParallel(10000);
        testWithNElementsParallel(100000);
        testWithNElementsParallel(1000000);
        testWithNElementsParallel(5000000);
        testWithNElementsParallel(10000000);
    }

    public static void testWithNElementsParallel(int n) {
        int[] arr = Utils.createTestSet(n, Main.MAX_SIZE);

        HeapSortParallel hsp = new HeapSortParallel(10, arr);

        long startTime = System.nanoTime();
        hsp.sort(arr);
        long endTime = System.nanoTime();
        long totalTimeMs = ((endTime - startTime)/1000);
        System.out.println(String.format("%d\t\t\t%d\t\t\t%d", n, totalTimeMs, (totalTimeMs/1000)));
    }
}
