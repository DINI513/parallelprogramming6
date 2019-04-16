import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket = serverSocket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inStream;
        BufferedReader dataInReader;
        DataOutputStream dataOutStream;
        try {
            inStream = socket.getInputStream();
            dataInReader = new BufferedReader(new InputStreamReader(inStream));
            dataOutStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }

        String line;
        while (true) {
            try {
                line = dataInReader.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    System.out.println("Client disconnected");
                    return;
                } else {
                    System.out.println("Operation " + line + " received");
                    ExecutorService executorService = Executors.newFixedThreadPool(3);
                    for(int i = 1; i < 3; i++) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    executorService.shutdown();
                    try {
                        executorService.awaitTermination(1, TimeUnit.HOURS);
                        System.out.println("Operation " + line + " done");
                        dataOutStream.writeBytes("Operation " + line + " done\n");
                        dataOutStream.flush();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}