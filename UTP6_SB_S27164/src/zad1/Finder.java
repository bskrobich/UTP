/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Finder {
    private final List<String> lines;

    public Finder(String fileName) {
        try (Stream<String> str = Files.lines(Paths.get(fileName))) {
            this.lines = str.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIfCount()
    {
        int count = 0;

        Pattern pattern = Pattern.compile("if\\s*\\(.*?\\)");
        Matcher matcher;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).trim().startsWith("/*"))
            {
                int j = i + 1;
                while (j < lines.size() && !lines.get(j).trim().startsWith("*/")) {
                    j++;
                }
                i = j;
            }
            else if (lines.get(i).trim().startsWith("//"))
            {
                continue;
            }
            else if (lines.get(i).contains("if"))
            {
                String modified = lines.get(i).replaceAll("\"[^\"]*\"", "");

                matcher = pattern.matcher(modified);
                while (matcher.find()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getStringCount (String stringToCount)
    {
        Pattern pattern = Pattern.compile(stringToCount);
        Matcher matcher;
        int count = 0;

        for (String line : lines) {
            if (line.contains(stringToCount)) {
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    count++;
                }
            }
        }
        return count;
    }

}
