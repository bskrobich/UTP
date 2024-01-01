package zad1;

import java.util.ArrayList;
import java.util.List;

public class Letters extends Thread
{
    private final List<Thread> threads = new ArrayList<>();

    public Letters(String word)
    {
        for (char letter : word.toCharArray()) {
            Runnable thread = () -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(letter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            };
            threads.add(new Thread(thread, "Thread " + letter));
        }
    }

    public List<Thread> getThreads()
    {
        return this.threads;
    }

}
