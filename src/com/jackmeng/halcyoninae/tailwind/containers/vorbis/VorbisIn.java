/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.tailwind.containers.vorbis;

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


    /**
     * @param buffer
     * @param offset
     * @param length
     * @return int
     * @throws IOException
     */
    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        try {
            return this.stream.readPcm(buffer, offset, length);
        } catch (EndOfOggStreamException e) {
            // IGNORED
        }
        return -1;
    }


    /**
     * @param buffer
     * @return int
     * @throws IOException
     */
    @Override
    public int read(byte[] buffer) throws IOException {
        return this.read(buffer, 0, buffer.length);
    }


    /**
     * @return int
     * @throws IOException
     */
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
