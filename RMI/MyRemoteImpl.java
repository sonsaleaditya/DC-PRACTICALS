import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemoteInterface {
    public MyRemoteImpl() throws RemoteException {
        super();
    }

    public String greet() {
        return "Hii, i am aditya";
    }

    public double sine(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    public double cosine(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public double tangent(double angle) {
        return Math.tan(Math.toRadians(angle));
    }

    public double cosecant(double angle) throws RemoteException {
        double sineValue = sine(angle);
        if (sineValue == 0) {
            throw new RemoteException("Cosecant is undefined for this angle (division by zero).");
        }
        return 1 / sineValue;
    }

    public double secant(double angle) throws RemoteException {
        double cosineValue = cosine(angle);
        if (cosineValue == 0) {
            throw new RemoteException("Secant is undefined for this angle (division by zero).");
        }
        return 1 / cosineValue;
    }

    public double cotangent(double angle) throws RemoteException {
        double tangentValue = tangent(angle);
        if (tangentValue == 0) {
            throw new RemoteException("Cotangent is undefined for this angle (division by zero).");
        }
        return 1 / tangentValue;
    }
}

