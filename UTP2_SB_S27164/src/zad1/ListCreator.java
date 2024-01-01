/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> { // Uwaga: klasa musi być sparametrtyzowana
    List<T> list;

    public ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Selector<T> selector) {
        List<T> resultList = new ArrayList<>();
        for (T element : list) {
            if (selector.select(element))
                resultList.add(element);
        }
        list = resultList;
        return this;
    }

    public <U> List<U> mapEvery(Mapper<U, T> mapper) {
        List<U> resultList = new ArrayList<>();
        for (T element : list) {
            resultList.add(mapper.map(element));
        }
        return resultList;
    }
}
