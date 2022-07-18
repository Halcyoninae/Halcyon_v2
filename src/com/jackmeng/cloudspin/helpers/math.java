package com.jackmeng.cloudspin.helpers;

public final class math {
  private math() {}

  public static double _max(double [][] arr) {
    double max = arr[0][0];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        if (arr[i][j] > max) {
          max = arr[i][j];
        }
      }
    }
    return max;
  }

  public static double signpow(double val, double exp) {
    return Math.copySign(Math.pow(Math.abs(val), exp), val);
  }
}
