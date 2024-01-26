/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad2;


import java.beans.PropertyVetoException;

public class Main {
  public static void main(String[] args) {

    Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
    System.out.println(purch);

    purch.addPropertyChangeListener(p ->
            System.out.println(
                    "Change value of: " + p.getPropertyName()
                    + " from: " + p.getOldValue()
                    + " to: " + p.getNewValue()
            ));

    purch.addVetoableChangeListener(p -> {
      if (p.getPropertyName().equals("price") && (Double)p.getNewValue() < 1000)
          throw new PropertyVetoException("Price change to: " + p.getNewValue() + " not allowed", p);
    });

    try {
      purch.setData("w promocji");
      purch.setPrice(2000.00);
      System.out.println(purch);

      purch.setPrice(500.00);

    } catch (PropertyVetoException exc) {
      System.out.println(exc.getMessage());
    }
    System.out.println(purch);


  }
}
