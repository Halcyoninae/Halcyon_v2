package com.jackmeng.app.tasks;

/**
 * This class aims to optimize tasks throughout the JVM 
 * environment for this application during runtime.
 * 
 * It runs in the background and tries to optimize any
 * tasks that may be running in the background.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class DefunctOptimizer implements Runnable {
  public DefunctOptimizer() {

  }

  @Override
  public void run() {
    new Thread(() -> {
      while(true) {
        Runtime.getRuntime().gc();
        try {
          Thread.sleep(4000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
