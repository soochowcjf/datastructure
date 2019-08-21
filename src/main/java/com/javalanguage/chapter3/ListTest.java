package com.javalanguage.chapter3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cjf on 2019/8/18 21:47
 */
public class ListTest {

    private static ArrayList<Integer> list = new ArrayList<Integer>() {{
        add(6);
        add(5);
        add(1);
        add(4);
        add(2);
    }};

    public static void main(String[] args) {
//        removeEventVer1(list);
//        removeEventVer2(list);
        removeEventVer3(list);
        System.out.println(list);

    }

    private static void removeEventVer1(List<Integer> list) {
        int i = 0;
        while (i < list.size()) {
            if (list.get(i) % 2 == 0) {
                list.remove(i);
            } else {
                i++;
            }
        }
    }

    /**
     * Exception in thread "main" java.util.ConcurrentModificationException
     *
     * @param list
     */
    private static void removeEventVer2(List<Integer> list) {
        for (Integer x : list) {
            if (x % 2 == 0) {
                //高级for底层也是用的迭代器
                list.remove(x);
            }
        }
    }

    private static void removeEventVer3(List<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next % 2 == 0) {
                iterator.remove();
            }
        }
    }

}
