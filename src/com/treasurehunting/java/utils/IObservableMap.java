package com.treasurehunting.java.utils;

import java.util.Iterator;

public interface IObservableMap<T> {

    void add(int key, T item);
    void insert(int index, T item);
    void clear();
    Boolean contains(T item);
    int indexOf(T item);
    void copyTo(T[] array, int arrayIndex);
    Boolean remove(T item);
    Iterator<T> getEnumerator();
    void removeAt(int index);

}