import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MyRMIServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1096); // Create the RMI registry
            MyRemoteImpl remoteObj = new MyRemoteImpl();
            Naming.rebind("rmi://localhost:1096/MyRemoteObject", remoteObj);
            System.out.println("Server has started......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

