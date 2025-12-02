import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

// To recompile files and run server:
// javac src/*.java && java -cp src OutServer
public class OutServer {
    
    public static void main(String[] args) {
        try {
        writeToSocket(12345);
        } catch(IOException e) {
            System.err.println("Error writing to socket");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // Thread thread1 = new Thread(() -> spam("Hello"));
        // thread1.start();

        // Thread thread2 = new Thread(() -> spam("World!"));
        // thread2.start();
    }

    public static void spam(String msg){
        while (true) { 
                System.out.println(msg);   
            try {
                Thread.sleep(1000);                    
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }     
        }
    }

    public static void writeToSocket(int port) throws IOException {

        try (ServerSocket server = new ServerSocket(port);){
            while(true){
                System.out.println("Waiting for client to connect...");
                Socket socket = server.accept();
                Thread clientThread = new Thread(() ->  handleClient(socket));
                clientThread.start();
            }
        }
    }

    public static void handleClient(Socket socket){
        try{
            System.out.println("Client connected!");

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, java.nio.charset.StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                bufferedWriter.write("Hello");
                bufferedWriter.newLine();
                bufferedWriter.flush();

                // try {
                //     Thread.sleep(500);
                // } catch (InterruptedException e) {
                //     Thread.currentThread().interrupt();
                //     System.out.println("Client interrupted");
                //     return;
                // }
            }
        }catch(IOException e){
            System.out.println("Client disconnected");
            return;
        }
    }
}