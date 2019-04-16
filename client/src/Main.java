import java.io.*;
import java.net.Socket;

public class Main {
    private static Socket clientSocket;
    private static BufferedReader consoleReader;
    private static BufferedReader streamReader;
    private static BufferedWriter streamWriter;

    public static void main(String[] args) {
        try {
            clientSocket = new Socket("localhost", 1234);
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (1 == 1) {
            System.out.println("Enter message for server:");
            try {
                String msg = consoleReader.readLine();
                streamWriter.write(msg + "\n");
                streamWriter.flush();
                msg = streamReader.readLine();
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}