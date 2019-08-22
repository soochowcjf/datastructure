package com.javalanguage.chapter3;

import java.util.Iterator;

/**
 * @author cjf on 2019/8/18 23:37
 */
public class MyArrayList<E> implements Iterable<E> {

    private static final int DEFAULT_SIZE = 8;

    private Object[] arr;

    private int size;

    public MyArrayList() {
        arr = new Object[DEFAULT_SIZE];
        size = 0;
    }

    void encureCapacity() {
        if (arr.length == size) {
            //数组扩大2倍
            Object[] arr2 = new Object[size * 2];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            arr = arr2;
        }
    }

    public E get(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new ArrayIndexOutOfBoundsException("Index " + idx + "; size " + size());
        }
        return (E) arr[idx];
    }

    public boolean add(E e) {
        return add(size(), e);
    }

    public boolean add(int index, E e) {
        encureCapacity();
        System.arraycopy(arr, index, arr, index + 1, size() - index);
        arr[index] = e;
        size++;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public E remove(int index) {
        Object o = arr[index];
        System.arraycopy(arr, index+1, arr, index, size() - index - 1);
        arr[size--] = null;
        return (E) o;
    }

    public void clear() {
        arr = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>();
    }

    class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            okToRemove =true;
            return (T) arr[cursor++];
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(cursor);
            cursor--;
            okToRemove =false;
        }
    }

    public static void main(String[] args) {
        MyArrayList<Integer> lst = new MyArrayList<>();

        for (int i = 0; i < 10; i++) {
            lst.add(i);
        }
        for (int i = 20; i < 30; i++) {
            lst.add(0, i);
        }

        lst.remove(0);
        lst.remove(lst.size() - 1);

        for (Integer integer : lst) {
            System.out.println(integer);
        }
    }

}
