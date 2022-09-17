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

package com.jackmeng.halcyoninae.cosmos.tasks;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane;

import java.util.concurrent.ExecutorService;

/**
 * A class designed to constantly ping
 * the file view system in order to alert
 * it of any change.
 * <p>
 * In order to automatically update the file view
 * system without the user having to update it manually.
 */
public final class PingFileView implements Runnable {
    private final BottomPane bp;
    private ExecutorService worker;

    /**
     * Calls the default BottomPane Object
     *
     * @param bp the bottompane instance
     * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane
     */
    public PingFileView(BottomPane bp) {
        this.bp = bp;
    }

    @Override
    public void run() {
        if (worker == null) {
            /*
             * worker = new Thread(() -> {
             * while (true) {
             * bp.mastRevalidate();
             * Program.cacher.pingLikedTracks();
             * Program.cacher.pingSavedPlaylists();
             * Program.cacher.forceSaveQuiet();
             * try {
             * Thread.sleep(ConcurrentTiming.MAX_TLE);
             * } catch (InterruptedException e) {
             * e.printStackTrace();
             * }
             * }
             * });
             * worker.start();
             */
        }
    }

}
