package com.jackmeng.tailwind;

import java.io.File;

public final class TailwindTranscoder implements Transcoder {
  @Override
  public void transcode(int inFormat, int outFormat, File inLocale, File outLocale) {

  }

  public static int[] byteify(byte[] arr, int shift_n) {
    int[] temp = new int[arr.length / 2];
    for (int i = 0; i < arr.length / 2; i++) {
      temp[i] = (arr[i * 2] & 0xFF) | (arr[i * 2 + 1] << (shift_n == 0 ? 8 : shift_n));
    }
    return temp;
  }
}
