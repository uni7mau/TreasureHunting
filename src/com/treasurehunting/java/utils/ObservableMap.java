package com.treasurehunting.java.utils;

import java.util.*;
import java.util.function.Consumer;

public class ObservableMap<T> implements IObservableMap<T> {

    private final HashMap<Integer, T> list;
    public Consumer<HashMap<Integer, T>> anyValueChanged;

    // Constructor
    public ObservableMap(HashMap<Integer, T> initialList) {
        this.list = (initialList != null) ? new HashMap<>(initialList) : new HashMap<>();
    }

    // Thêm constructor không tham số
    public ObservableMap() {
        this.list = new HashMap();
    }

    // Lấy phần tử tại vị trí index
    public T get(int index) {
        return list.get(index);
    }

    // Gán giá trị tại vị trí index
    public void set(int index, T value) {
        list.put(index, value);
        invoke();
    }

    // Đăng ký sự kiện thay đổi giá trị
    public void setOnAnyValueChanged(Consumer<HashMap<Integer, T>> listener) {
        this.anyValueChanged = listener;
    }

    // Gọi sự kiện khi danh sách thay đổi
    private void invoke() {
        if (anyValueChanged != null) {
            anyValueChanged.accept((HashMap<Integer, T>) Collections.unmodifiableMap(list));
        }
    }

    // Số lượng phần tử trong danh sách
    public int size() {
        return list.size();
    }

    // Kiểm tra danh sách có rỗng không
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Thêm phần tử vào danh sách
    public void add(int key, T item) {
        list.put(key, item);
        invoke();
    }

    // Xóa tất cả phần tử trong danh sách
    public void clear() {
        list.clear();
        invoke();
    }

    // Kiểm tra sự tồn tại của phần tử
    public Boolean contains(T item) {
        return list.containsValue(item);
    }

    @Override
    public int indexOf(T item) {
//        return list.in
        // bro it's wrong, don't use this
        return 0;
    }

    // Xóa phần tử khỏi danh sách
    public Boolean remove(T item) {
//        boolean result = list.remove(item);
//        if (result) {
//            invoke();
//        }
//        return result;
        // I don't get it 🔥

        return false;
    }

    @Override
    public Iterator<T> getEnumerator() {
        return list.values().iterator();
    }

    // Thêm phần tử vào vị trí cụ thể
    public void insert(int index, T item) {
        list.put(index, item);
        invoke();
    }

    // Xóa phần tử tại vị trí cụ thể
    public void removeAt(int index) {
        list.remove(index);
        invoke();
    }

    // Sao chép danh sách vào mảng
    public void copyTo(T[] array, int arrayIndex) {
        for (int i = 0; i < list.size(); i++) {
            array[arrayIndex + i] = list.get(i);
        }
    }

//    // Lấy danh sách không thể thay đổi
//    public HashMap<Integer, T> asUnmodifiableList() {
//        return Collections.unmodifiableNavigableMap(list);
//    }

}