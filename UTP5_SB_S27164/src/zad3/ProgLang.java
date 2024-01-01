package zad3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProgLang {
    private List<List<String>> languages;

    public ProgLang(String source) {
        this.languages = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(source))) {
            languages = lines
                    .map(line -> Arrays.asList(line.split("\t")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public Map<String, Set<String>> getLangsMap() {
        Map<String, Set<String>> resultMap = new LinkedHashMap<>();

        for (List<String> list : this.languages) {
            String key = list.get(0);
            Set<String> values = new LinkedHashSet<>();
            for (int i = 1; i < list.size(); i++) {
                values.add(list.get(i));
                resultMap.put(key, values);
            }
        }
        return resultMap;
    }

    public void checkIfExists(Map<String, Set<String>> map, String key, String value) {
        Set<String> set = map.computeIfAbsent(key, k -> new LinkedHashSet<>());
        set.add(value);
    }

    public Map<String, Set<String>> getProgsMap() {
        Map<String, Set<String>> resultMap = new LinkedHashMap<>();

        for (List<String> list : this.languages) {
            for (int i = 1; i < list.size(); i++) {
                checkIfExists(resultMap, list.get(i), list.get(0));
            }
        }
        return resultMap;
    }

    public Map<String, Set<String>> getLangsMapSortedByNumOfProgs() {
        return this.sorted(this.getLangsMap(), (l1, l2) -> {
            int result = l2.getValue().size() - l1.getValue().size();
            if (result == 0)
                return l1.getKey().compareTo(l2.getKey());
            return result;
        });
    }

    public Map<String, Set<String>> getProgsMapSortedByNumOfLangs() {
        return this.sorted(this.getProgsMap(), (p1, p2) -> {
            int result = p2.getValue().size() - p1.getValue().size();
            if (result == 0)
                return p1.getKey().compareTo(p2.getKey());
            return result;
        });
    }

    public Map<String, Set<String>> getProgsMapForNumOfLangsGreaterThan(int min)
    {
        return this.filtered(this.getProgsMap(), p -> p.getValue().size() > min);
    }

    public <T, V> Map<T, V> sorted(Map<T, V> srcMap, Comparator<Map.Entry<T, V>> comparator) {
        return srcMap
                .entrySet()
                    .stream()
                        .sorted(comparator)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    public <T, V> Map<T,V> filtered(Map<T, V> srcMap, Predicate<Map.Entry<T, V>> predicate) {
        return srcMap
                .entrySet()
                    .stream()
                        .filter(predicate)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

}
