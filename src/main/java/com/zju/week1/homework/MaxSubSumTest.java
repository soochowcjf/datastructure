package com.zju.week1.homework;
import org.junit.Test;
/**
 * @author:chenjinfeng
 * @date: 2018/9/8
 * @time: 9:27
 * @desc
 */
/**
 * 给定K个整数组成的序列{ N1,N2, ...,N​k}，“连续子列”被定义为{ Ni, Ni+1, ..., Nj}，
 * 其中 1≤i≤j≤K。“最大子列和”则被定义为所有连续子列元素的和中最大者。例如给定序列{ -2, 11, -4, 13, -5, -2 }，
 * 其连续子列{ 11, -4, 13 }有最大的和20。现要求你编写程序，计算给定整数序列的最大子列和。
 * 本题旨在测试各种不同的算法在各种数据情况下的表现。各组测试数据特点如下：
 * 数据1：与样例等价，测试基本正确性；
 * 数据2：10^2个随机整数；
 * 数据3：10^3个随机整数；
 * 数据4：10^4个随机整数；
 * 数据5：10^5个随机整数；
 * 输入格式:
 * 输入第1行给出正整数K (≤100000)；第2行给出K个整数，其间以空格分隔。
 * 输出格式:
 * 在一行中输出最大子列和。如果序列中所有整数皆为负数，则输出0。
 * 输入样例:
 * -2 11 -4 13 -5 -2
 * 输出样例:
 * 20
 */
public class MaxSubSumTest {

    @Test
    public void fun() {
//        int i = function3(arr, 0, arr.length - 1);
        int i = function4(arr);
        System.out.println(i);
    }

    private int[] arr = {-2, 11, -4, 13, -5, -2};

    /**
     * 1.穷举法
     */
    private int function1(int[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
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
     * 2.简化穷举
     */
    private int function2(int[] arr) {
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
     * 3.递归法 分而治之 时间复杂度为 nlog(n)
     */
    private int function3(int[] arr, int left, int right) {

        if (left == right) {
            if (arr[left] > 0) {
                return arr[left];
            } else {
                return 0;
            }
        }

        //递归
        int center = (left + right) / 2;
        int maxLeftSum = function3(arr, left, center);
        int maxRightSum = function3(arr, center + 1, right);

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
     * 4.在线处理法 时间复杂度n
     */
    private int function4(int[] arr) {
        int max = 0, sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (sum < 0) {
                sum = 0;
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

}
