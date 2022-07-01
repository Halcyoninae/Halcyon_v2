package com.jackmeng.tailwind;

import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.tailwind.TailwindPlaylistListener.TailwindPlaylistStatusListener;

public class TailwindPlaylistCLI implements TailwindPlaylistStatusListener {
  @Override
  public void playListStatusUpdate(TailwindPlaylistStatusEvent status) {
    Debugger.warn("TAILWIND_PLAYLIST> " + status);
  }
}
