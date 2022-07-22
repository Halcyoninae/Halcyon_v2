package com.halcyoninae.test.cloudspin;

import javax.imageio.ImageIO;

import com.halcyoninae.cloudspin.CloudSpin;
import com.halcyoninae.halcyon.debug.Debugger;

import java.awt.*;
import java.awt.image.*;
import java.io.File;

public class CloudSpinTester {
  public static void main(String ... args) throws Exception {
    BufferedImage bf = ImageIO.read(new File("/home/jackm/Code/mp4j-test/src/com/halcyoninae/test/cloudspin/test.png"));
    ImageIO.write(CloudSpin.grabCrop(bf, new Rectangle(bf.getWidth() / 2, bf.getHeight() /2, 50, 50)), "png", new File("/home/jackm/Code/mp4j-test/src/com/halcyoninae/test/cloudspin/test2.png"));
  }
}
