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

package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.halcyon.utils.Wrapper;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A global scoped targeted towards managing multiple
 * Listeners for the BigContainer player all at the same time without
 * having to hold multiple Lists directly.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class TailwindEventManager {
    private final List<WeakReference<TailwindListener.TimeUpdateListener>> timeListeners;
    private final List<WeakReference<TailwindListener.StatusUpdateListener>> statusUpdateListeners;
    private final List<WeakReference<TailwindListener.GenericUpdateListener>> genericUpdateListeners;
    private TailwindListener.FrameBufferListener bufferListener;

    public TailwindEventManager() {
        timeListeners = new ArrayList<>();
        statusUpdateListeners = new ArrayList<>();
        genericUpdateListeners = new ArrayList<>();
    }

    /**
     * @param e
     * @return boolean
     */
    public boolean addTimeListener(TailwindListener.TimeUpdateListener e) {
        return timeListeners.add(new WeakReference<>(e));
    }

    /**
     * @param e
     * @return boolean
     */
    public boolean addStatusUpdateListener(TailwindListener.StatusUpdateListener e) {
        return statusUpdateListeners.add(new WeakReference<>(e));
    }

    /**
     * @param e
     * @return boolean
     */
    public boolean addGenericUpdateListener(TailwindListener.GenericUpdateListener e) {
        return genericUpdateListeners.add(new WeakReference<>(e));
    }

    /**
     * @param e
     */
    public void addFrameBufferListener(TailwindListener.FrameBufferListener e) {
        this.bufferListener = e;
    }

    /**
     * @return e
     */
    public List<WeakReference<TailwindListener.TimeUpdateListener>> getTimeListeners() {
        return timeListeners;
    }

    /**
     * @return e
     */
    public List<WeakReference<TailwindListener.StatusUpdateListener>> getStatusUpdateListeners() {
        return statusUpdateListeners;
    }

    /**
     * @return e
     */
    public List<WeakReference<TailwindListener.GenericUpdateListener>> getGenericUpdateListeners() {
        return genericUpdateListeners;
    }

    /**
     * @return e
     */
    public TailwindListener.FrameBufferListener getFrameBufferListeners() {
        return bufferListener;
    }

    /**
     * @param time
     */
    public synchronized void dispatchTimeEvent(long time) {
        for (WeakReference<TailwindListener.TimeUpdateListener> e : timeListeners) {
            e.get().trackCurrentTime(time);
        }

    }

    /**
     * @param status
     */
    public synchronized void dispatchStatusEvent(TailwindStatus status) {
        for (WeakReference<TailwindListener.StatusUpdateListener> e : statusUpdateListeners) {
            e.get().statusUpdate(status);
        }

    }

    /**
     * @param event
     */
    public synchronized void dispatchGenericEvent(TailwindEvent event) {
        for (WeakReference<TailwindListener.GenericUpdateListener> e : genericUpdateListeners) {
            e.get().genericUpdate(event);
        }
    }

    /**
     * @param samples
     */
    public synchronized void dispatchNewBufferEvent(byte[] samples) {
        Wrapper.threadedRun(() -> bufferListener.frameUpdate(samples));
    }

}
