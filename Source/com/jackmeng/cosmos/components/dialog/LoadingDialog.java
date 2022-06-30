package com.jackmeng.cosmos.components.dialog;

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

public class LoadingDialog extends JFrame {
  private JProgressBar bar;

  public LoadingDialog(String text, boolean isImportant) {
    setPreferredSize(new Dimension(400, 200));
    setResizable(false);
    setAlwaysOnTop(isImportant);
    setLocationRelativeTo(null);
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setPreferredSize(new Dimension(400, 200));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JLabel label = new JLabel(text);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(label);
    bar = new JProgressBar();
    bar.setPreferredSize(new Dimension(400, 20));
    bar.setStringPainted(true);
    bar.setString("Loading...");
    bar.setAlignmentX(Component.CENTER_ALIGNMENT);
    bar.setIndeterminate(true);
    panel.add(bar);
    getContentPane().add(panel);
  }

  public void run(Runnable task) {
    this.pack();
    this.setAlwaysOnTop(true);
    this.setVisible(true);
    new Thread(task::run);
  }

  public void run() {
    run(() -> {
    });
  }

  public void kill() {
    this.dispose();
  }

}
