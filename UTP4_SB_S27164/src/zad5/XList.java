package zad5;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList<T> extends ArrayList<T> {

    public XList(Collection<T> c) {
        super(c);
    }
    @SafeVarargs
    public XList(T... arg)
    {
        Collections.addAll(this, arg);
    }

    @SafeVarargs
    public static <U> XList<U> of(U... arg)
    {
        return new XList<>(arg);
    }

    public static <U> XList<U> of(Collection<U> collection)
    {
        return new XList<>(collection);
    }

    public static XList<String> charsOf(String str)
    {
        return XList.of(str.split(""));
    }

    public static XList<String> tokensOf(String str, String separator)
    {
        return XList.of(str.split(separator));
    }

    public static XList<String> tokensOf(String str)
    {
        return XList.tokensOf(str, " ");
    }

    public XList<T> copy()
    {
        return XList.of(this);
    }

    public XList<T> union(Collection<T> collection)
    {
        XList<T> resultList = this.copy();
        resultList.addAll(collection);
        return resultList;
    }

    @SafeVarargs
    public final XList<T> union(T... arg)
    {
        return this.union(XList.of(arg));
    }

    public XList<T> diff(Collection<T> collection)
    {
        XList<T> resultList = new XList<>();

        for (T el : this){
            if (!collection.contains(el))
                resultList.add(el);
        }
        return resultList;
    }

    @SafeVarargs
    public final XList<T> diff(T... arg)
    {
        return this.diff(XList.of(arg));
    }

    public XList<T> unique()
    {
        return XList.of(new LinkedHashSet<>(this));
    }


    public <R> XList<R> collect(Function<T, R> func)
    {
        XList<R> resultList = new XList<>();

        for (T el : this) {
            resultList.add(func.apply(el));
        }
        return resultList;
    }

    public String join(String separator)
    {
        return this.stream().map(Object::toString).collect(Collectors.joining(separator));
    }

    public void forEachWithIndex(BiConsumer<T, Integer> biConsumer)
    {
        for (int i = 0; i < this.size(); i++)
            biConsumer.accept(this.get(i), i);
    }















}
