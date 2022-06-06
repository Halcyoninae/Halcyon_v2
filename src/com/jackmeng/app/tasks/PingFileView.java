package com.jackmeng.app.tasks;

import java.util.Map;

import com.jackmeng.app.components.bottompane.tabs.FileView;
import com.jackmeng.global.Pair;

public final class PingFileView implements Runnable {
  private FileView fv;
  private Map<String, Pair<String, String[]>> lastMap;

  public PingFileView(FileView fv) {
    this.fv = fv;
    lastMap = fv.getFolders();
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(ConcurrentTiming.MAX_TLE);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (FileView.compareTreeFolders(fv.getFolders(), lastMap)) {
        fv.revalidateFiles();
      }

      lastMap = fv.getFolders();
    }
  }

}
