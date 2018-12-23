package com.zju.week2.homework;
import java.util.Scanner;

/**
 * @author:chenjinfeng
 * @date: 2018/9/16
 * @time: 12:58
 * @desc
 */
/**
 * 02-线性结构2 一元多项式的乘法与加法运算 （20 分）
 * 设计函数分别求两个一元多项式的乘积与和。
 * 输入格式:
 * 输入分2行，每行分别先给出多项式非零项的个数，再以指数递降方式输入一个多项式非零项系数和指数（绝对值均为不超过1000的整数）。数字间以空格分隔。
 * 输出格式:
 * 输出分2行，分别以指数递降方式输出乘积多项式以及和多项式非零项的系数和指数。数字间以空格分隔，但结尾不能有多余空格。零多项式应输出0 0。
 * 输入样例:
 * 4 3 4 -5 2  6 1  -2 0
 * 3 5 20  -7 4  3 1
 * 输出样例:
 * 15 24 -25 22 30 21 -10 20 -21 8 35 6 -33 5 14 4 -15 3 18 2 -6 1
 * 5 20 -4 4 -5 2 9 1 -2 0
 */
public class PolyDataAddAndMulti {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a[] = read(scanner);//读入数组一
        int b[] = read(scanner);//读入数组二
        int d[] = multi(a, b);  // 多项式乘法计算
        print(d);               // 打印数组
        int c[] = add(a, b);    // 多项式加法计算
        print(c);               // 打印数组
    }

    // 将从键盘输入的数字字符串转化为整数型数组
    public static int[] read(Scanner scanner) {
        String s = scanner.nextLine();
        //把多个空格替换为单个空格
        s = s.replaceAll("( )+", " ");
        String[] a = s.split(" ");
        Boolean negative = false;
        int b[] = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            char c = a[i].charAt(0);
            if (c == '-') {
                negative = true;
                a[i] = a[i].substring(1, a[i].length());
                b[i] = Integer.parseInt(a[i]);
                b[i] = negative ? -b[i] : b[i];
            } else {
                b[i] = Integer.parseInt(a[i]);
            }
        }
        // 返回一个整数型数组
        return b;
    }


    // 多项式加法计算
    public static int[] add(int[] a, int[] b) {
        int[] c = new int[a.length + b.length - 1];
        int l = a[0] + b[0];
        int i = 1, j = 1, k = 1;
        int same = 0;
        while (i <= a[0] && j <= b[0]) {
            if (a[2 * i] < b[2 * j]) {
                c[2 * k] = b[2 * j];
                c[2 * k - 1] = b[2 * j - 1];
                j++;
                k++;
            } else if (a[2 * i] > b[2 * j]) {
                c[2 * k] = a[2 * i];
                c[2 * k - 1] = a[2 * i - 1];
                i++;
                k++;
            } else if (a[2 * i] == b[2 * j]) {
                if (a[2 * i - 1] + b[2 * j - 1] == 0) {
                    i++;
                    j++;
                    same = same + 2;
                    continue;
                } else {
                    c[2 * k] = a[2 * i];
                    c[2 * k - 1] = a[2 * i - 1] + b[2 * j - 1];
                    same = same + 1;
                    i++;
                    j++;
                    k++;
                }
            }
        }
        while (i <= a[0]) {
            c[2 * k] = a[2 * i];
            c[2 * k - 1] = a[2 * i - 1];
            i++;
            k++;
        }
        while (j <= b[0]) {
            c[2 * k] = b[2 * j];
            c[2 * k - 1] = b[2 * j - 1];
            j++;
            k++;
        }
        c[0] = l - same;
        return c;
    }

    // 多项式乘法计算
    public static int[] multi(int[] a, int[] b) {
        int[][] c = new int[a[0]][b[0] * 2 + 1];
        int[] d = new int[a[0] * b[0] * 2 + 1];
        int k = 1;
        for (int i = 1; i <= a[0]; i++) {
            c[i - 1][0] = b[0];
            k = 1;
            for (int j = 1; j <= b[0]; j++) {
                c[i - 1][2 * k] = a[2 * i] + b[2 * j];
                c[i - 1][2 * k - 1] = a[2 * i - 1] * b[2 * j - 1];
                k++;
            }
        }
        for (int i = 0; i < c.length; i++) {
            d = add(d, c[i]);
        }
        // 返回两个有序多项式数组的合并数组
        return d;
    }

    // 输出结果
    public static void print(int[] d) {
        if (d[0] == 0) {
            System.out.println("0 0");
        } else {
            for (int i = 1; i < d[0] * 2; i++) {
                System.out.print(d[i] + " ");
            }
            System.out.print(d[d[0] * 2] + "\n");
        }
    }


}
