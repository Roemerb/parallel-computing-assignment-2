public class Main {
    public static void main(String[] args) throws Exception {
        HeapSortSerial hss = new HeapSortSerial();
        System.out.println("Sorting array serially");
        int[] arr = hss.sort(Utils.createTestSet(5000, 10000));
        System.out.println("Checking if array is properly sorted...");
        Utils.checkSort(arr);
        System.out.println("Sorted correctly! Here's the sorted array:");
        Utils.printArray(arr, 25);
    }
}
