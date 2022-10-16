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

package com.jackmeng.halcyoninae.halcyon.utils;


import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * A constant defined class that holds
 * values for any external resources, such as
 * the properties file for the program config.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.halcyon.utils.ExternalResource
 * @since 3.0
 */
public class ProgramResourceManager {

        public static final String KEY_USER_DEFAULT_FOLDER = "user.default.folder";
        public static final String KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER = "audio.info.media_title_as_header";
        public static final String KEY_INFOVIEW_BACKDROP_USE_GREYSCALE = "audio.info.backdrop_use_grayscale";
        public static final String KEY_INFOVIEW_BACKDROP_USE_GRADIENT = "audio.info.backdrop_use_gradient";
        public static final String KEY_PROGRAM_FORCE_OPTIMIZATION = "user.force_optimization";
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
        public static final String KEY_INFOVIEW_BACKDROP_BLUR_STYLE = "audio.info.backdrop_blur_style";
        public static final String KEY_USER_PROGRAM_USE_OPENGL = "user.opengl_acc_pipeline";

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
                        new Property(KEY_INFOVIEW_BACKDROP_BLUR_STYLE, "default",
                                        new StrictValidator("wrap", "reflect")),
                        new Property(KEY_USER_PROGRAM_USE_OPENGL, "true", new BooleanValidator()),
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
