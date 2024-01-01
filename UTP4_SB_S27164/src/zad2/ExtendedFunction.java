package zad2;

import java.util.function.Function;

@FunctionalInterface
public interface ExtendedFunction<T,R> extends Function<T,R> {
    R checkedApply(T arg) throws Exception;

    default R apply(T arg) {
        try {
            return checkedApply(arg);
        } catch (RuntimeException exc) {
            throw exc;
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

}
