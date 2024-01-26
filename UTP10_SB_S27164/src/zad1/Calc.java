/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

public class Calc {
    private final Map<Character, BinaryOperator<BigDecimal>> operations = new HashMap<>();

    public Calc() {
        operations.put('+', BigDecimal::add);
        operations.put('-', BigDecimal::subtract);
        operations.put('*', BigDecimal::multiply);
        operations.put('/', (num1, num2) -> num1.divide(num2, new MathContext(8)));
    }

    public String doCalc(String cmd) {
        try {
            String[] args = cmd.split("\\s+");

            BigDecimal num1 = new BigDecimal(args[0]);
            char operator = args[1].charAt(0);
            BigDecimal num2 = new BigDecimal(args[2]);

            BinaryOperator<BigDecimal> operation = operations.get(operator);
            BigDecimal result = operation.apply(num1, num2);
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Invalid command to calc");
        }
    }

}
