package com.jackmeng.app.tasks;

import java.util.ArrayList;
import java.util.Map;

import com.jackmeng.app.components.bottompane.BottomPane;
import com.jackmeng.app.components.bottompane.FileList;
import com.jackmeng.app.components.bottompane.tabs.FileView;
import com.jackmeng.global.Pair;

/**
 * A class designed to constantly ping
 * the file view system in order to alert
 * it of any change.
 * 
 * In order to automatically update the file view
 * system without the user having to update it manually.
 */
public final class PingFileView implements Runnable {
  private Thread worker;
  private BottomPane bp;

  /**
   * Calls the default BottomPane Object 
   * @param bp the bottompane instance
   * @see com.jackmeng.app.components.bottompane.BottomPane
   */
  public PingFileView(BottomPane bp) {
    this.bp = bp;
  }

  @Override
  public void run() {
    if (worker == null) {
      worker = new Thread(() -> {
        while (true) {
          bp.mastRevalidate();
          try {
            Thread.sleep(ConcurrentTiming.MAX_TLE);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
      worker.start();
    }
  }

}
