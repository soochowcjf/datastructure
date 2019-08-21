package com.javalanguage.chapter3;

import java.util.Iterator;

/**
 * @author cjf on 2019/8/20 21:41
 */
public class MyLinkedList<E> implements Iterable<E> {

    private Node<E> first;

    private Node<E> last;

    private int size;

    public MyLinkedList() {
        size = 0;
        first = new Node<>(null, null, null);
        last = new Node<>(null, first, null);
        first.next = last;
    }

    public void clear() {
        size = 0;
        first.next = last;
        last.pre = first;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(E e) {
        Node<E> newNode = new Node<>(e, last.pre, last);
        Node<E> pre = last.pre;
        pre.next = newNode;
        last.pre = newNode;
        size++;
        return true;
    }

    private void addBefore(Node<E> p, E x) {
        Node<E> newNode = new Node<>(x, p.pre, p);
        newNode.pre.next = newNode;
        p.pre = newNode;
        size++;
    }

    private Node<E> getNode(int idx, int lower, int upper) {
        Node<E> p;

        if (idx < lower || idx > upper) {
            throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());
        }

        if (idx < size() / 2) {
            p = first.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = last;
            for (int i = size(); i > idx; i--) {
                p = p.pre;
            }
        }
        return p;
    }

    public void add(int idx, E e) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException(idx + "");
        }
        int i = 0;
        Node<E> node = first;
        while (i < idx) {
            node = first.next;
            i++;
        }
        Node<E> newNode = new Node<>(e, node, node.next);
        Node<E> next = node.next;
        node.next = newNode;
        next.pre = newNode;
        size++;
    }

    public E get(int idx) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException(idx + "");
        }
        int i = 0;
        Node<E> node = null;
        while (i < idx) {
            node = first.next;
            i++;
        }
        return node.getValue();
    }

    public E set(int idx, E e) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException(idx + "");
        }
        int i = 0;
        Node<E> node = null;
        while (i < idx) {
            node = first.next;
            i++;
        }
        node.value = e;
        return e;
    }

    public E remove(int idx) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException(idx + "");
        }
        int i = 0;
        Node<E> node = first.next;
        while (i < idx) {
            node = node.next;
            i++;
        }
        Node<E> pre = node.pre;
        Node<E> next = node.next;
        pre.next = next;
        next.pre = pre;
        size--;
        return node.value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");

        for (E x : this) {
            sb.append(x + " ");
        }
        sb.append("]");

        return new String(sb);
    }


    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {

        private Node<E> cursor = first;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return cursor.next != last;
        }

        @Override
        public E next() {
            Node<E> next = cursor.next;
            cursor = next;
            okToRemove = true;
            return next.value;
        }

        @Override
        public void remove() {
            Node pre = cursor.pre;
            Node next = cursor.next;
            pre.next = next;
            next.pre = pre;
            okToRemove = false;
            size--;
        }
    }

    private class Node<V> {
        V value;
        Node<V> pre;
        Node<V> next;

        public Node() {
        }

        public Node(V value, Node<V> pre, Node<V> next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<V> getPre() {
            return pre;
        }

        public void setPre(Node<V> pre) {
            this.pre = pre;
        }

        public Node<V> getNext() {
            return next;
        }

        public void setNext(Node<V> next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> lst = new MyLinkedList<>();

        for (int i = 0; i < 10; i++) {
            lst.add(i);
        }
        for (int i = 20; i < 30; i++) {
            lst.add(0, i);
        }

        lst.remove(0);
        lst.remove(lst.size() - 1);

        System.out.println(lst);

        java.util.Iterator<Integer> itr = lst.iterator();
        while (itr.hasNext()) {
            itr.next();
            itr.remove();
            System.out.println(lst);
        }
    }

}
