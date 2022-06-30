package com.jackmeng.tailwind;

import java.util.ArrayList;
import java.util.List;

public class TailwindPlaylistEventManager {
  private List<TailwindPlaylistListener.TailwindPlaylistStatusListener> statusListeners;
  private List<TailwindPlaylistListener.TailwindPlayListUpdateListener> updateListeners;

  public TailwindPlaylistEventManager() {
    statusListeners = new ArrayList<>();
    this.updateListeners = new ArrayList<>();
  }

  public boolean addStatusListener(TailwindPlaylistListener.TailwindPlaylistStatusListener listener) {
    return statusListeners.add(listener);
  }
}
