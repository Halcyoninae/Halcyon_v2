package org.tritonus.lowlevel.gsm;

/**
 * Constants for frame sizes etc in GSM 06.10.
 *
 * <p>
 * For explanation of the frame formats see {@link GsmFrameFormat}.
 * </p>
 *
 * @author Matthias Pfisterer
 * @see GsmFrameFormat
 */
public interface GsmConstants {
    /**
     * Samples per frame in toast frame format.
     */
    int TOAST_SAMPLES_PER_FRAME = 160;

    /**
     * Samples per frame in Microsoft frame format.
     */
    int MICROSOFT_SAMPLES_PER_FRAME = 320;

    /**
     * Length of a coded frame in bytes in toast frame format.
     */
    int TOAST_ENCODED_GSM_FRAME_SIZE = 33;

    /**
     * Length of a coded frame in bytes in Microsoft frame format.
     */
    int MICROSOFT_ENCODED_GSM_FRAME_SIZE = 65;
}
