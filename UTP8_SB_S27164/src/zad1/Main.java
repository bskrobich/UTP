/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */
package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class Main
{
    private static final Lock lock = new ReentrantLock();
    private volatile static Towar towar;
    private static int totalWeight;
    private volatile static boolean isCompleted;
    private static int createdObjects;


    public static void main(String[] args)
    {
        String filePath = "../Towary.txt";

        Thread threadA = new Thread(() -> createObjects(filePath));
        Thread threadB = new Thread(Main::calculateWeight);

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        
        System.out.println(totalWeight);
    }

    private static void createObjects(String filePath)
    {
        createdObjects = 0;
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                synchronized (lock) {
                    lock.lock();
                    try {
                        String[] data = line.split(" ");
                        int id = Integer.parseInt(data[0]);
                        int weight = Integer.parseInt(data[1]);
                        towar = new Towar(id, weight);
                    } finally {
                        lock.unlock();
                    }
                    if (createdObjects != 0 && createdObjects % 200 == 0)
                        System.out.println("utworzono " + createdObjects + " obiektów");

                    createdObjects++;
                    lock.notifyAll();

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            });
            isCompleted = true;
            synchronized (lock) {
                lock.notifyAll();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void calculateWeight()
    {
        int countedObjects = 0;
        while (!isCompleted) {
            synchronized (lock) {
                if (countedObjects != 0 && countedObjects % 100 == 0)
                    System.out.println("policzono wage " + countedObjects + " towarów");

                lock.lock();
                try {
                    if (towar != null)
                        totalWeight += towar.getWeight();
                } finally {
                    lock.unlock();
                }
                countedObjects++;
                lock.notifyAll();

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }

}
