/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.halcyoninae.halcyon.debug;

import com.halcyoninae.halcyon.global.TObject;

import java.util.Arrays;

/**
 * Custom Alert Debugger TextConstructor
 *
 * This class is read only once all its
 * attributes has been set (via the constructor)
 *
 * @author Jack Meng
 * @since 3.3
 */
public class TConstr {
    private TObject<?>[] payload;
    private CLIStyles[] start;

    @SafeVarargs
    public <T> TConstr(CLIStyles[] start, T... payload) {
        this.payload = new TObject[payload.length];
        for (int i = 0; i < payload.length; i++) {
            this.payload[i] = new TObject<>(payload[i]);
        }
        this.start = start;
    }

    public <T> TConstr(CLIStyles start, T payload) {
        this.payload = new TObject[] { new TObject<>(payload) };
        this.start = new CLIStyles[] { start };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(start).forEach(x -> sb.append(x.getColor()));
        Arrays.asList(payload).forEach(x -> sb.append(x.first.toString()));
        return sb.toString();
    }
}
