package com.exozet.android.core.utils;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class CollectionExtensions {

    private CollectionExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static <T> Collection<T> intersect(@NonNull final Collection<? extends T> a, @NonNull final Collection<? extends T> b) {
        final Collection<T> result = new ArrayList<T>();
        for (final T t : a) {
            if (b.remove(t)) result.add(t);
        }
        return result;
    }

    public static boolean isEmpty(@Nullable final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static int size(@Nullable final Collection<?> collection) {
        return isEmpty(collection)
                ? 0
                : collection.size();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] l) {
        return l == null || l.length < 1;
    }

    public static boolean isEmpty(@Nullable final SparseArray<?> collection) {
        return collection == null || collection.size() == 0;
    }
}
