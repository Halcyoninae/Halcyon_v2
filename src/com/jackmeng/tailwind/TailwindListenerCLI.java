package com.jackmeng.tailwind;

import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.tailwind.TailwindEvent.TailwindStatus;

public class TailwindListenerCLI
    implements TailwindListener.StatusUpdateListener, TailwindListener.GenericUpdateListener {

  /**
   * @param status
   */
  @Override
  public void statusUpdate(TailwindStatus status) {
    Debugger.warn("TailwindPLAYER> " + status);
  }


  /**
   * @param event
   */
  @Override
  public void genericUpdate(TailwindEvent event) {
    Debugger.warn("TailwindPLAYER> " + event.getCurrentAudioInfo().getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
  }

}
