/*
 *	Sequencer.java
 *
 *	This file is part of Tritonus: http://www.tritonus.org/
 */

/*
 *  Copyright (c) 1999 - 2004 by Matthias Pfisterer
 *
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

package javax.sound.midi;

import java.io.IOException;
import java.io.InputStream;


public interface Sequencer
        extends MidiDevice {
    int LOOP_CONTINUOUSLY = -1;

    Sequence getSequence();

    void setSequence(Sequence sequence)
            throws InvalidMidiDataException;

    void setSequence(InputStream inputStream)
            throws InvalidMidiDataException, IOException;

    void start();

    long getLoopStartPoint();

    void setLoopStartPoint(long lTick);

    long getLoopEndPoint();

    void setLoopEndPoint(long lTick);

    int getLoopCount();

    void setLoopCount(int nLoopCount);

    void stop();


    boolean isRunning();

    void startRecording();

    void stopRecording();

    boolean isRecording();

    // name should be: enableRecording
    void recordEnable(Track track, int nChannel);

    // name should be: disableRecording
    void recordDisable(Track track);


    float getTempoInBPM();

    void setTempoInBPM(float fBPM);


    float getTempoInMPQ();

    void setTempoInMPQ(float fMPQ);


    float getTempoFactor();

    void setTempoFactor(float fFactor);


    long getTickLength();

    long getTickPosition();

    void setTickPosition(long lTick);


    long getMicrosecondLength();

    long getMicrosecondPosition();

    void setMicrosecondPosition(long lMicroseconds);


    Sequencer.SyncMode getMasterSyncMode();

    void setMasterSyncMode(Sequencer.SyncMode syncMode);

    Sequencer.SyncMode[] getMasterSyncModes();


    Sequencer.SyncMode getSlaveSyncMode();

    void setSlaveSyncMode(Sequencer.SyncMode syncMode);

    Sequencer.SyncMode[] getSlaveSyncModes();


    void setTrackMute(int nTrack, boolean bMute);

    boolean getTrackMute(int nTrack);

    void setTrackSolo(int nTrack, boolean bSolo);

    boolean getTrackSolo(int nTrack);


    boolean addMetaEventListener(MetaEventListener listener);

    void removeMetaEventListener(MetaEventListener listener);


    int[] addControllerEventListener(ControllerEventListener listener, int[] anControllers);

    int[] removeControllerEventListener(ControllerEventListener listener, int[] anControllers);


////////////////////////// INNER CLASSES //////////////////////////////

    class SyncMode {
        public static final SyncMode INTERNAL_CLOCK = new SyncMode("Internal Clock");
        public static final SyncMode MIDI_SYNC = new SyncMode("MIDI Sync");
        public static final SyncMode MIDI_TIME_CODE = new SyncMode("MIDI Time Code");
        public static final SyncMode NO_SYNC = new SyncMode("No Timing");


        private final String m_strName;


        protected SyncMode(String strName) {
            m_strName = strName;
        }


        public final boolean equals(Object obj) {
            return super.equals(obj);
        }


        public final int hashCode() {
            return super.hashCode();
        }


        public final String toString() {
            return super.toString() + "[name=" + m_strName + "]";
        }


    }
}


/*** Sequencer.java ***/
