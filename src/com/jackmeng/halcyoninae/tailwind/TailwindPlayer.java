/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.cosmos.dialog.ErrorWindow;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.jackmeng.halcyoninae.tailwind.audioinfo.AudioInfo;
import com.jackmeng.halcyoninae.tailwind.simple.FileFormat;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The official adapted audio framework for the Halcyon Program.
 * <p>
 * This framework is licensed under the GPL-2.0 license. If you are to
 * incorporate this in your own program, you must follow the addendums
 * of the GPL-2.0 license.
 * <p>
 * This engine is multi-use meaning it is much more efficient for you
 * to have one instance of this class and use it throughout your program.
 * Reuse of this player is done by closing and opening again.
 * <p>
 * An opening call while it is already opened will cause the stream to close
 * forcefully. It is suggested for the user to strongly check if the stream
 * is open beforehand and handle it themselves accordingly, instead of
 * letting {@link #open(File)} handle it itself.
 * <p>
 * A good system to implement is a playlist feature where data is constantly
 * fed into the player once the old stream ends. This can be done via
 * listener and by handling the open and play functions accordingly.
 * <p>
 * For different circumstances it should be noted that the usage
 * of an opening buffer is not used and every thing is gathered from
 * AudioFormat. This results in less overhead and less cpu usage.
 * <p>
 * Implementation for different audio formats is provided by the
 * AudioUtil class.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class TailwindPlayer implements Audio {
    // PUBLIC STATIC UTIL START
    public static final int MAGIC_NUMBER       = 4096;
    public static final String MASTER_GAIN_STR = "Master Gain", BALANCE_STR = "Balance", PAN_STR = "Pan";
    // PUBLIC STATIC UTIL END
    private final Object referencable          = new Object();
    private final Object timeRef               = new Object();
    private final TailwindEventManager events;
    private File resource;
    private int my_magic_number                = MAGIC_NUMBER;
    private SourceDataLine line;
    private TailwindPipelineMethod pipeline;
    private FileFormat format;
    private Map<String, Control> controlTable;
    private boolean open, paused, playing, forceClose;
    private AudioInputStream ais;
    private long microsecondLength, frameLength, milliPos;
    private ExecutorService worker;
    private AudioFormat formatAudio;

    public TailwindPlayer(boolean useAutoClose) {
        events = new TailwindEventManager();
        pipeline = TailwindPipelineMethod.DEFAULT_;
        if (useAutoClose)
            events.addStatusUpdateListener(new TailwindDefaultListener(this));
    }

    /**
     * @param s
     */
    public void setDynamicAllocation(boolean s) {
        my_magic_number = s ? -1 : MAGIC_NUMBER;
    }

    /**
     * @return TailwindPipelineMethod
     */
    public synchronized TailwindPipelineMethod getPipelineMethod() {
        return pipeline;
    }

    /**
     * @param pipeline
     */
    public synchronized void setPipelineMethod(TailwindPipelineMethod pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * @return boolean
     */
    public synchronized boolean isDefaultPipeline() {
        return pipeline.equals(TailwindPipelineMethod.DEFAULT_);
    }

    /**
     * @return boolean
     */
    public boolean isForceCloseOnOpen() {
        return forceClose;
    }

    /**
     * @param s
     */
    public void setForceCloseOnOpen(boolean s) {
        this.forceClose = s;
    }

    /**
     * @return boolean
     */
    public boolean isDyanmicAllocated() {
        return my_magic_number >= 4;
    }

    /**
     * @param line
     * @param table
     * @return e
     */
    private Map<String, Control> setControls(Line line, Map<String, Control> table) {
        if (isDefaultPipeline()) {
            Map<String, Control> temp = new WeakHashMap<>();
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
        return Collections.emptyMap();
    }

    /**
     * Note: This method does not check the exact validity of a file.
     * <p>
     * Opens the line for reading and playback.
     * <p>
     * This method will try its best to handle missing media length
     * by manually reading per frame-which may take time.
     *
     * @param url Open a resource from a file
     */
    @Override
    public void open(File url) {
        if (isForceCloseOnOpen()
                && (isOpen() || isPlaying() || (worker != null && (!worker.isShutdown() || !worker.isTerminated()))))
            close();
        if (!isForceCloseOnOpen() && (isOpen() || isPlaying()
                || (worker != null && (!worker.isShutdown() || !worker.isTerminated())))) {
            return;
        }
        if (isDefaultPipeline()) {
            try {
                milliPos = 0L;
                this.resource = url;
                this.format = FileFormat.getFormatByName(this.resource.getName());
                Debugger.unsafeLog("TailwindPlayer> Opening: " + resource.getAbsolutePath());
                ais = TailwindHelper.getAudioIS(resource.toURI().toURL());
                assert ais != null;
                microsecondLength = (long) (1000000 *
                        (ais.getFrameLength() /
                                ais.getFormat().getFrameRate()));
                frameLength = ais.getFrameLength();

                if (new AudioInfo(url).getTag(AudioInfo.KEY_MEDIA_DURATION) == null) {
                    if (this.microsecondLength < 0) {
                        byte[] buffer = new byte[MAGIC_NUMBER];
                        int readBytes;

                        while ((readBytes = this.ais.read(buffer)) != -1) {
                            this.frameLength += readBytes;
                        }

                        this.frameLength /= this.ais.getFormat().getFrameSize();
                        this.ais.close();
                        this.ais = TailwindHelper.getAudioIS(this.resource.toURI().toURL());
                        assert this.ais != null;
                        this.microsecondLength = (long) (1000000 *
                                (frameLength / this.ais.getFormat().getFrameRate()));
                    }
                } else {
                    if (microsecondLength < 0) {
                        frameLength = ais.getFrameLength();
                        microsecondLength = 1000000L
                                * Integer.parseInt(new AudioInfo(url).getTag(AudioInfo.KEY_MEDIA_DURATION));
                    }
                }

                DataLine.Info info = new DataLine.Info(
                        SourceDataLine.class,
                        ais.getFormat());
                formatAudio = ais.getFormat();
                this.line = (SourceDataLine) AudioSystem.getLine(info);
                this.line.open(ais.getFormat());
                controlTable = setControls(this.line, controlTable);
                this.open = true;
                events.dispatchStatusEvent(TailwindStatus.OPEN);
                events.dispatchGenericEvent(new TailwindEvent(new AudioInfo(resource)));
            } catch (Exception e) {
                new ErrorWindow("There was an error reading this file!\n" + e.getMessage()).run();
                ExternalResource.dispatchLog(e);
            }
        }
    }

    /**
     * @return long Returns the length of the media file in MicroSeconds
     */
    public synchronized long getMicrosecondLength() {
        return microsecondLength;
    }

    /**
     * @return long Returns the length of the media file in MilliSeconds
     */
    public synchronized long getLength() {
        return microsecondLength / 1000;
    }

    /**
     * @return boolean (true || false) if the stream is detected to be playing
     */
    public synchronized boolean isPlaying() {
        return playing;
    }

    /**
     * @return AudioFormat Get the absolute audio format obj representing the
     *         current stream
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
     * Defaults to the SourceDataLine's microsecond
     * position.
     * <p>
     * NOTE: This method does not take into account any
     * time seeking etc and only takes into account the time
     * since the line was opened.
     *
     * @return The microsecond position.
     */
    public synchronized long getMicrosecondPosition() {
        return isDefaultPipeline()
                ? (line != null ? line.getMicrosecondPosition() : 0L)
                : 0L;
    }

    /**
     * Does not use the internal SourceDataLine's microsecond
     * positioning.
     * <p>
     * NOTE: This method does not gurantee absolute precision;
     * however, it does take into account any time modifications
     * including seeking.
     *
     * @return long The millisecond position
     */
    public synchronized long getPosition() {
        return milliPos;
    }

    /**
     * @param millis
     */
    @Override
    public synchronized void setPosition(long millis) {
        if (isDefaultPipeline() && isOpen()) {
            setFramePosition((long) (ais.getFormat().getFrameRate() / 1000F) * millis);
        }
    }

    /**
     * Uses the line's frame position to determine where the current
     * pointer is.
     *
     * @return long Frame Position from the DataLine
     */
    public synchronized long getLongFramePosition() {
        return isDefaultPipeline() ? line.getLongFramePosition() : 0L;
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
        if (isDefaultPipeline()) {
            if (open) {
                try {
                    resetProperties();
                    if (!worker.isShutdown()) {
                        worker.shutdown();
                    }
                    if (line != null) {
                        line.stop();
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
    }

    /**
     * Fades out the audio until the audio dies.
     * <p>
     * <p>
     * NOTE: This method does not automatically take care
     * of draining and closing the current line and stream.
     *
     * @param time Time in milliseconds for this action to take
     *             place in.
     */
    public void fadeOut(int time) {
        if (isDefaultPipeline()) {
            if (line != null) {
                FloatControl control = (FloatControl) this.controlTable.get(MASTER_GAIN_STR);
                if (control.getUpdatePeriod() != -1) {
                    control.shift(
                            control.getValue(),
                            control.getMinimum(), time * 1000);
                } else {
                    Debugger.info("Automatic Updates Not Supported");
                    // later i will implement this
                }
            }
        }
    }

    @Override
    public void play() {
        if (playing || paused) {
            pause();
        }
        if (isDefaultPipeline()) {
            worker = Executors.newSingleThreadExecutor();
            worker.execute(this.new StandardPipeLine());

            ExecutorService timeWorker = Executors.newSingleThreadExecutor();
            timeWorker.execute(() -> {
                while (!timeWorker.isShutdown()) {
                    if (!paused) {
                        if (open) {
                            while (isPlaying() && !isPaused() && isOpen()) {
                                milliPos += 5;
                                try {
                                    Thread.sleep(5L);
                                } catch (InterruptedException e) {
                                    // DO NOTHING
                                }
                            }
                        } else {
                            timeWorker.shutdown();
                            milliPos = 0L;
                        }
                    } else {
                        while (paused) {
                            try {
                                synchronized (timeRef) {
                                    timeRef.wait();
                                }
                            } catch (InterruptedException e) {
                                // IGNORE
                            }
                        }
                    }
                }

            });
        }
        playing = true;
        events.dispatchStatusEvent(TailwindStatus.PLAYING);

    }

    private void resetProperties() {
        playing = false;
        paused = false;
        open = false;
        milliPos = 0L;
    }

    /**
     * @param percent
     */
    @Override
    public void setGain(float percent) {
        if (isDefaultPipeline()) {
            FloatControl control = (FloatControl) this.controlTable.get(MASTER_GAIN_STR);
            control.setValue(percent < control.getMinimum() ? control.getMinimum()
                    : (Math.min(percent, control.getMaximum())));
            if (((int) control.getValue()) == ((int) control.getMaximum())) {
                Debugger.good(">3< Earrape Mode! Let's go!");
            }
        }
    }

    /**
     * @param balance
     */
    @Override
    public void setBalance(float balance) {
        if (isDefaultPipeline()) {
            FloatControl bal = (FloatControl) this.controlTable.get(BALANCE_STR);
            bal.setValue(
                    balance < bal.getMinimum() ? bal.getMinimum() : (Math.min(balance, bal.getMaximum())));
        }
    }

    /**
     * @param pan
     */
    public void setPan(float pan) {
        if (isDefaultPipeline()) {
            FloatControl ctrl = (FloatControl) this.controlTable.get(PAN_STR);
            ctrl.setValue(
                    pan < ctrl.getMinimum() ? ctrl.getMinimum() : (Math.min(pan, ctrl.getMaximum())));
        }
    }

    /**
     * @param mute
     */
    @Override
    public void setMute(boolean mute) {
        throw new UnsupportedOperationException(
                "This method should not be used directly via the Tailwind Implementation!");
    }

    @Override
    public void resume() {
        if (isDefaultPipeline()) {
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
        events.dispatchStatusEvent(TailwindStatus.RESUMED);
    }

    /**
     * This method provides a safety check upon the given
     * time to seek.
     * <p>
     * This method takes the current position of the
     * stream and adds the given millis parameter to
     * that time and resume play from there.
     * <p>
     * If the method is called with -2, the stream will
     * skip to the beginning (essentially restarting the
     * stream); while -1 as a parameter
     * will skip to the of the stream (essentially closing
     * the stream).
     *
     * @param millis The milliseconds to skip
     */
    @Override
    public void seekTo(long millis) {
        if (isDefaultPipeline()) {
            if (open || playing) {
                long time = getPosition() + millis;
                Debugger.info("Vanilla Time Submission:" + millis + "\nTime Submission: " + time + "\nFor Pos: "
                        + getPosition() + "\nFor Length: " + getMicrosecondLength() / 1000L + "\n"
                        + TimeParser.fromMillis(millis) + "\nTime sub: " + TimeParser.fromMillis(time)
                        + "\nCurrent Time"
                        + TimeParser.fromMillis(milliPos));
                if (time < 0 || millis == -2) {
                    milliPos = 0L;
                    setPosition(0);
                    Debugger.warn("FAULT: Lower bound exceeded for parameter: " + millis + "(ms)");
                } else if (time > getMicrosecondLength() / 1000L || millis == -1) {
                    milliPos = getMicrosecondLength() / 1000L;
                    setPosition(getMicrosecondLength() / 1000L);
                    Debugger.warn("FAULT: Upper bound exceeded for parameter: " + millis + "(ms)");
                } else {
                    milliPos = time;
                    setPosition(time);
                    Debugger.good("OK: Bound checked. Skipping: " + millis);
                }
            }
        }
    }

    @Override
    public void pause() {
        if (isDefaultPipeline()) {
            if (playing && !paused) {
                paused = true;
                playing = false;
                events.dispatchStatusEvent(TailwindStatus.PAUSED);
            }
        }
    }

    @Override
    public void stop() {
        if (isDefaultPipeline()) {
            playing = false;
            if (paused) {
                synchronized (referencable) {
                    referencable.notifyAll();
                }
                paused = false;
            }
            setPosition(0);
        }
        events.dispatchStatusEvent(TailwindStatus.CLOSED);
    }

    /**
     * @param frame
     */
    public void setFramePosition(long frame) {
        if (isDefaultPipeline()) {
            try {
                if (playing)
                    pause();

                if (frame <= frameLength)
                    reset();

                byte[] buffer = new byte[ais.getFormat().getFrameSize()];
                long curr = 0L;

                while (curr < frame) {
                    ais.read(buffer);
                    curr++;
                }
                Debugger.warn("Skipped frames: " + curr);

                if (paused) {
                    resume();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        close();
        open(resource);
    }

    /**
     * @return e
     */
    public Map<String, Control> getControls() {
        return controlTable;
    }

    public class StandardPipeLine implements Runnable {
        @Override
        public void run() {
            if (line != null) {
                byte[] buffer = null;
                int i;
                if (!ExternalResource.pm.get(ProgramResourceManager.KEY_AUDIO_DEFAULT_BUFFER_SIZE).equals("auto")) {
                    try {
                        buffer = new byte[Integer
                                .parseInt(
                                        ExternalResource.pm.get(ProgramResourceManager.KEY_AUDIO_DEFAULT_BUFFER_SIZE))];
                    } catch (Exception e) {
                        new ErrorWindow(
                                "<html><p>Failed to allocate the necessary amount to the buffer!<br>Do not modify the property (set to \"auto\") for buffer allocation<br>unless you know what you are doing!</p></html>")
                                .run();
                        e.printStackTrace();
                    }
                } else {
                    /*
                     * int nb = TailwindTranscoder.normalize(formatAudio.getSampleSizeInBits());
                     * float[] samples = new float[MAGIC_NUMBER * formatAudio.getChannels()];
                     * long[] transfer = new long[samples.length];
                     */
                    buffer = new byte[my_magic_number == -1
                            ? ais.getFormat().getFrameSize() * 2
                            : MAGIC_NUMBER];
                    Debugger.warn("Tailwind_buffer_size: " + buffer.length);
                }
                line.start();

                while (!worker.isShutdown()) {
                    if (!paused) {
                        try {
                            if (isOpen()) {
                                while (playing && !paused && isOpen() && (i = ais.read(buffer)) > -1) {
                                    line.write(buffer, 0, i);
                                }
                            }
                            if (!paused) {
                                reset();
                                playing = false;
                                worker.shutdown();
                                worker.awaitTermination(25L, TimeUnit.MILLISECONDS);
                                events.dispatchStatusEvent(TailwindStatus.END);
                                Debugger.warn("=========TailwindPlayer STOP=========\n");
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

}