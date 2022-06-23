package com.jackmeng.halcyon.connections.ploogin;

public interface Ploogin extends Runnable {
  default String getPluginName() {
    return this.getClass().getSimpleName();
  }

  default String getPluginDescription() {
    return "Something";
  }

  default String getPluginAuthor() {
    return "Someone";
  }
}
