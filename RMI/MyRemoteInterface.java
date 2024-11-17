import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemoteInterface extends Remote {
    String greet() throws RemoteException;
    double sine(double angle) throws RemoteException;
    double cosine(double angle) throws RemoteException;
    double tangent(double angle) throws RemoteException;
    double cosecant(double angle) throws RemoteException;
    double secant(double angle) throws RemoteException;
    double cotangent(double angle) throws RemoteException;
}

