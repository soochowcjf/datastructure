package com.zju.week1.homework;
import org.junit.Test;

/**
 * @author:chenjinfeng
 * @date: 2018/9/8
 * @time: 13:50
 * @desc 二分查找
 */
public class BinarySearch {

    private int[] arr = {12, 31, 55, 89, 101};

    /**
     * 二分查找递归实现
     */
    private int binarySearch(int[] srcArray, int start, int end, int key) {
        int mid = (end - start) / 2 + start;
        if (srcArray[mid] == key) {
            return mid;
        }
        if (start >= end) {
            return -1;
        } else if (key > srcArray[mid]) {
            return binarySearch(srcArray, mid + 1, end, key);
        } else if (key < srcArray[mid]) {
            return binarySearch(srcArray, start, mid - 1, key);
        }
        return -1;
    }

    /**
     * 二分查找普通循环实现
     */
    public int binarySearch(int[] srcArray, int key) {
        int mid = srcArray.length / 2;
        if (key == srcArray[mid]) {
            return mid;
        }

        int start = 0;
        int end = srcArray.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (key < srcArray[mid]) {
                end = mid - 1;
            } else if (key > srcArray[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    @Test
    public void test() {
        int i = binarySearch(arr, 0);
        System.out.println(i);
    }
}
