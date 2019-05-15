package Roemerb.ParallelComputing.Assignment3;

import java.util.Random;

public class Utils {
    static int[] createTestSet(int length, int max)
    {
        Random r = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++)
        {
            arr[i] = r.nextInt((max - 0) + 1);
        }

        return arr;
    }

    /**
     * Checks if arr is actually sorted by comparing every element with the last one
     *
     * @param arr
     * @throws Exception
     */
    static void checkSort(int arr[]) throws Exception {
        int last = arr[0];
        for (int i = 0; i < arr.length; i++)
        {
            // Skip the first index as there is no last to compare with
            if (i == 0)
            {
                continue;
            }

            if (arr[i] < last)
            {
                throw new Exception(
                        String.format("Sort error at index %d: %d is smaller than last index %d", i, arr[i], last)
                );
            }
            last = arr[i];
        }
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[], int breakAfterLines)
    {
        int n = arr.length;
        int printed = 0;
        for (int i=0; i<n; ++i)
        {
            if (breakAfterLines != 0 && printed == breakAfterLines)
            {
                System.out.print("\n");
                printed = 0;
            }
            System.out.print(arr[i]+"\t");
            printed++;
        }
        System.out.println();
    }

    public static int[][] divideArray(int[] arr, int slices)
    {
        int sliceSize = (int)Math.floor((double)arr.length / slices);
        int[][] output = new int[slices][];

        for (int i = 0; i < slices; ++i)
        {
            int start = i * sliceSize;
            int length = Math.min(arr.length - start, sliceSize);

            int[] temp = new int[length];
            System.arraycopy(arr, start, temp, 0, length);
            output[i] = temp;
        }

        return output;
    }
}
