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

package com.jackmeng.halcyoninae.halcyon.debug;

import com.jackmeng.halcyoninae.halcyon.global.TObject;

import java.util.Arrays;

/**
 * Custom Alert Debugger TextConstructor
 * <p>
 * This class is read only once all its
 * attributes has been set (via the constructor)
 *
 * @author Jack Meng
 * @since 3.3
 */
public class TConstr {
    private final TObject<?>[] payload;
    private final CLIStyles[] start;

    @SafeVarargs
    public <T> TConstr(CLIStyles[] start, T... payload) {
        this.payload = new TObject[payload.length];
        for (int i = 0; i < payload.length; i++) {
            this.payload[i] = new TObject<>(payload[i]);
        }
        this.start = start;
    }

    public <T> TConstr(CLIStyles start, T payload) {
        this.payload = new TObject[]{new TObject<>(payload)};
        this.start = new CLIStyles[]{start};
    }

    /**
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(start).forEach(x -> sb.append(x.getColor()));
        Arrays.asList(payload).forEach(x -> sb.append(x.first.toString()));
        return sb.toString();
    }
}
