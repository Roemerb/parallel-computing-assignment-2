package com.company.rmiserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.company.rmiinterface.RMIInterface;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface{

    private static final long serialVersionUID = 1L;
    private static final int ARRAY_SIZE = 10000;
    private static final int MAX_VALUE = 1000000;
    private static final int SLICE_SIZE = 100;

    private static List<int[]> dividedArr;
    private static List<int[]> sortedList;

    protected ServerOperation() throws RemoteException {
        super();
    }

    @Override
    public boolean requestJobAvailability() throws RemoteException {
        return sortedList.get(0).length == ARRAY_SIZE;
    }

    @Override
    public int[] requestChunk() throws RemoteException{
        if (dividedArr.isEmpty()) {
            return new int[0];
        } else {
            int[] chunk = dividedArr.remove(0);
            return chunk;
        }

    }

    @Override
    public void sendSortedChunk(int[] sortedChunk) throws RemoteException {
        sortedList.add(sortedChunk);
    }

    @Override
    public int[][] requestSortedChunks() throws RemoteException{
        if(sortedList.size() > 2){
            int[][] sortedArr = new int[sortedList.size()][];
            sortedList.toArray(sortedArr);
            sortedList = new ArrayList<>();
            return sortedArr;
        }
        return null;
    }

    @Override
    public void sendSortedArray(int[] sortedArray) throws RemoteException{
        sortedList.add(sortedArray);
    }

    public static void main(String[] args){


        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("rmiregistry &");
            Naming.rebind("//localhost/HeapSortServer", new ServerOperation());
            System.err.println("Server ready");
            System.out.println("\nCreating test set with " + ARRAY_SIZE + " elements...");
            int[] arr = Utils.createTestSet(ARRAY_SIZE, MAX_VALUE);
            dividedArr = Utils.divideArray(arr, SLICE_SIZE, ARRAY_SIZE/SLICE_SIZE);
            System.out.println("Divided array is ready...");
            sortedList = new ArrayList<>();
            while(true){
                if(sortedList.size() != 0 && sortedList.get(0).length == ARRAY_SIZE){
                    Utils.checkSort(sortedList.get(0));
                    Utils.printArray(sortedList.get(0), 1);
                    break;
                }
            }

        } catch (Exception e) {

            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();

        }

    }

}
