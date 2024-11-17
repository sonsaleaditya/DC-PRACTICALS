import java.io.*;
import java.net.*;

public class TcpClient {
    private static final String SERVER_ADDRESS = "192.168.177.193"; // Server IP
    private static final int PORT = 1234; // Server port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            // Read and print welcome message from server
            System.out.println(in.readLine());

            String userInput;
            while (true) {
                // Read input from server
                System.out.println(in.readLine());

                // Get input from user
                userInput = stdIn.readLine();
                out.println(userInput);

                // Exit condition
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                // Read and print conversion results
                System.out.println("Binary: " + in.readLine());
                System.out.println("Octal: " + in.readLine());
                System.out.println("Hexadecimal: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
