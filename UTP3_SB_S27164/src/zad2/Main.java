/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = dest
            .stream()
            .filter(el -> el.startsWith("WAW"))
            .map(el -> {
              String[] line = el.split(" ");
              int price = Integer.parseInt(line[2]);
              int priceInPLN = (int)(price * ratePLNvsEUR);
              return "to " + line[1] + " - price in PLN:  " + priceInPLN;
            })
            .collect(Collectors.toList());

    for (String r : result) System.out.println(r);
  }
}
