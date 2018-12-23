package com.algorithm.weichat;

/**
 * @author:chenjinfeng
 * @date: 2018/9/5
 * @time: 23:17
 * @desc 八皇后算法
 */
public class Queen8 {

    private static final int MAX_SUM = 8;

    /**
     * 定义一个二维数组
     */
    private int[][] chessBoard = new int[MAX_SUM][MAX_SUM];

    /**
     * 检查落点是否符合规则
     */
    private boolean check(int x, int y) {
        for (int i = 0; i < y; i++) {
            //检查纵向是否符合
            if (chessBoard[x][i] == 1) {
                return false;
            }
            //检查左侧上方是否符合
            if (x - i - 1 >= 0 && chessBoard[x - i - 1][y - i - 1] == 1) {
                return false;
            }
            //检查右侧上方是否符合
            if (x + i + 1 < MAX_SUM && chessBoard[x + i + 1][y - 1 -i] == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 递归
     */
    private boolean settleQueen(int y) {
        //行数超过8，说明已经找出答案
        if (y == MAX_SUM) {
            return true;
        }
        //遍历当前行，足一格子验证
        for (int i = 0; i < MAX_SUM; i++) {
            //为当前行清零，以免在递归的时候出现脏数据
            for (int x = 0; x < MAX_SUM; x++) {
                chessBoard[x][y] = 0;
            }
            //检查是否符合规则，如果符合，更改元素值进一步递归
            if (check(i, y)) {
                chessBoard[i][y] = 1;
                //递归如果返回true，说明下层已经找到解法，无需继续循环
                if (settleQueen(y + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void printChessBoard() {
        for (int i = 0; i < MAX_SUM; i++) {
            for (int j = 0; j < MAX_SUM; j++) {
                System.out.print(chessBoard[j][i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.settleQueen(0);
        queen8.printChessBoard();
        /**
         * 10000000
         * 00001000
         * 00000001
         * 00000100
         * 00100000
         * 00000010
         * 01000000
         * 00010000
         */
    }
}
