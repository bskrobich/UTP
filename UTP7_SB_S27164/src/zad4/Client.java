package zad4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void start() {

        try {
            Socket socket = new Socket("localhost", 5252);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.println("[CLIENT]: Connected to server.");

            while (true) {
                System.out.print("[CLIENT]: Type the command: (Hello, Date, Time, Exit): ");
                String command = scanner.nextLine();
                if (command.equals("Exit"))
                    System.exit(0);

                bw.write(command + '\n');
                bw.flush();
                String response = br.readLine();
                System.out.println("[CLIENT] Server response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
