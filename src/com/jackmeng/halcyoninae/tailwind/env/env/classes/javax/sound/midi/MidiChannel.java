/*
 *	MidiChannel.java
 *
 *	This file is part of Tritonus: http://www.tritonus.org/
 */

/*
 *  Copyright (c) 1999 by Matthias Pfisterer
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


public interface MidiChannel {
    void noteOn(int nNoteNumber, int nVelocity);

    void noteOff(int nNoteNumber, int nVelocity);

    void noteOff(int nNoteNumber);

    void setPolyPressure(int nNoteNumber, int nPressure);

    int getPolyPressure(int nNoteNumber);

    int getChannelPressure();

    void setChannelPressure(int nPressure);

    void controlChange(int nController, int nValue);

    int getController(int nController);

    void programChange(int nProgram);

    void programChange(int nBank, int nProgram);

    int getProgram();

    int getPitchBend();

    void setPitchBend(int nBend);

    void resetAllControllers();

    void allNotesOff();

    void allSoundOff();

    boolean localControl(boolean bOn);

    boolean getMono();

    void setMono(boolean bMono);

    boolean getOmni();

    void setOmni(boolean bOmni);

    boolean getMute();

    void setMute(boolean bMute);

    boolean getSolo();

    void setSolo(boolean bSolo);
}


/*** MidiChannel.java ***/
