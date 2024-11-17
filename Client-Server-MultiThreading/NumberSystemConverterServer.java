import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NumberSystemConverterServer {
    private static final int PORT = 1234;
    private static final int MAX_CLIENTS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Welcome to the Number System Converter Server!");
            String inputLine;
            while (true) {
                out.println("Enter a decimal number (or 'exit' to quit):");
                inputLine = in.readLine();
                if (inputLine == null || "exit".equalsIgnoreCase(inputLine)) {
                    break;
                }
                try {
                    int decimal = Integer.parseInt(inputLine);
                    String binary = Integer.toBinaryString(decimal);
                    String octal = Integer.toOctalString(decimal);
                    String hexadecimal = Integer.toHexString(decimal).toUpperCase();

                    out.println(binary);
                    out.println(octal);
                    out.println(hexadecimal);

                    System.out.println("Client converted: " + decimal);
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter a valid decimal number.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
