public class Main {

    public static final int MAX_VALUE = 5000000;

    public static void main(String[] args) throws Exception {
        System.out.println("Testing serial implementation");
        testWithNElementsSerial(1000);
        testWithNElementsSerial(10000);
        testWithNElementsSerial(100000);
        testWithNElementsSerial(1000000);
        testWithNElementsSerial(10000000);

        HeapSortParallel hsp = new HeapSortParallel();
        hsp.sort(Utils.createTestSet(100,100));
    }

    public static void testWithNElementsSerial(int n)
    {
        HeapSortSerial hss = new HeapSortSerial();

        System.out.println("Starting test with " + n + " elements");

        int[] arr = Utils.createTestSet(n, Main.MAX_VALUE);

        long startTime = System.nanoTime();
        hss.sort(arr);
        long endTime = System.nanoTime();
        long totalTimeMs = ((endTime - startTime)/1000);
        System.out.println("Took: " + totalTimeMs + " microseconds (" + (totalTimeMs/1000) + " milliseconds)");
    }
}
