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

package com.jackmeng.halcyoninae.halcyon.constant;

/**
 * A global scope project manager that contains
 * constants to most used objects and values throughout the
 * program.
 *
 * @author Jack Meng
 * @since 3.0
 */
public interface Manager {
    int BUTTON_STD_ICON_WIDTH_N_HEIGHT         = 16;

    /// BIGCONTAINER Size Config START ///
    int MIN_WIDTH                              = 460;
    int MIN_HEIGHT                             = 500;

    int MAX_WIDTH                              = MIN_HEIGHT + 40;
    int MAX_HEIGHT                             = MIN_HEIGHT + 40;
    /// BIGCONTAINER Size Config END ///

    /// GENERAL RESOURCE LOCATION START
    String RSC_FOLDER_NAME                     = "resources";
    /// GENERAL RESOURCE LOCATION END

    String[] ALLOWED_FORMATS                   = new String[]{"wav", "mp3", "aiff", "aif", "aifc", "ogg", "oga"};

    /// BEGIN RESOURCE LOCATION FOR ICONS

    String GITHUB_LOGO_LIGHT                   = RSC_FOLDER_NAME + "/external/github_light.png";
    String BBLOC_MINIMIZED_PLAYER              = RSC_FOLDER_NAME + "/bbloc/minimize.png";
    String BBLOC_REFRESH_FILEVIEW_ICON         = RSC_FOLDER_NAME + "/bbloc/refresh_icon.png";
    String BBLOC_REFRESH_FILEVIEW_ICON_PRESSED = RSC_FOLDER_NAME + "/bbloc/refresh_icon_pressed.png";
    String PROGRAM_ICON_LOGO                   = RSC_FOLDER_NAME + "/app/logo2.png";
    String PROGRAM_GREEN_LOGO                  = RSC_FOLDER_NAME + "/app/logo_green_ping.png";
    String PROGRAM_BLUE_LOGO                   = RSC_FOLDER_NAME + "/app/logo_blue_ping.png";
    String PROGRAM_RED_LOGO                    = RSC_FOLDER_NAME + "/app/logo_red_ping.png";
    String PROGRAM_ORANGE_LOGO                 = RSC_FOLDER_NAME + "/app/logo_orange_ping.png";
    String PROGRAM_PINK_LOGO                   = RSC_FOLDER_NAME + "/app/logo_pink_ping.png";
    /// END RESOURCE LOCATION FOR ICONS

    /// TABBED View Config Start
    String PLAYLIST_TAB_ICON                   = RSC_FOLDER_NAME + "/tabsview/playlist_tab.png";
    String SLIDERS_TAB_ICON                    = RSC_FOLDER_NAME + "/tabsview/slider_tab.png";

    String FILEVIEW_DEFAULT_TAB_NAME           = "Playlist";
    String SETTINGS_DEFAULT_TAB_NAME           = "Settings";
    String SLIDERS_DEFAULT_TAB_NAME            = "Controls";

    String FILEVIEW_DEFAULT_TAB_TOOLTIP        = "View your selected playlist(s) here.";

    int TAB_VIEW_MIN_TEXT_STRIP_LENGTH         = 10;
    /// TABBED View Config End

    /// BBLOC BUTTONS Config START
    String ADDFOLDER_BUTTON_TEXT               = "+";
    String ADDFOLDER_BUTTON_TOOLTIP            = "Add a new folder to the playlist.";

    String ADDFOLDER_BUTTON_DEFAULT_ICON       = RSC_FOLDER_NAME + "/bbloc/add_folder.png";
    String ADDFOLDER_BUTTON_PRESSED_ICON       = RSC_FOLDER_NAME + "/bbloc/add_folder_pressed.png";

    String SETTINGS_BUTTON_DEFAULT_ICON        = RSC_FOLDER_NAME + "/bbloc/settings_normal.png";
    String SETTINGS_BUTTON_PRESSED_ICON        = RSC_FOLDER_NAME + "/bbloc/settings_pressed.png";
    String SLIDERS_BUTTON_DEFAULT_ICON         = RSC_FOLDER_NAME + "/bbloc/sliders.png";
    String SETTINGS_BUTTON_TOOLTIP             = "Open the settings dialog.";

    String PROJECTPAGE_BUTTON_TOOLTIP          = "Visit this project's GitHub page.";

    String REFRESH_BUTTON_TOOLTIP              = "Refresh the playlist.";
    /// BBLOC BUTTONS Config END

    /// SETTINGS DIALOG Config START
    int SETTINGS_MIN_WIDTH                     = 500;
    int SETTINGS_MIN_HEIGHT                    = 400;
    /// SETTINGS DIALOG Config END
}
