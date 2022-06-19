package com.jackmeng.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jackmeng.connections.properties.ResourceFolder;
import com.jackmeng.constant.ProgramResourceManager;

public final class Async {
  public static void async(Runnable runnable) {
    if(ResourceFolder.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("true")) {
      ExecutorService threadpool = Executors.newFixedThreadPool(1);
      threadpool.submit(runnable);
    } else {
      CompletableFuture.runAsync(runnable);
    }
  }
}
