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

package com.halcyoninae.tailwind;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a faked AudioInfo object.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class StubAudioInfo extends AudioInfo {
    private final File f;
    private Map<String, String> tags;

    public StubAudioInfo(File f) {
        this.f = f;
        initTags();
    }

    public StubAudioInfo(String str) {
        this(new File(str));
    }

    @Override
    public void initTags() {
        tags = new HashMap<>();
        tags.put(KEY_ABSOLUTE_FILE_PATH, f.getAbsolutePath());
    }
}
