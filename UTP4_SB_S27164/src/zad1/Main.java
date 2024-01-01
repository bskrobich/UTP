/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {

    Function<String, List<String>> flines = filePath -> {
      List<String> resultList;

      try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
        resultList = lines.collect(Collectors.toList());
      } catch (IOException e) {
        throw new RuntimeException();
      }
      return resultList;
    };

    Function<List<String>, String> join = srcList -> String.join("", srcList);

    Function<String, List<Integer>> collectInts = str -> {
      List<Integer> resultList = new ArrayList<>();

      Pattern pattern = Pattern.compile("[0-9]+");
      Matcher matcher = pattern.matcher(str);

      while (matcher.find()) {
        resultList.add(Integer.parseInt(matcher.group()));
      }
      return resultList;
    };

    Function<List<Integer>, Integer> sum = list -> {
      int sumOfInt = 0;

      for (int el : list) {
        sumOfInt += el;
      }
      return sumOfInt;
    };

    String fname = System.getProperty("user.home") + "/LamComFile.txt";
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}
