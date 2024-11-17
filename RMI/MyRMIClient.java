import java.rmi.Naming;
import java.util.Scanner;

public class MyRMIClient {
    public static void main(String[] args) {
        try {
            MyRemoteInterface remoteObj = (MyRemoteInterface) Naming.lookup("rmi://localhost:1096/MyRemoteObject");
            
            // Print the greeting
            System.out.println(remoteObj.greet());
            
            // Get angle input from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the angle in degrees : ");
            double angle = scanner.nextDouble();
            
            // Calculate trigonometric values
            System.out.println("Sine: " + remoteObj.sine(angle));
            System.out.println("Cosine: " + remoteObj.cosine(angle));
            System.out.println("Tangent: " + remoteObj.tangent(angle));
            System.out.println("Cosecant: " + remoteObj.cosecant(angle));
            System.out.println("Secant: " + remoteObj.secant(angle));
            System.out.println("Cotangent: " + remoteObj.cotangent(angle));
            
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

