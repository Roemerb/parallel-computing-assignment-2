public class HeapSortSerial {

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

    private int[] swap(int arr[], int i, int j)
    {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
        return arr;
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    public static void main(String[] args)
    {
        int arr[] = {12, 11, 13, 5, 6, 7};

        HeapSortSerial hs = new HeapSortSerial();

        printArray(hs.sort(arr));
    }
}
