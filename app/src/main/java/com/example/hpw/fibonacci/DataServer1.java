package com.example.hpw.fibonacci;

import android.content.Context;
import android.util.SparseArray;

import com.hpw.mvpframe.utils.ACache;

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
    private static SparseArray sparseArray = new SparseArray();
    private static ACache aCache;

    public static void createFibonacciData(Context context) {
        aCache = ACache.get(context);
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= total; i++) {
                    addData(i);
                }
            }
        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                for (int i = total; i >= 0; i--) {
//                    addData(i);
//                }
//            }
//        }.start();
    }

    public static List<String> getFibonacciData(int num, int page, boolean sort) {
        List<String> returnlist = new ArrayList<>();
        int index = num * page, max;
        max = num * (page + 1) < total ? num * (page + 1) : total;
        for (int i = index; i < max; i++) {
            addReturnData(returnlist, i, sort);
        }
        return returnlist;
    }

    private static void addReturnData(List<String> s, int i, boolean sort) {
        String string = null;
        int position = i;
        if (!sort) {
            position = total - 1 - i;
        }
        if (sparseArray.get(position) != null) {
            string = sparseArray.get(position).toString();
        } else if (aCache.getAsString(String.valueOf(position)) != null) {
            string = aCache.getAsString(String.valueOf(position));
            sparseArray.put(position, string);
        } else {
            string = format(position);
            sparseArray.put(position, string);
            aCache.put(String.valueOf(position), string);
        }
        s.add(string);
    }

    private static void addData(int i) {
        if (aCache.getAsString(String.valueOf(i)) == null) {
            String s = format(i);
            sparseArray.put(i, s);
            aCache.put(String.valueOf(i), s);
        } else {
            sparseArray.put(i, aCache.getAsString(String.valueOf(i)));
        }
    }

    //格式化值(大于10^10)
    private static String format(int i) {
        BigInteger bigInteger = fb(BigInteger.valueOf(i).pow(2).intValue())[0][1];
        if (bigInteger.compareTo(temp) > 0) {
            return "F(" + i + "^2) -> " + decimalFormat.format(bigInteger);
        } else {
            return "F(" + i + "^2) -> " + bigInteger.toString();
        }
    }

    //计算斐波那契
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
