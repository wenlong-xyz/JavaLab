package rmi.version2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by wenlong on 2016/12/5.
 */
public class ComputeEngine implements Compute{
    public ComputeEngine(){
        super();
    }

    @Override
    public <T> T executeTask(Task<T> t){
        return t.execute();
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null){
           System.setSecurityManager( new SecurityManager());
        }
        try {
            String name = "Compute";
            Compute engine = new ComputeEngine();

            // the second argument, an int, specifies which TCP port to use to listen
            // for incoming remote invocation requests for the object.
            // It is common to use the value zero, which specifies the use of an anonymous port.
            // The actual port will then be chosen at runtime by RMI or the underlying operating system.
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine,0);
            Registry registry  = LocateRegistry.getRegistry();
            registry.rebind(name,stub);
            System.out.println("ComputerEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception");
            e.printStackTrace();
        }
    }
}
