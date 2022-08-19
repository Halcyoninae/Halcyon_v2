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

package com.jackmeng.halcyoninae.tailwind.vorbis;

import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.vorbis.VorbisStream;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;

/**
 * Default Vorbis OGG input stream wrapper
 *
 * @author Jack Meng
 * @since 3.3
 */
public class VorbisIn extends InputStream {
    private final VorbisStream stream;

    public VorbisIn(VorbisStream in) {
        this.stream = in;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        try {
            return this.stream.readPcm(buffer, offset, length);
        } catch (EndOfOggStreamException e) {
            // IGNORED
        }
        return -1;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return this.read(buffer, 0, buffer.length);
    }

    @Override
    public int read() throws IOException {
        return 0;
    }


    /**
     * @return AudioFormat Return this ogg stream's format
     */
    public AudioFormat getFormat() {
        return new AudioFormat(stream.getIdentificationHeader().getSampleRate(), 16,
                stream.getIdentificationHeader().getChannels(), true, true);
    }
}
