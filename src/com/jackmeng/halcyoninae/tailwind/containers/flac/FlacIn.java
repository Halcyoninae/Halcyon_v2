package com.jackmeng.halcyoninae.tailwind.containers.flac;

import de.jarnbjo.flac.FlacStream;
import de.jarnbjo.ogg.EndOfOggStreamException;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;

public class FlacIn extends InputStream {
    private final FlacStream stream;

    public FlacIn(FlacStream s) {
        this.stream = s;
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
        return new AudioFormat(stream.getStreamInfo().getSampleRate(), stream.getStreamInfo().getBitsPerSample(), stream.getStreamInfo().getChannels(), true, true);
    }

}
