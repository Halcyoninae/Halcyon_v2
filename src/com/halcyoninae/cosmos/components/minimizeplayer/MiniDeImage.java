package com.halcyoninae.cosmos.components.minimizeplayer;

import java.awt.image.*;

import com.halcyoninae.cloudspin.lib.blurhash.BlurHash;

public final class MiniDeImage {
  private MiniDeImage() {}

  public static BufferedImage blurHash(BufferedImage r, int x, int y) {
    return new BlurHash().blur(r, x, y, (Object[]) null); // cast a null???? cmon bruh
  }
}
