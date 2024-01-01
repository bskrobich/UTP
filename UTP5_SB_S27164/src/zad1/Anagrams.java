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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagrams
{
    private List<String> words;
    public Anagrams(String source)
    {
        this.words = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(source))) {
            this.words = lines
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public List<List<String>> getSortedByAnQty()
    {
        List<List<String>> resultList = new ArrayList<>();

        for (String word : this.words) {
            boolean isFound = false;

            for (List<String> innerList : resultList) {
                String firstWord = innerList.get(0);

                if (isAnagram(word, firstWord)) {
                    isFound = true;
                    innerList.add(word);
                    break;
                }
            }
            if (!isFound) {
                List<String> newInnerList = new ArrayList<>();
                newInnerList.add(word);
                resultList.add(newInnerList);
            }
        }

        resultList.sort( (list1, list2) -> {
            int result = list2.size() - list1.size();
            if (result == 0) {
                return list1.get(0).compareTo(list2.get(0));
            }
            return result;
        });

        return resultList;
    }

    public boolean isAnagram(String s1, String s2)
    {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] a1 = s1.toCharArray();
        Arrays.sort(a1);
        char[] a2 = s2.toCharArray();
        Arrays.sort(a2);

        return Arrays.equals(a1, a2);
    }


    public String getAnagramsFor(String word)
    {
        List<List<String>> listOfLists = this.getSortedByAnQty();
        List<String> resultList = new ArrayList<>();

        for (List<String> list : listOfLists) {
            if (list.contains(word)) {
                resultList.addAll(list);
                resultList.remove(word);
                break;
            }
        }
        return word + ": " + resultList;
    }
}
