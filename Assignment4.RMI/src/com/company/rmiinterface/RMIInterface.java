package com.company.rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    public boolean requestJobAvailability() throws RemoteException;
    public int[] requestChunk() throws RemoteException;
    public void sendSortedChunk(int[] sortedChunk) throws RemoteException;
    public int[][] requestSortedChunks() throws RemoteException;
    public void sendSortedArray(int[] sortedArray) throws RemoteException;

}
