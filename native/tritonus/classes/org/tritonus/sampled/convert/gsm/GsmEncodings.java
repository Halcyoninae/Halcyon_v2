package org.tritonus.sampled.convert.gsm;

import org.tritonus.lowlevel.gsm.GsmFrameFormat;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

/**
 * Constants for {@link Encoding} used for GSM 06.10.
 *
 * @author Matthias Pfisterer
 */
public interface GsmEncodings {
    /**
     * Encoding for the "toast" frame format. Corresponds to
     * {@link GsmFrameFormat#TOAST}.
     */
    Encoding TOAST_GSM_ENCODING = new AudioFormat.Encoding(
            "GSM0610");

    /**
     * Encoding for the Microsoft frame format. Corresponds to
     * {@link GsmFrameFormat#MICROSOFT}.
     */
    Encoding MS_GSM_ENCODING = new AudioFormat.Encoding(
            "MS GSM0610");
}
