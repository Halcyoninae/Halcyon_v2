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

package com.jackmeng.halcyoninae.halcyon.connections.properties;

import com.jackmeng.halcyoninae.halcyon.connections.properties.validators.*;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * A constant defined class that holds
 * values for any external resources, such as
 * the properties file for the program config.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource
 * @since 3.0
 */
public class ProgramResourceManager {

    public static final String KEY_USER_DEFAULT_FOLDER = "user.default.folder";
    public static final String KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER = "audio.info.media_title_as_header";
    public static final String KEY_INFOVIEW_BACKDROP_USE_GREYSCALE = "audio.info.backdrop_use_grayscale";
    public static final String KEY_INFOVIEW_BACKDROP_USE_GRADIENT = "audio.info.backdrop_use_gradient";
    public static final String KEY_PROGRAM_FORCE_OPTIMIZATION = "user.force_optimization";
    public static final String KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE = "audio.info.backdrop_gradient_style";
    public static final String KEY_PROGRAM_HIDPI_VALUE = "user.hidpi_value";
    public static final String KEY_USER_DSIABLE_CLI = "user.disable_cli";
    public static final String KEY_USER_USE_DISCORD_RPC = "user.use_discord_rpc";
    public static final String KEY_USER_CHAR_SET_WRITE_TABLE = "user.charset_write_table";
    public static final String KEY_AUDIO_DEFAULT_BUFFER_SIZE = "audio.buffer_size";
    public static final String KEY_MINI_PLAYER_DEFAULT_BG_ALPHA = "mini.player.default_bg_alpha";
    public static final String KEY_TIME_CONTROL_FAST_WARD_MS_INCREMENT = "time.control.forward_ms_increment";
    public static final String KEY_TIME_CONTROL_BACK_WARD_MS_INCREMENT = "time.control.back_ms_increment";
    public static final String KEY_USER_PROGRAM_COLOR_THEME = "user.program_color_theme";
    public static final String KEY_INFOVIEW_BACKDROP_BLUR_FACTOR = "audio.info.backdrop_blur_factor";

    /**
     * if false, a loop will disable a shuffle if both are activated.
     * if true, a loop will be disabled if a shuffle is activated (AKA the shuffle
     * is the master).
     */
    public static final String KEY_AUDIO_LOOP_SLAVE = "mini.player.loop_slave";
    public static final Property[] propertiesList = new Property[] {
            new Property(KEY_USER_DEFAULT_FOLDER, ".", new DirectoryValidator()),
            new Property(KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER,
                    "true", new BooleanValidator()),
            new Property(KEY_INFOVIEW_BACKDROP_USE_GREYSCALE,
                    "true", new BooleanValidator()),
            new Property(KEY_INFOVIEW_BACKDROP_USE_GRADIENT, "true",
                    new BooleanValidator()),
            new Property(KEY_PROGRAM_FORCE_OPTIMIZATION, "false", new BooleanValidator()),
            new Property(KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE,
                    "focused", new StrictValidator("top", "left", "right",
                            "focused")),
            new Property(KEY_PROGRAM_HIDPI_VALUE, "1.0", new NumericRangeValidator(0.9d, 1.1d, 0.1d)),
            new Property(KEY_USER_DSIABLE_CLI, "true", new BooleanValidator()),
            new Property(KEY_USER_USE_DISCORD_RPC, "true", new BooleanValidator()),
            new Property(KEY_USER_CHAR_SET_WRITE_TABLE, "utf8", new StrictValidator("utf8", "utf16le",
                    "utf16be")),
            new Property(KEY_AUDIO_DEFAULT_BUFFER_SIZE, "auto", new DefaultValidator()),
            new Property(KEY_MINI_PLAYER_DEFAULT_BG_ALPHA, "0.6",
                    new NumericRangeValidator(0.0d, 1.0d, 0.1d)),
            new Property(KEY_TIME_CONTROL_FAST_WARD_MS_INCREMENT, "5000",
                    new NumericRangeValidator(1000.0d, 20000.0d, 1.0d)),
            new Property(KEY_TIME_CONTROL_BACK_WARD_MS_INCREMENT, "5000", new NumericRangeValidator(
                    1000.0d, 20000.0d, 1.0d)),
            new Property(KEY_USER_PROGRAM_COLOR_THEME, "dark_green",
                    new StrictValidator("dark_green", "dark_orange", "light_orange",
                            "light_green")),
            new Property(KEY_INFOVIEW_BACKDROP_BLUR_FACTOR, "30", new NumericRangeValidator(0, 40, 1)),
    };
    public static final String FILE_SLASH = "/";
    public static final String PROGRAM_RESOURCE_FOLDER = "halcyon";
    public static final String PROGRAM_RESOURCE_FILE_PROPERTIES = "conf.halcyon";
    public static final String[] RESOURCE_SUBFOLDERS = { "log", "bin", "user" };
    public static final String DEFAULT_ARTWORK_FILE_NAME = "artwork_cache.png";

    private ProgramResourceManager() {
    }

    /**
     * @return The Map of default properties
     */
    public static Map<String, String> getProgramDefaultProperties() {
        Map<String, String> properties = new WeakHashMap<>();
        for (Property p : propertiesList)
            properties.put(p.propertyName, p.defaultProperty);
        return properties;
    }

    /**
     * @return The map of the allowed properties
     */
    public static Map<String, PropertyValidator> getAllowedProperties() {
        Map<String, PropertyValidator> properties = new WeakHashMap<>();
        for (Property p : propertiesList)
            properties.put(p.propertyName, p.pr);
        return properties;
    }

    /**
     * Writes a bufferedimage to the resource folder.
     *
     * @param img An image to write; a BufferedImage instance
     * @return The string representing the location of the image (ABSOLUTE PATH)
     */
    public static String writeBufferedImageToBin(BufferedImage img) {
        return ExternalResource.writeBufferedImageCacheFile(
                img,
                RESOURCE_SUBFOLDERS[1],
                DEFAULT_ARTWORK_FILE_NAME);
    }
}
