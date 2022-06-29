/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.tailwind;

import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.tailwind.TailwindEvent.TailwindStatus;
import com.jackmeng.tailwind.simple.FileFormat;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The official adapted audio framework for the Halcyon Program.
 *
 * This framework is licensed under the GPL-2.0 license. If you are to
 * incorporate this in your own program, you must follow the addendums
 * of the GPL-2.0 license.
 *
 * This engine is multi-use meaning it is much more efficient for you
 * to have one instance of this class and use it throughout your program.
 * Reuse of this player is done by closing and opening again.
 *
 * An opening call while it is already opened will cause the stream to close
 * forcefully. It is suggested for the user to strongly check if the stream
 * is open beforehand and handle it themselves accordingly, instead of
 * letting {@link #open(File)} handle it itself.
 *
 * A good system to implement is a playlist feature where data is constantly
 * fed into the player once the old stream ends. This can be done via
 * listener and by handling the open and play functions accordingly.
 *
 * For different circumstances it should be noted that the usage
 * of an opening buffer is not used and every thing is gathered from
 * AudioFormat. This results in less overhead and less cpu usage.
 *
 * Implementation for different audio formats is provided by the
 * AudioUtil class.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class TailwindPlayer implements Audio, Runnable {
  private File resource;
  private SourceDataLine line;
  private FileFormat format;
  private Map<String, Control> controlTable;
  private boolean open, paused, playing;
  private AudioInputStream ais;
  private long microsecondLength, frameLength;
  private ExecutorService worker;
  private final Object referencable = new Object();
  private final TailwindEventManager events;

  // PUBLIC STATIC UTIL
  public static String MASTER_GAIN_STR = "Master Gain", BALANCE_STR = "Balance";

  public TailwindPlayer() {
    events = new TailwindEventManager();
    TailwindDefaultListener tdfl = new TailwindDefaultListener(this);
    events.addStatusUpdateListener(tdfl);
    events.addGenericUpdateListener(tdfl);
  }

  @Override
  public void open(File url) {
    if (isOpen() || isPlaying()) {
      close();
    }
    try {
      this.resource = url;
      this.format = FileFormat.getFormatByName(this.resource.getName());
      ais = AudioUtil.getAudioIS(resource.toURI().toURL());
      assert ais != null;
      microsecondLength = (long) (1000000 *
          (ais.getFrameLength() /
              ais.getFormat().getFrameRate()));
      frameLength = ais.getFrameLength();

      if (microsecondLength < 0) {
        frameLength = ais.getFrameLength();
        microsecondLength = 1000000L * Integer.parseInt(new AudioInfo(url).getTag(AudioInfo.KEY_MEDIA_DURATION));
      }

      DataLine.Info info = new DataLine.Info(
          SourceDataLine.class,
          ais.getFormat());
      this.line = (SourceDataLine) AudioSystem.getLine(info);
      this.line.open(ais.getFormat());
      controlTable = TailwindPlayer.setControls(this.line, controlTable);
      this.open = true;
      events.dispatchStatusEvent(TailwindStatus.OPEN);
      events.dispatchGenericEvent(new TailwindEvent(new AudioInfo(resource)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public synchronized long getMicrosecondLength() {
    return microsecondLength;
  }

  public synchronized long getLength() {
    return microsecondLength / 1000;
  }

  public synchronized boolean isPlaying() {
    return playing;
  }

  public synchronized boolean isPaused() {
    return paused;
  }

  public synchronized boolean isOpen() {
    return open;
  }

  public synchronized long getFrameLength() {
    return frameLength;
  }

  public synchronized FileFormat getFileFormat() {
    return format;
  }

  /**
   * @return The current source data line's position in microsecond
   */
  public synchronized long getPositionMicro() {
    return line.getMicrosecondPosition();
  }

  public synchronized long getPosition() {
    return getPositionMicro() / 1000L;
  }

  public synchronized long getLongFramePosition() {
    return line.getLongFramePosition();
  }

  public synchronized boolean addGenericUpdateListener(TailwindListener.GenericUpdateListener e) {
    return events.addGenericUpdateListener(e);
  }

  public synchronized boolean addStatusUpdateListener(TailwindListener.StatusUpdateListener e) {
    return events.addStatusUpdateListener(e);
  }

  public synchronized boolean addTimeListener(TailwindListener.TimeUpdateListener e) {
    return events.addTimeListener(e);
  }

  @Override
  public void open(URL url) {
    this.open(new File(url.getFile()));
  }

  @Override
  public void close() {
    if (open) {
      try {
        resetProperties();
        if (line != null) {
          line.flush();
          line.drain();
          line.close();
        }

        if (ais != null) {
          ais.close();
        }
        events.dispatchStatusEvent(TailwindStatus.CLOSED);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void play() {
    if (playing || paused) {
      stop();
    }
    worker = Executors.newSingleThreadExecutor();
    worker.execute(this);
    playing = true;

    events.dispatchStatusEvent(TailwindStatus.PLAYING);
  }

  private void resetProperties() {
    playing = false;
    paused = false;
    open = false;
  }

  @Override
  public void setGain(float percent) {
    FloatControl control = (FloatControl) this.controlTable.get(MASTER_GAIN_STR);
    control.setValue(percent < control.getMinimum() ? control.getMinimum()
        : (Math.min(percent, control.getMaximum())));
  }

  @Override
  public void setBalance(float balance) {
    FloatControl bal = (FloatControl) this.controlTable.get(BALANCE_STR);
    bal.setValue(
        balance < bal.getMinimum() ? bal.getMinimum() : (Math.min(balance, bal.getMaximum())));
  }

  @Override
  public void setMute(boolean mute) {
  }

  @Override
  public void resume() {
    if (paused) {
      playing = true;
      paused = false;
      synchronized (referencable) {
        referencable.notifyAll();
      }
    } else {
      play();
    }
  }

  @Override
  public void seek(long millis) {
    setPosition(millis);
  }

  @Override
  public synchronized void setPosition(long millis) {
    if (open) {
      setFramePosition((long) (ais.getFormat().getFrameSize() / 1000F) * millis);
    }
  }

  @Override
  public void pause() {
    if (playing && !paused) {
      paused = true;
      playing = false;
    }
    events.dispatchStatusEvent(TailwindStatus.PAUSED);
  }

  @Override
  public void stop() {
    playing = false;
    paused = false;
    setPosition(0);
  }

  public void setFramePosition(long frame) {
    try {
      if (playing)
        pause();

      if (frame < frameLength)
        reset();

      byte[] buffer = new byte[ais.getFormat().getFrameSize()];
      long curr = 0L;

      while (curr < frame) {
        ais.read(buffer);
        curr++;
      }

      if (paused) {
        line.start();
        resume();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void reset() {
    close();
    open(resource);
  }

  private static Map<String, Control> setControls(Line line, Map<String, Control> table) {
    Map<String, Control> temp = new HashMap<>();
    for (Control ctrl : line.getControls()) {
      String t = ctrl.getType().toString();
      if (table != null && table.containsKey(t)) {
        Control old = table.get(t);
        if (ctrl instanceof FloatControl && old instanceof FloatControl) {
          ((FloatControl) ctrl).setValue(
              ((FloatControl) ctrl).getValue());
        } else if (ctrl instanceof BooleanControl) {
          ((BooleanControl) ctrl).setValue(
              ((BooleanControl) ctrl).getValue());
        } else if (ctrl instanceof EnumControl) {
          ((EnumControl) ctrl).setValue(
              ((EnumControl) ctrl).getValue());
        }
      }
      temp.put(ctrl.getType().toString(), ctrl);
    }
    return temp;
  }

  public Map<String, Control> getControls() {
    return controlTable;
  }

  @Override
  public void run() {
    if (line != null) {
      line.start();

      byte[] buffer = new byte[ais.getFormat().getFrameSize()];
      int i;
      while (!worker.isShutdown()) {
        if (!paused) {
          try {
            while (playing && !paused && (i = ais.read(buffer)) > -1)
              line.write(buffer, 0, i);
            if (!paused) {
              reset();
              playing = false;
              worker.shutdown();
              events.dispatchStatusEvent(TailwindStatus.END);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          while (paused) {
            try {
              synchronized (referencable) {
                referencable.wait();
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }

    }
  }

}
