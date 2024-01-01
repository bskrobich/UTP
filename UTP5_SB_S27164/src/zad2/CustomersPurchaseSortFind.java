/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomersPurchaseSortFind
{
    private List<Purchase> listOfPurchases;

    public void readFile(String fileName) {
        this.listOfPurchases = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            listOfPurchases = lines
                    .map(line -> {
                        String[] purchase = line.split(";");
                        return new Purchase(
                                purchase[0], purchase[1], purchase[2], Double.parseDouble(purchase[3]), Double.parseDouble(purchase[4]));
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public Comparator<Purchase> compareBy(String criterion) {

        if (criterion.equals("Nazwiska")) {
            return (p1, p2) -> {
                int result = p1.getCustomerName().compareTo(p2.getCustomerName());

                if (result == 0)
                    return p1.getCustomerId().compareTo(p2.getCustomerId());

                return result;
            };
        } else if (criterion.equals("Koszty")) {
            return (p1, p2) -> {
                double result = (p2.getPrice() * p2.getQuantity()) - (p1.getPrice() * p1.getQuantity());

                if (result == 0)
                    return p1.getCustomerId().compareTo(p2.getCustomerId());

                return (int) result;
            };
        } else
            return (p1, p2) -> 0;
    }

    public void showSortedBy(String criterion) {
        System.out.println('\n' + criterion);

        this.listOfPurchases
                .stream()
                .sorted(compareBy(criterion))
                .forEach(p ->
                        System.out.println(p.toString(criterion.equals("Koszty")))
                );
    }

    public void showPurchaseFor(String customerId) {
        System.out.println("\nKlient " + customerId);

        for (Purchase p : this.listOfPurchases) {
            if (p.getCustomerId().equals(customerId))
                System.out.println(p);
        }
    }
}
