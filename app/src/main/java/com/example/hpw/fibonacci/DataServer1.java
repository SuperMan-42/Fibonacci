package com.example.hpw.fibonacci;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpw on 17-1-7.
 */

public class DataServer1 {
    private static final BigInteger bigInteger0 = BigInteger.ZERO;
    private static final BigInteger bigInteger1 = BigInteger.ONE;
    private static final BigInteger temp = BigInteger.TEN.pow(10);
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0000000000E00");
    private static final int total = 451;
    private static final BigInteger[][] UNIT = {{bigInteger1, bigInteger1}, {bigInteger1, bigInteger0}};
    private static final BigInteger[][] ZERO = {{bigInteger0, bigInteger0}, {bigInteger0, bigInteger0}};
    private static List<String> list = new ArrayList<>();
    private static List<String> list1 = new ArrayList<>();

    public static void createFibonacciData() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= total; i++) {
                    addData(list, i);
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = total; i >= 0; i--) {
                    addData(list1, i);
                }
            }
        }.start();
    }

    public static List<String> getFibonacciData(int num, int page, boolean sort) {
        List<String> returnlist = new ArrayList<>();
//        if (sort) {
//            if (num * (page + 1) < total) {
//                for (int i = num * page; i < num * (page + 1); i++) {
//                    addReturnData(returnlist, i);
//                }
//            } else {
//                addReturnData(returnlist, total);
//            }
//        } else {
//            if (num * (page + 1) < total) {
//                for (int i = total - num * page; i > total - num * (page + 1); i--) {
//                    addReturnData(returnlist, i);
//                }
//            } else {
//                addReturnData(returnlist, 0);
//            }
//        }

        if (num * (page + 1) < total) {
            for (int i = num * page; i < num * (page + 1); i++) {
                addReturnData(returnlist, i, sort);
            }
        } else {
            addReturnData(returnlist, total, sort);
        }
        return returnlist;
    }

    private static void addReturnData(List<String> s, int i, boolean sort) {
        try {
            if (sort) {
                s.add(list.get(i));
            } else {
                s.add(list1.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            if (sort) {
                s.add("F(" + i + "^2) -> \n" + decimalFormat.format(fb(BigInteger.valueOf(i).pow(2).intValue())[0][1]));
            } else {
                s.add("F(" + (total - i) + "^2) -> \n" + decimalFormat.format(fb(BigInteger.valueOf(total - i).pow(2).intValue())[0][1]));
            }
        }
    }

    private static void addData(List<String> stringList, int i) {
        BigInteger bigInteger = fb(BigInteger.valueOf(i).pow(2).intValue())[0][1];
        if (bigInteger.compareTo(temp) > 0) {
            stringList.add("F(" + i + "^2) -> \n" + decimalFormat.format(bigInteger));
        } else {
            stringList.add("F(" + i + "^2) -> \n" + bigInteger.toString());
        }
    }

    private static BigInteger[][] fb(int n) {
        if (n == 0) {
            return ZERO;
        }
        if (n == 1) {
            return UNIT;
        }
        if ((n & 1) == 0) {
            BigInteger[][] matrix = fb(n >> 1);
            return matrixMultiply(matrix, matrix);
        }
        BigInteger[][] matrix = fb(((n - 1) >> 1));
        return matrixMultiply(matrixMultiply(matrix, matrix), UNIT);
    }

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
}
