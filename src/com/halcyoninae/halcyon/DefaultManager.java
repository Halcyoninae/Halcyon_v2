/*
 *  Copyright: (C) 2022 name of Jack Meng
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

package com.halcyoninae.halcyon;

/**
 * This interface primarily manages
 * any links and does not seem to manage
 * any data. It is similar to Manager
 *
 * @author Jack Meng
 * @see com.halcyoninae.halcyon.constant.Manager
 * @since 3.0
 */
public final class DefaultManager {

    public static final String PROJECT_GITHUB_PAGE = "https://github.com/Exoad4JVM/mp4j";
    public static final String FILE_SLASH = System.getProperty("file.separator");
    public static boolean DEBUG_PROGRAM = false;

    private DefaultManager() {
    }
}
