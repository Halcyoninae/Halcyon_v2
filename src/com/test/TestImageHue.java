package com.test;

import java.awt.Color;
import java.io.File;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.halcyoninae.cloudspin.CloudSpin;
import com.halcyoninae.cosmos.theme.bundles.DarkOrange;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.ColorTool;

public class TestImageHue {
    public static void main(String... args) throws Exception {
        BufferedImage bi = ImageIO.read(new File("./src/com/test/play_button.png"));
        /*
         * int i = 12;
         * while (i-- > 0) {
         * Color t = ColorTool.rndColor();
         * long usedMem = Runtime.getRuntime().totalMemory() -
         * Runtime.getRuntime().freeMemory();
         * long l = System.currentTimeMillis();
         * Debugger.info(
         * "Lasts: " + (System.currentTimeMillis() - l) + " | Memory Used: " + usedMem +
         * " | Color Used: " + t.getRGB());
         * Thread.sleep(100);
         * }
         */
        CloudSpin.hueImage(bi, ColorTool.colorBreakDown(new DarkOrange().getForegroundColor()));
        ImageIO.write(bi, "png", new File("./src/com/test/out-play_button.png"));
        ImageIO.write(CloudSpin.createUnknownIMG(), "png", new File("./src/com/test/out-unknown.png"));
    }
}
