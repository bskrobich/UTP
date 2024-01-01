package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator <T> {

    List<T> list;

    public ListCreator(List<T> list)
    {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> srcList)
    {
        return new ListCreator<>(srcList);
    }

    public ListCreator<T> when(Predicate<T> pred)
    {
        List<T> resultList = new ArrayList<>();

        for (T el : list) {
            if (pred.test(el))
                resultList.add(el);
        }
        list = resultList;
        return this;
    }

    public <U> List<U> mapEvery(Function<T, U> func)
    {
        List<U> resultList = new ArrayList<>();

        for (T el : list) {
            resultList.add(func.apply(el));
        }
        return resultList;
    }
}
