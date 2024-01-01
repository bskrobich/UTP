package zad4;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
    private T val;
    public Maybe() { }
    public Maybe(T val)
    {
        this.val = val;
    }

    public static <U> Maybe<U> of(U val)
    {
        return new Maybe<>(val);
    }

    public void ifPresent(Consumer<T> cons)
    {
        if (val != null)
            cons.accept(val);
    }

    public <V> Maybe<V> map(Function<T, V> func)
    {
        if (val != null)
            return new Maybe<>(func.apply(val));
        else
            return new Maybe<>();
    }

    public T get()
    {
        if (val != null)
            return val;
        else
            throw new NoSuchElementException(" maybe is empty");
    }

    public boolean isPresent()
    {
        return val != null;
    }

    public T orElse(T defVal)
    {
        if (val != null)
            return val;
        else
            return defVal;
    }

    public Maybe<T> filter(Predicate<T> pred)
    {
        if (val == null || pred.test(val))
            return this;
        else
            return new Maybe<>();
    }

    @Override
    public String toString()
    {
        if (val != null)
            return "Maybe has value " + val;
        else
            return "Maybe is empty";

    }
}
