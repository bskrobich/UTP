package zad4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.*;

public class Server {
    private static int port = 5252;
    private static final ExecutorService service = Executors.newFixedThreadPool(5);

    public static void start() throws IOException {
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("[SERVER]: Connection ready");

            while (true) {
                Socket cs = ss.accept();
                FutureTask<Void> task = new FutureTask<>(() -> clientHandler(cs));
                service.submit(task);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            service.shutdown();
        }
    }

    public static Void clientHandler(Socket cs) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream()));

            String input;
            while ((input = br.readLine()) != null) {
                System.out.println("[SERVER] Task received: " + input);

                switch (input) {
                    case "Hello":
                        bw.write("Hello Client!\n");
                        break;
                    case "Date":
                        bw.write(String.valueOf(LocalDate.now()) + '\n');
                        break;
                    case "Time":
                        bw.write(String.valueOf(LocalTime.now()) + '\n');
                        break;
                    default:
                        bw.write("Answer for the task not found\n");
                        break;
                }
                bw.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try{
                cs.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }
}
