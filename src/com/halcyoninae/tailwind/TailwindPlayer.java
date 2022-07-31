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

package com.halcyoninae.tailwind;

import com.halcyoninae.cosmos.components.dialog.ErrorWindow;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.halcyoninae.tailwind.simple.FileFormat;

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
  private AudioFormat formatAudio;
  private final Object referencable = new Object();
  private final TailwindEventManager events;

  public static final int BUFF_SIZE = 1024;

  // PUBLIC STATIC UTIL
  public static String MASTER_GAIN_STR = "Master Gain", BALANCE_STR = "Balance", PAN_STR = "Pan";

  public TailwindPlayer() {
    events = new TailwindEventManager();
    events.addStatusUpdateListener(new TailwindDefaultListener(this));
  }

  /**
   * @param url
   */
  @Override
  public void open(File url) {
    if (isOpen() || isPlaying() || (worker != null && (!worker.isShutdown() || !worker.isTerminated()))) {
      close();
    }
    try {
      this.resource = url;
      this.format = FileFormat.getFormatByName(this.resource.getName());
      Debugger.unsafeLog("TailwindPlayer> Opening: " + resource.getAbsolutePath());
      ais = AudioUtil.getAudioIS(resource.toURI().toURL());
      assert ais != null;
      microsecondLength = (long) (1000000 *
          (ais.getFrameLength() /
              ais.getFormat().getFrameRate()));
      frameLength = ais.getFrameLength();

      if (new AudioInfo(url).getTag(AudioInfo.KEY_MEDIA_DURATION) == null) {
        if (this.microsecondLength < 0) {
          byte[] buffer = new byte[4096];
          int readBytes = 0;

          while ((readBytes = this.ais.read(buffer)) != -1) {
            this.frameLength += readBytes;
          }

          this.frameLength /= this.ais.getFormat().getFrameSize();
          this.ais.close();
          this.ais = AudioUtil.getAudioIS(this.resource.toURI().toURL());
          this.microsecondLength = (long) (1000000 *
              (frameLength / this.ais.getFormat().getFrameRate()));
        }
      } else {
        if (microsecondLength < 0) {
          frameLength = ais.getFrameLength();
          microsecondLength = 1000000L * Integer.parseInt(new AudioInfo(url).getTag(AudioInfo.KEY_MEDIA_DURATION));
        }
      }

      DataLine.Info info = new DataLine.Info(
          SourceDataLine.class,
          ais.getFormat());
      formatAudio = ais.getFormat();
      this.line = (SourceDataLine) AudioSystem.getLine(info);
      this.line.open(ais.getFormat());
      controlTable = TailwindPlayer.setControls(this.line, controlTable);
      this.open = true;
      events.dispatchStatusEvent(TailwindStatus.OPEN);
      events.dispatchGenericEvent(new TailwindEvent(new AudioInfo(resource)));
    } catch (Exception e) {
      new ErrorWindow("There was an error reading this file!").run();
      ResourceFolder.dispatchLog(e);
    }
  }

  /**
   * @return long
   */
  public synchronized long getMicrosecondLength() {
    return microsecondLength;
  }

  /**
   * @return long
   */
  public synchronized long getLength() {
    return microsecondLength / 1000;
  }

  /**
   * @return boolean
   */
  public synchronized boolean isPlaying() {
    return playing;
  }

  /**
   * @return AudioFormat
   */
  public synchronized AudioFormat getAudioFormatAbsolute() {
    return formatAudio;
  }

  /**
   * @return boolean
   */
  public synchronized boolean isPaused() {
    return paused;
  }

  /**
   * @return boolean
   */
  public synchronized boolean isOpen() {
    return open;
  }

  /**
   * @return long
   */
  public synchronized long getFrameLength() {
    return frameLength;
  }

  /**
   * @return FileFormat
   */
  public synchronized FileFormat getFileFormat() {
    return format;
  }

  /**
   * @return The current source data line's position in microsecond
   */
  public synchronized long getPositionMicro() {
    return line != null ? line.getMicrosecondPosition() : 0l;
  }

  /**
   * @return long
   */
  public synchronized long getPosition() {
    return getPositionMicro() / 1000L;
  }

  /**
   * @return long
   */
  public synchronized long getLongFramePosition() {
    return line.getLongFramePosition();
  }

  /**
   * @param e
   * @return boolean
   */
  public synchronized boolean addGenericUpdateListener(TailwindListener.GenericUpdateListener e) {
    return events.addGenericUpdateListener(e);
  }

  /**
   * @param e
   */
  public synchronized void setFrameBufferListener(TailwindListener.FrameBufferListener e) {
    events.addFrameBufferListener(e);
  }

  /**
   * @param e
   * @return boolean
   */
  public synchronized boolean addStatusUpdateListener(TailwindListener.StatusUpdateListener e) {
    return events.addStatusUpdateListener(e);
  }

  /**
   * @param e
   * @return boolean
   */
  public synchronized boolean addTimeListener(TailwindListener.TimeUpdateListener e) {
    return events.addTimeListener(e);
  }

  /**
   * @param e
   */
  public synchronized void addLineListener(LineListener e) {
    line.addLineListener(e);
  }

  /**
   * @param url
   */
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
    worker = Executors.newSingleThreadScheduledExecutor();
    worker.execute(this);
    playing = true;

    events.dispatchStatusEvent(TailwindStatus.PLAYING);
  }

  private void resetProperties() {
    playing = false;
    paused = false;
    open = false;
  }

  /**
   * @param percent
   */
  @Override
  public void setGain(float percent) {
    FloatControl control = (FloatControl) this.controlTable.get(MASTER_GAIN_STR);
    control.setValue(percent < control.getMinimum() ? control.getMinimum()
        : (Math.min(percent, control.getMaximum())));
  }

  /**
   * @param balance
   */
  @Override
  public void setBalance(float balance) {
    FloatControl bal = (FloatControl) this.controlTable.get(BALANCE_STR);
    bal.setValue(
        balance < bal.getMinimum() ? bal.getMinimum() : (Math.min(balance, bal.getMaximum())));
  }

  /**
   * @param pan
   */
  public void setPan(float pan) {
    FloatControl ctrl = (FloatControl) this.controlTable.get(PAN_STR);
    ctrl.setValue(
        pan < ctrl.getMinimum() ? ctrl.getMinimum() : (Math.min(pan, ctrl.getMaximum())));
  }

  /**
   * @param mute
   */
  @Override
  public void setMute(boolean mute) {
    throw new UnsupportedOperationException("This method should not be used directly via the Tailwind Implementation!");
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

  /**
   * @param millis
   */
  @Override
  public void seek(long millis) {
    setPosition(millis);
  }

  /**
   * @param millis
   */
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

  /**
   * @param frame
   */
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

  /**
   * @param line
   * @param table
   * @return e
   */
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

  /**
   * @return e
   */
  public Map<String, Control> getControls() {
    return controlTable;
  }

  @Override
  public void run() {
    if (line != null) {
      byte[] buffer = null;
      if (!ResourceFolder.pm.get(ProgramResourceManager.KEY_AUDIO_DEFAULT_BUFFER_SIZE).equals("auto")) {
        try {
          buffer = new byte[Integer
              .parseInt(ResourceFolder.pm.get(ProgramResourceManager.KEY_AUDIO_DEFAULT_BUFFER_SIZE))];
        } catch (Exception e) {
          new ErrorWindow(
              "<html><p>Failed to allocate the necessary amount to the buffer!<br>Do not modify the property (set to \"auto\") for buffer allocation<br>unless you know what you are doing!</p></html>")
              .run();
          e.printStackTrace();
        }
      }
      int i;
      if (buffer == null) {
        buffer = new byte[BUFF_SIZE * formatAudio.getChannels()
            * TailwindTranscoder.normalize(formatAudio.getSampleSizeInBits())];
      }
      line.start();
      while (!worker.isShutdown()) {
        if (!paused) {
          try {
            if (isOpen()) {
              while (playing && !paused && isOpen() && (i = ais.read(buffer)) != -1) {
                if (paused || !playing || !isOpen()) {
                  break;
                }

                line.write(buffer, 0, i);
              }
            }
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