package zad2;

import java.io.IOException;
import java.nio.file.*;

public class Futil {
    public static void processDir(String dirName, String resultFileName)
    {
        try {
            MyFileVisitor visitor = new MyFileVisitor(Paths.get("./" + resultFileName));
            Files.walkFileTree(Paths.get(dirName), visitor);
            visitor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
