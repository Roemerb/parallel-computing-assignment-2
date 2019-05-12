public class Main {

    public static final int MAX_VALUE = 5000000;

    public static void main(String[] args) throws Exception {
        System.out.println("Testing serial implementation");
        System.out.println(String.format("%s\t\t\t%s\t\t%s", "Elements", "Micro S.", "Micro S."));
        testWithNElementsSerial(1000);
        testWithNElementsSerial(10000);
        testWithNElementsSerial(100000);
        testWithNElementsSerial(1000000);
        testWithNElementsSerial(10000000);

        System.out.println("Testing serial implementation");
        System.out.println(String.format("%s\t\t\t%s\t\t%s", "Elements", "Micro S.", "Micro S."));
        testWithNElementsParallel(1000);
        testWithNElementsParallel(10000);
        testWithNElementsParallel(100000);
        testWithNElementsParallel(1000000);
        testWithNElementsParallel(10000000);
    }

    public static void testWithNElementsSerial(int n)
    {
        HeapSortSerial hss = new HeapSortSerial();

        int[] arr = Utils.createTestSet(n, Main.MAX_VALUE);

        long startTime = System.nanoTime();
        hss.sort(arr);
        long endTime = System.nanoTime();
        long totalTimeMs = ((endTime - startTime)/1000);
        System.out.println(String.format("%d\t\t\t%d\t\t\t%d", n, totalTimeMs, (totalTimeMs/1000)));
    }

    public static void testWithNElementsParallel(int n)
    {
        HeapSortParallel hsp = new HeapSortParallel();

        int[] arr = Utils.createTestSet(n, Main.MAX_VALUE);

        long startTime = System.nanoTime();
        hsp.sort(arr);
        long endTime = System.nanoTime();
        long totalTimeMs = ((endTime - startTime)/1000);
        System.out.println(String.format("%d\t\t\t%d\t\t\t%d", n, totalTimeMs, (totalTimeMs/1000)));
    }
}
