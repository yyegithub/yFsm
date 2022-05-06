package fun.ycx.fsm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class ConvenientSet<E> extends HashSet<E> {
    public static <T> ConvenientSet<T> of(T element) {
        ConvenientSet<T> set = new ConvenientSet<>();
        set.add(element);
        return set;
    }

    public static <T> ConvenientSet<T> of(Collection<T> elements) {
        ConvenientSet<T> set = new ConvenientSet<>();
        set.addAll(elements);
        return set;
    }

    @SafeVarargs
    public static <T> ConvenientSet<T> of(T... elements) {
        ConvenientSet<T> set = new ConvenientSet<>();
        set.addAll(Arrays.asList(elements));
        return set;
    }

    public static <T> ConvenientSet<T> empty() {
        return new ConvenientSet<>();
    }
}
