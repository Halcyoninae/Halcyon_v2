package com.jackmeng.app;

import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.jackmeng.app.tasks.ConcurrentTiming;
import com.jackmeng.app.tasks.PingFileView;

public final class ThreadedScheduler {
  public ThreadedScheduler() {
  }

  /**
   * Anything that needs initialization should be done here.
   * <br>
   * This init process is done before anything is displayed or run in the program
   * itself. This is done mostly to configure the current host's system to be
   * fitting the program.
   * <br>
   * Along with this, this process also sets up any Threads that must be running
   * throughout the program either for: user comfort or program functionality.
   * <br>
   * On demand tasks such as those that need to be run on a separate thread must
   * be init by themselves not here.
   */
  static {
    System.setProperty("file.encoding", "UTF-8");
    System.setProperty("sun.jnu.encoding", "UTF-8");
    System.setProperty("flatlaf.useJetBrainsCustomDecorations", "true");
    System.setOut(null);
    UIManager.put("FileChooser.readOnly", true);
    try {
      UIManager.setLookAndFeel(FlatArcDarkIJTheme.class.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // PROGRAMMABLE THREADS
    Runnable[] tasks = new Runnable[] {
        new PingFileView(Global.f),
    };

    for (Runnable t : tasks) {
      new Thread(t).start();
    }

  }
}
