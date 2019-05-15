package Roemerb.ParallelComputing.Assignment2;

import java.lang.reflect.Array;
import java.util.PriorityQueue;

public class MergeKArrays
{
    public static int[] mergeKSortedArray(int[][] arr) {
        PriorityQueue<ArrayContainer> q = new PriorityQueue<ArrayContainer>();
        int total = 0;

        for (int i = 0; i < arr.length; i++) {
            q.add(new ArrayContainer(arr[i], 0));
            total = total + arr[i].length;
        }

        int m = 0;
        int result[] = new int[total];

        while (!q.isEmpty()) {
            ArrayContainer ac = q.poll();
            result[m++] = ac.arr[ac.index];

            if (ac.index < ac.arr.length-1) {
                q.add(new ArrayContainer(ac.arr, ac.index+1));
            }
        }

        return result;
    }
}
