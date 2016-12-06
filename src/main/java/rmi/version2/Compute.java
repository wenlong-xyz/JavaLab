package rmi.version2;


import java.rmi.Remote;
import java.rmi.RemoteException;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by wenlong on 2016/12/5.
 */
public interface Compute extends Remote {
    <T> T executeTask(Task<T> t) throws RemoteException;
}
