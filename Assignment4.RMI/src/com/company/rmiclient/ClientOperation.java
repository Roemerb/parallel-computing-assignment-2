package com.company.rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.company.rmiinterface.RMIInterface;

public class ClientOperation {

    private static RMIInterface look_up;

    public static void main(String[] args)
            throws MalformedURLException, RemoteException, NotBoundException {

        look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
        HeapSortSerial heapSort = new HeapSortSerial();

        while(true) {

            int[] chunk = look_up.requestChunk();
            if (chunk.length != 0){
                chunk = heapSort.sort(chunk);
                look_up.sendSortedChunk(chunk);
            }

            int[][] sortedChunks = look_up.requestSortedChunks();
            if(sortedChunks == null){
                continue;
            }else{
               int[] sortedArray = MergeKArrays.mergeKSortedArray(sortedChunks);
               look_up.sendSortedArray(sortedArray);
            }

            if(!look_up.requestJobAvailability()){
                break;
            }
        }

    }

}