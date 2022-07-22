package com.halcyoninae.tailwind;

import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.tailwind.TailwindPlaylistListener.TailwindPlaylistStatusListener;

public class TailwindPlaylistCLI implements TailwindPlaylistStatusListener {

  /**
   * @param status
   */
  @Override
  public void playListStatusUpdate(TailwindPlaylistStatusEvent status) {
    Debugger.warn("TAILWIND_PLAYLIST> " + status);
  }
}
