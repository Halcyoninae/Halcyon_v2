package com.jackmeng.test.cloudspin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;

import com.jackmeng.cloudspin.*;
import com.jackmeng.test.TesterFrame;

public class CloudSpinTester {

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    BufferedImage img = ImageIO.read(new File("Source/com/jackmeng/test/cloudspin/colorwheel.png"));
    JPanel jp = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        CloudLiteSpin.colorTone(img, Color.BLUE);
        g.drawImage(img, 0, 0, null);
      }
    };
    jp.setPreferredSize(new Dimension(300, 300));

    TesterFrame tf = new TesterFrame(jp);
    tf.run();
  }
}