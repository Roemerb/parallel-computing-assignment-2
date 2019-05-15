package Roemerb.ParallelComputing.Assignment3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKArrays
{
    public static int[] mergeKSortedArray(List<int[]> arr) {
        PriorityQueue<ArrayContainer> q = new PriorityQueue<ArrayContainer>();
        int total = 0;

        Iterator<int[]> i = arr.iterator();
        while (i.hasNext()) {
            int[] sub = i.next();
            q.add(new ArrayContainer(sub, 0));
            total = total + sub.length;
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
