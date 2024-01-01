/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad1;


import java.util.*;

public class Main {

  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
            .when(
                    (String el) -> el.startsWith("WAW")
            )
            .mapEvery(
                    (String el) -> {
                        String[] line = el.split(" ");
                        int price = Integer.parseInt(line[2]);
                        int priceInPLN = (int)(price * xrate);
                        return "to " + line[1] + " - price in PLN:  " + priceInPLN;
                    }
                    );
  }

  public static void main(String[] args) {
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}
