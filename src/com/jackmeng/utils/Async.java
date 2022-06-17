package com.jackmeng.utils;

import java.util.concurrent.CompletableFuture;

public final class Async {
  public static void async(Runnable runnable) {
    CompletableFuture<?> future = CompletableFuture.runAsync(runnable);
  }
}
