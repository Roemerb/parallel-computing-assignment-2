package Roemerb.ParallelComputing.Assignment4;

/**
 * HeapSortSerial is a simple implementation of the Heap Sort algorithm, directly implemented from the pseudocode.
 *
 * @class HeapSortSerial
 */
public class HeapSortSerial {

    /**
     * The main sorting method
     *
     * @param arr The array to be sorted
     * @return
     */
    public int[] sort(int[] arr)
    {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
        {
            arr = heapify(arr, n, i);
        }

        for (int i = n-1; i>=0; i--)
        {
            arr = swap(arr, 0, i);
            arr = heapify(arr, i, 0);
        }

        return arr;
    }

    /**
     * Creates a sorted heap of arr
     *
     * @param arr
     * @param n
     * @param i
     * @return
     */
    public int[] heapify(int arr[], int n, int i)
    {
        int max = i;
        int left = 2*i+1;
        int right = 2*i+2;

        if (left < n && arr[left] > arr[max])
            max = left;

        if (right < n && arr[right] > arr[max])
            max = right;

        if (max != i)
        {
            arr = swap(arr, i, max);
            arr = heapify(arr, n, max);
        }

        return arr;
    }

    /**
     * Simple swap method to swap 2 elements in an array
     *
     * @param arr
     * @param i
     * @param j
     * @return
     */
    private int[] swap(int arr[], int i, int j)
    {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
        return arr;
    }
}
