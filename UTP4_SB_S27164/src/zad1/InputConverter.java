package zad1;

import java.util.function.Function;

public class InputConverter<T> {
    private final T source;
    public InputConverter(T source)
    {
        this.source = source;
    }

    public <V> V convertBy(Function... functions)
    {
        Object src = source;
        Object result = null;

        for (Function func : functions) {
            result = func.apply(src);
            src = result;
        }
        return (V) result;
    }
}
