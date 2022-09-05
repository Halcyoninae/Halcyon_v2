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

package com.jackmeng.halcyoninae.cosmos.tasks;

import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class aims to optimize tasks throughout the JVM
 * environment for this application during runtime.
 * <p>
 * It runs in the background and tries to optimize any
 * tasks that may be running in the background.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class DefunctOptimizer implements Runnable {

    @Override
    public void run() {
        if (ExternalResource.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("true")) {
            ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
            s.scheduleAtFixedRate(System::gc, 0, 250, TimeUnit.MILLISECONDS);
        }
    }
}
