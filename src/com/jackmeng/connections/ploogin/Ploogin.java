package com.jackmeng.connections.ploogin;

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
