package zad2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileVisitor implements FileVisitor<Path> {
    private final BufferedWriter writer;

    public MyFileVisitor(Path resultFileName) throws IOException
    {
        OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(Paths.get(resultFileName.toString())), StandardCharsets.UTF_8);
        writer = new BufferedWriter(osw);
    }
    public void close() throws IOException
    {
        writer.close();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        if (!file.getFileName().toString().equals(".DS_Store")) {
            InputStreamReader isr = new InputStreamReader(Files.newInputStream(Paths.get(file.toString())), "cp1250");
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + '\n');
            }
            reader.close();
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
    {
        System.err.println(exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
    {
        return FileVisitResult.CONTINUE;
    }

}
