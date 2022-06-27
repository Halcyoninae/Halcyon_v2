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
  private int buffer_size = 4096;

  // PUBLIC STATIC UTIL
  public static String MASTER_GAIN_STR = "Master Gain", BALANCE_STR = "Balance";

  public TailwindPlayer() {
    events = new TailwindEventManager();
    TailwindDefaultListener tdfl = new TailwindDefaultListener(this);
    events.addStatusUpdateListener(tdfl);
    events.addGenericUpdateListener(tdfl);
  }

  public TailwindPlayer(int bufferSize) {
    this();
    this.buffer_size = bufferSize;
  }

  @Override
  public synchronized void open(File url) {
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
        byte[] readableBuffer = new byte[Math.max(buffer_size, 1024)];
        int read;

        while ((read = ais.read(readableBuffer)) != -1) {
          frameLength += read;
        }

        frameLength /= this.ais.getFormat().getFrameSize();
        ais.close();
        ais = AudioUtil.getAudioIS(resource.toURI().toURL());
        assert ais != null;
        microsecondLength = (long) (1000000 *
            (frameLength / ais.getFormat().getFrameRate()));
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

  public long getMicrosecondLength() {
    return microsecondLength;
  }

  public long getLength() {
    return getMicrosecondLength() / 1000;
  }

  public boolean isPlaying() {
    return playing;
  }

  public boolean isPaused() {
    return paused;
  }

  public boolean isOpen() {
    return open;
  }

  public long getFrameLength() {
    return frameLength;
  }

  public FileFormat getFileFormat() {
    return format;
  }

  /**
   * @return The current source data line's position in microsecond
   */
  public long getPositionMicro() {
    return line.getMicrosecondPosition();
  }

  public long getPosition() {
    return getPosition() / 1000L;
  }

  public long getLongFramePosition() {
    return line.getLongFramePosition();
  }

  public boolean addGenericUpdateListener(TailwindListener.GenericUpdateListener e) {
    return events.addGenericUpdateListener(e);
  }

  public boolean addStatusUpdateListener(TailwindListener.StatusUpdateListener e) {
    return events.addStatusUpdateListener(e);
  }

  public boolean addTimeListener(TailwindListener.TimeUpdateListener e) {
    return events.addTimeListener(e);
  }

  @Override
  public synchronized void open(URL url) {
    this.open(new File(url.getFile()));
  }

  @Override
  public synchronized void close() {
    if (open) {
      try {
        resetProperties();
        if (line != null) {
          line.flush();
          line.drain();
          line = null;
        }

        if (ais != null) {
          ais.close();
          ais = null;
        }
        events.dispatchStatusEvent(TailwindStatus.CLOSED);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public synchronized void play() {
    if (playing || paused) {
      stop();
    }
    worker = Executors.newCachedThreadPool();
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
  public synchronized void setGain(float percent) {
    FloatControl control = (FloatControl) this.controlTable.get(MASTER_GAIN_STR);
    control.setValue(percent < control.getMinimum() ? control.getMinimum()
        : (Math.min(percent, control.getMaximum())));
  }

  @Override
  public synchronized void setBalance(float balance) {
    FloatControl bal = (FloatControl) this.controlTable.get(BALANCE_STR);
    bal.setValue(
        balance < bal.getMinimum() ? bal.getMinimum() : (Math.min(balance, bal.getMaximum())));
  }

  @Override
  public synchronized void setMute(boolean mute) {
  }

  @Override
  public synchronized void resume() {
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
  public synchronized void seek(long millis) {
    setPosition(millis);
  }

  @Override
  public void setPosition(long millis) {
    if (open) {
      setFramePosition((long) (ais.getFormat().getFrameSize() / 1000F) * millis);
    }
  }

  @Override
  public synchronized void pause() {
    if (playing && !paused) {
      paused = true;
      playing = false;
    }
    events.dispatchStatusEvent(TailwindStatus.PAUSED);
  }

  @Override
  public synchronized void stop() {
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

  private void reset() {
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
