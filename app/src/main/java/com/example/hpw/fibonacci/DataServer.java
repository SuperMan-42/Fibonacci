package com.example.hpw.fibonacci;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpw on 16/11/1.
 */
public class DataServer {
    private static final BigInteger bigInteger0 = BigInteger.ZERO;
    private static final BigInteger bigInteger1 = BigInteger.ONE;
    private static final BigInteger temp = BigInteger.TEN.pow(10);
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0000000000E00");
    private static final int total = 451;
    // 关联矩阵
    private static final BigInteger[][] UNIT = {{bigInteger1, bigInteger1}, {bigInteger1, bigInteger0}};
    // 全0矩阵
    private static final BigInteger[][] ZERO = {{bigInteger0, bigInteger0}, {bigInteger0, bigInteger0}};

    public static List<String> getFibonacciData(int num, int page, boolean sort) {
        List<String> list = new ArrayList<>();
        if (sort) {
            if (num * (page + 1) < total) {
                for (int i = num * page; i < num * (page + 1); i++) {
                    addData(list, i);
                }
            } else {
                addData(list, total);
            }
        } else {
            if (num * (page + 1) < total) {
                for (int i = total - num * page; i > total - num * (page + 1); i--) {
                    addData(list, i);
                }
            } else {
                addData(list, 0);
            }
        }
        return list;
    }

    private static void addData(List<String> list, int i) {
        //算法一
        BigInteger bigInteger = fb(BigInteger.valueOf(i).pow(2).intValue())[0][1];
//        算法二
//        BigInteger bigInteger = fibonacciNormal(BigInteger.valueOf(i).pow(2).intValue());
        if (bigInteger.compareTo(temp) > 0) {
            list.add("F(" + i + "^2) -> \n" + decimalFormat.format(bigInteger));
        } else {
            list.add("F(" + i + "^2) -> \n" + bigInteger.toString());
        }
    }

    /**
     * 求斐波那契数列
     *
     * @param n
     * @return
     */
    private static BigInteger[][] fb(int n) {
        if (n == 0) {
            return ZERO;
        }
        if (n == 1) {
            return UNIT;
        }
        // n是偶数
        if ((n & 1) == 0) {
            BigInteger[][] matrix = fb(n >> 1);
            return matrixMultiply(matrix, matrix);
        }
        // n是奇数
        BigInteger[][] matrix = fb(((n - 1) >> 1));
        return matrixMultiply(matrixMultiply(matrix, matrix), UNIT);
    }

    /**
     * 矩阵相乘
     *
     * @param m r1*c1
     * @param n c1*c2
     * @return 新矩阵, r1*c2
     */
    private static BigInteger[][] matrixMultiply(BigInteger[][] m, BigInteger[][] n) {
        int rows = m.length;
        int cols = n[0].length;
        BigInteger[][] r = new BigInteger[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                r[i][j] = BigInteger.valueOf(0);
                for (int k = 0; k < m[i].length; k++) {
                    r[i][j] = r[i][j].add(m[i][k].multiply(n[k][j]));
                }
            }
        }
        return r;
    }

    // 递推实现方式
    private static BigInteger fibonacciNormal(int n) {
        if (n <= 2) {
            return BigInteger.ONE;
        }
        BigInteger n1 = BigInteger.ONE, n2 = BigInteger.ONE, sn = BigInteger.ZERO;
        for (int i = 0; i < n - 2; i++) {
            sn = n1.add(n2);
            n1 = n2;
            n2 = sn;
        }
        return sn;
    }

    // 递归实现方式
    public static int fibonacci(int n) {
        if (n <= 2) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}