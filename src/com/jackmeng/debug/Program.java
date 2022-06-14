package com.jackmeng.debug;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Provides a concurrent logging system for the program
 * instead of using Debugger, which is meant for debugging during
 * initial development.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class Program {
  private static ExecutorService executorService;

  private static void println(String e) {
    if (executorService.isShutdown() || executorService.isTerminated()) {
      executorService =
        Executors.newCachedThreadPool(
          new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
              Thread t = new Thread(r);
              t.setDaemon(true);
              return t;
            }
          }
        );
      executorService.submit(
        new Runnable() {

          @Override
          public synchronized void run() {
            while (true) {
              try {
                wait();
                System.err.println(e);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          }
        }
      );
    }
  }

  /**
   * This main function runs the daemon for system logging
   *
   * This is provided under an executor service that runs
   * async to the main thread in order to log everything made by the
   * the program.
   *
   * This async mechanism is called natively.
   * @param args Initial items to log
   */
  public static void main(String... args) {
    for (String arg : args) {
        println(arg);
    }
  }
}
