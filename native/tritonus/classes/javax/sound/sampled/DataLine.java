/*
 *	DataLine.java
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

package javax.sound.sampled;

import org.tritonus.share.TDebug;
import org.tritonus.share.sampled.AudioFormats;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public interface DataLine
        extends Line {
    void drain();


    void flush();


    void start();


    void stop();


    boolean isRunning();


    boolean isActive();


    AudioFormat getFormat();


    int getBufferSize();


    int available();


    int getFramePosition();


    long getMicrosecondPosition();


    float getLevel();


    class Info
            extends Line.Info {
        private final AudioFormat[] EMPTY_AUDIO_FORMAT_ARRAY = new AudioFormat[0];

        private final List<AudioFormat> m_audioFormats;
        private final int m_nMinBufferSize;
        private final int m_nMaxBufferSize;


        public Info(Class lineClass,
                    AudioFormat[] aAudioFormats,
                    int nMinBufferSize,
                    int nMaxBufferSize) {
            super(lineClass);
            m_audioFormats = Arrays.asList(aAudioFormats);
            m_nMinBufferSize = nMinBufferSize;
            m_nMaxBufferSize = nMaxBufferSize;
        }


        public Info(Class lineClass,
                    AudioFormat audioFormat,
                    int nBufferSize) {
            this(lineClass,
                    new AudioFormat[]{audioFormat},
                    nBufferSize,
                    nBufferSize);
        }


        public Info(Class lineClass,
                    AudioFormat audioFormat) {
            this(lineClass,
                    audioFormat,
                    AudioSystem.NOT_SPECIFIED);
        }


        public AudioFormat[] getFormats() {
            return m_audioFormats.toArray(EMPTY_AUDIO_FORMAT_ARRAY);
        }


        public boolean isFormatSupported(AudioFormat audioFormat) {
            Iterator<AudioFormat> formats = m_audioFormats.iterator();
            while (formats.hasNext()) {
                AudioFormat format = formats.next();
                if (AudioFormats.matches(format, audioFormat)) {
                    return true;
                }
            }
            return false;
        }


        public int getMinBufferSize() {
            return m_nMinBufferSize;
        }


        public int getMaxBufferSize() {
            return m_nMaxBufferSize;
        }


        public boolean matches(Line.Info info) {
            if (TDebug.TraceDataLine) {
                TDebug.out(">DataLine.Info.matches(): called");
                TDebug.out("DataLine.Info.matches(): own info: " + this);
                TDebug.out("DataLine.Info.matches(): test info: " + info.toString());
            }
            if (!super.matches(info)) {
                if (TDebug.TraceDataLine) TDebug.out("<DataLine.Info.matches(): super.matches() does not match()");
                return false;
            }
            if (!(info instanceof DataLine.Info)) {
                return false;
            }
            DataLine.Info dataLineInfo = (DataLine.Info) info;
            // TODO: check against documentation !!
            // $$fb2000-12-02: handle NOT_SPECIFIED
            if ((getMinBufferSize() != AudioSystem.NOT_SPECIFIED
                    && dataLineInfo.getMinBufferSize() != AudioSystem.NOT_SPECIFIED
                    && getMinBufferSize() < dataLineInfo.getMinBufferSize()) ||
                    (getMaxBufferSize() != AudioSystem.NOT_SPECIFIED
                            && dataLineInfo.getMaxBufferSize() != AudioSystem.NOT_SPECIFIED
                            && getMaxBufferSize() > dataLineInfo.getMaxBufferSize())) {
                if (TDebug.TraceDataLine) TDebug.out("<DataLine.Info.matches(): buffer sizes do not match");
                return false;
            }
            // $$fb2000-12-02: it's the other way round !!!
            //                 all of this classes formats must match at least one of
            //                 dataLineInfo's formats.
            Iterator<AudioFormat> formats = m_audioFormats.iterator();
            while (formats.hasNext()) {
                AudioFormat format = formats.next();
                if (TDebug.TraceDataLine) TDebug.out("checking if supported: " + format);
                if (!dataLineInfo.isFormatSupported(format)) {
                    if (TDebug.TraceDataLine) TDebug.out("< format doesn't match");
                    return false;
                }
            }

			/*
			AudioFormat[]	infoFormats = dataLineInfo.getFormats();
			for (int i = 0; i < infoFormats.length; i++)
			{
				if (TDebug.TraceDataLine)
				{
					TDebug.out("checking if supported: " + infoFormats[i]);
				}
				if (!isFormatSupported(infoFormats[i]))
				{
					if (TDebug.TraceDataLine)
					{
						TDebug.out("< formats do not match");
					}
					return false;
				}
			}
			*/
            if (TDebug.TraceDataLine) {
                TDebug.out("< matches: true");
            }
            return true;
        }


        public String toString() {
            AudioFormat[] aFormats = getFormats();
            String strFormats = "formats:\n";
            for (int i = 0; i < aFormats.length; i++) {
                strFormats += aFormats[i].toString() + "\n";
            }
            return super.toString() + strFormats + "minBufferSize=" + getMinBufferSize() + " maxBufferSize=" + getMaxBufferSize();
        }

    }
}


/*** DataLine.java ***/
