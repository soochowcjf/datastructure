package com.zju.week1;

import org.junit.Test;

/**
 * @author:chenjinfeng
 * @date: 2018/9/3
 * @time: 21:54
 * @desc 求最大子序列和的算法
 */
public class MaxSubSum {

    @Test
    public void test() {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
//        System.out.println(maxSubSum1(arr));
//        System.out.println(maxSubSum2(arr));
//        System.out.println(maxSubSum3(arr, 0, arr.length - 1));
        System.out.println(maxSubSum4(arr));
    }

    /**
     * 1.穷举法，时间复杂度 O(n^3)
     */
    public int maxSubSum1(int[] arr) {
        int max = 0;
        //左边界
        for (int i = 0; i < arr.length; i++) {
            //右边界
            for (int j = i; j < arr.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                }
                if (sum > max) {
                    max = sum;
                }
            }
        }
        return max;
    }

    /**
     * 2.简化穷举法，时间复杂度 O(N^2)
     */
    public int maxSubSum2(int[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (sum > max) {
                    max = sum;
                }
            }
        }
        return max;
    }

    /**
     * 3.递归调用,分而治之 时间复杂度 nlog(n)
     */
    public int maxSubSum3(int[] arr, int left, int right) {

        if (left == right) {
            if (arr[left] > 0) {
                return arr[left];
            } else {
                return 0;
            }
        }

        //递归
        int center = (left + right) / 2;
        int maxLeftSum = maxSubSum3(arr, left, center);
        int maxRightSum = maxSubSum3(arr, center + 1, right);

        //求左边子序列的和
        int leftBorderSum = 0, maxLeftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += arr[i];
            if (leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
            }
        }

        int rigthBorderSum = 0, maxRightBorderSum = 0;
        for (int j = center + 1; j <= right; j++) {
            rigthBorderSum += arr[j];
            if (rigthBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rigthBorderSum;
            }
        }

        //左、右、跨边界
        return Math.max(Math.max(maxLeftSum, maxRightSum), maxLeftBorderSum + maxRightBorderSum);
    }

    /**
     * 4.在线处理，时间复杂度 O(n)
     */
    public int maxSubSum4(int[] arr) {
        int max = 0, sum = 0;
        for (int i = 0; i < arr.length; i++) {
            //向右累加
            sum += arr[i];
            if (sum > max) {
                //发现更大的和则更新当前的结果
                max = sum;
            } else if (sum < 0) {
                //如果当前子列的和为负，则不可能使后面的部分和增大，抛弃之
                sum = 0;
            }
        }
        return max;
    }
}
