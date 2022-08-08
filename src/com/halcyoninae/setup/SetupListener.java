package com.halcyoninae.setup;

/**
 * A setup listener to do things when a
 * setup event is called.
 *
 * @author Jack Meng
 * @since 3.3
 */
public interface SetupListener {
    void updateStatus(SetupStatus e);
}
