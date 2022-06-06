package com.jackmeng.app.tasks;

import com.jackmeng.app.components.bottompane.tabs.FileView;

public final class PingFileView implements Runnable {
  private FileView fv;

  public PingFileView(FileView fv) {
    this.fv = fv;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(ConcurrentTiming.MAX_TLE);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      fv.revalidateFiles();
    }
  }

}
