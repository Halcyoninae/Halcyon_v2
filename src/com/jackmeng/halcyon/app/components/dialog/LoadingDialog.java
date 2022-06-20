package com.jackmeng.halcyon.app.components.dialog;

import javax.swing.*;

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.utils.Wrapper;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

  public void run(Callable<Boolean> task) {
    this.pack();
    this.setAlwaysOnTop(true);
    this.setVisible(true);

    ExecutorService executor = Executors.newCachedThreadPool();
    Future<Boolean> future = executor.submit(task);
    while (!future.isDone()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    try {
      boolean result = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    executor.shutdown();
    kill();
  }

  public void kill() {
    this.dispose();
  }

}
