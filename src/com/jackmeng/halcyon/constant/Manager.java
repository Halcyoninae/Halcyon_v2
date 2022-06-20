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

package com.jackmeng.halcyon.constant;

import javax.swing.ImageIcon;

/**
 * A global scope project manager that contains
 * constants to most used objects and values throughout the
 * program.
 *
 * @author Jack Meng
 * @since 3.0
 */
public interface Manager {
  public static final int BUTTON_STD_ICON_WIDTH_N_HEIGHT = 16;

  /// BIGCONTAINER Size Config START ///
  public static final int MIN_WIDTH = 450;
  public static final int MIN_HEIGHT = 500;

  public static final int MAX_WIDTH = MIN_HEIGHT + 40;
  public static final int MAX_HEIGHT = MIN_HEIGHT + 40;
  /// BIGCONTAINER Size Config END ///

  /// FILEVIEW Size Config START ///
  public static final int FILEVIEW_MIN_WIDTH = MIN_WIDTH - 70;
  public static final int FILEVIEW_MIN_HEIGHT = MIN_HEIGHT - 50 / 2;

  public static final int FILEVIEW_MAX_WIDTH = MAX_WIDTH - 50;
  public static final int FILEVIEW_MAX_HEIGHT = MAX_HEIGHT + 50 / 2 - 40;
  /// FILEVIEW Size Config END ///

  /// B Bloc Size Config START ///
  public static final int B_MIN_WIDTH = MIN_WIDTH - FILEVIEW_MIN_WIDTH - 40;
  public static final int B_MIN_HEIGHT = MIN_HEIGHT / 2;

  public static final int B_MAX_WIDTH = B_MIN_WIDTH - FILEVIEW_MAX_WIDTH;
  public static final int B_MAX_HEIGHT = MAX_HEIGHT / 2;
  /// B Bloc Size Config END ///

  /// GENERAL RESOURCE LOCATION START
  public static final String RSC_FOLDER_NAME = "resource";
  /// GENERAL RESOURCE LOCATION END

  /// GENERAL PROGRAM CONFIGURATION START
  public static final String[] ALLOWED_FORMATS = new String[] { "wav", "mp3" };
  public static final int TAB_VIEW_MAX_CHAR_LENGTH = 10;
  /// GENERAL PROGRAM CONFIGURATION END

  /// BEGIN RESOURCE LOCATION FOR ICONS
  public static final String FILEVIEW_ICON_FOLDER_OPEN = RSC_FOLDER_NAME + "/fileview/folder_icon.png";
  public static final String FILEVIEW_ICON_FOLDER_CLOSED = RSC_FOLDER_NAME + "/fileview/folder_icon.png";
  public static final String FILEVIEW_ICON_FILE = RSC_FOLDER_NAME + "/fileview/leaf.png";
  public static final String FILEVIEW_DEFAULT_FOLDER_ICON = RSC_FOLDER_NAME + "/fileview/folder_icon.png";

  public static final String GITHUB_LOGO_LIGHT = RSC_FOLDER_NAME + "/external/github_light.png";

  public static final String INFOVIEW_DISK_NO_FILE_LOADED_ICON = RSC_FOLDER_NAME + "/infoview/disk.jpg";
  public static final String BUTTONCTRL_PLAY_PAUSE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/play_button.png";
  public static final String BUTTONCTRL_FWD_ICON = RSC_FOLDER_NAME + "/buttoncontrol/forward_button.png";
  public static final String BUTTONCTRL_BWD_ICON = RSC_FOLDER_NAME + "/buttoncontrol/backward_button.png";
  public static final String BUTTONCTRL_LOOP_ICON = RSC_FOLDER_NAME + "/buttoncontrol/loop_button.png";
  public static final String BUTTONCTRL_SHUFFLE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/shuffle_button.png";
  public static final String BUTTONCTRL_MUTED_ICON = RSC_FOLDER_NAME + "/buttoncontrol/mute_button.png";
  public static final String BUTTONCTRL_NOMUTED_ICON = RSC_FOLDER_NAME + "/buttoncontrol/nomute_button.png";
  public static final String BUTTONCTRL_LIKE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/like_button.png";
  public static final String BUTTONCTRL_NOLIKE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/nolike_button.png";
  public static final String BUTTONCTRL_RESTART_ICON = RSC_FOLDER_NAME + "/buttoncontrol/restart_button.png";
  public static final String BUTTONCONTROL_SHUFFLE_ICON_PRESSED = RSC_FOLDER_NAME
      + "/buttoncontrol/shuffle_button_pressed.png";
  public static final String BUTTONCONTROL_LOOP_ICON_PRESSED = RSC_FOLDER_NAME
      + "/buttoncontrol/loop_button_pressed.png";

  public static final String BBLOC_REFRESH_FILEVIEW_ICON = RSC_FOLDER_NAME + "/bbloc/refresh_icon.png";
    public static final String BBLOC_REFRESH_FILEVIEW_ICON_PRESSED = RSC_FOLDER_NAME + "/bbloc/refresh_icon_pressed.png";

  public static final ImageIcon INFOVIEW_DISK_NO_FILE_LOADED_ICON_ICON = Global.rd.getFromAsImageIcon(
      INFOVIEW_DISK_NO_FILE_LOADED_ICON);

  public static final String PROGRAM_ICON_LOGO = RSC_FOLDER_NAME + "/app/logo2.png";
  /// END RESOURCE LOCATION FOR ICONS

  /// TOPPane Wrapper Config START
  public static final int TOPPANE_MIN_WIDTH = MIN_WIDTH - FILEVIEW_MIN_WIDTH;
  public static final int TOPPANE_MIN_HEIGHT = MIN_HEIGHT / 2;

  public static final int TOPPANE_MAX_WIDTH = TOPPANE_MIN_WIDTH - FILEVIEW_MAX_WIDTH;
  public static final int TOPPANE_MAX_HEIGHT = MAX_HEIGHT / 2;
  /// TOPPane Wrapper Config END

  /// ButtonControl Config START
  public static final int BUTTONCONTROL_MIN_WIDTH = MIN_WIDTH;
  public static final int BUTTONCONTROL_MIN_HEIGHT = MIN_HEIGHT / 4;

  public static final int BUTTONCONTROL_MAX_WIDTH = MAX_WIDTH;
  public static final int BUTTONCONTROL_MAX_HEIGHT = MAX_HEIGHT / 4;
  /// ButtonControl Config END

  /// InfoView Config START
  public static final int INFOVIEW_MIN_WIDTH = MIN_WIDTH;
  public static final int INFOVIEW_MIN_HEIGHT = MIN_HEIGHT / 4;

  public static final int INFOVIEW_MAX_WIDTH = MAX_WIDTH;
  public static final int INFOVIEW_MAX_HEIGHT = MAX_HEIGHT / 4;

  public static final int INFOVIEW_INFODISPLAY_MAX_CHARS = 22;

  public static final int INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT = 96;

  public static final int INFOVIEW_FLOWLAYOUT_HGAP = 30;
  public static final int INFOVIEW_FLOWLAYOUT_VGAP_DIVIDEN = 6;
  /// InfoView Config END

  /// DIALOG_ERROR Config START
  public static final int DIALOG_ERROR_MIN_WIDTH = 300;
  public static final int DIALOG_ERROR_MIN_HEIGHT = 150;

  public static final String DIALOG_ERROR_WIN_TITLE = "Exception!";
  /// DIALOG_ERROR Config END

  /// DIALOG_CONFIRM Config START
  public static final int DIALOG_CONFIRM_MIN_WIDTH = 300;
  public static final int DIALOG_CONFIRM_MIN_HEIGHT = 200;

  public static final int DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH = DIALOG_CONFIRM_MIN_WIDTH - 20;
  public static final int DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT = DIALOG_CONFIRM_MIN_HEIGHT / 5;

  public static final String DIALOG_CONFIRM_WIN_TITLE = "Confirmation!";
  /// DIALOG_CONFIRM Config END

  /// TABBED View Config Start
  public static final String PLAYLIST_TAB_ICON = RSC_FOLDER_NAME + "/tabsview/playlist_tab.png";
  public static final String SLIDERS_TAB_ICON = RSC_FOLDER_NAME + "/tabsview/slider_tab.png";

  public static final String FILEVIEW_DEFAULT_TAB_NAME = "Playlist";
  public static final String SETTINGS_DEFAULT_TAB_NAME = "Settings";
  public static final String SLIDERS_DEFAULT_TAB_NAME = "Controls";

  public static final String FILEVIEW_DEFAULT_TAB_TOOLTIP = "View your selected playlist(s) here.";

  public static final int TAB_VIEW_MIN_TEXT_STRIP_LENGTH = 10;
  /// TABBED View Config End

  /// BBLOC BUTTONS Config START
  public static final String ADDFOLDER_BUTTON_TEXT = "+";
  public static final String ADDFOLDER_BUTTON_TOOLTIP = "Add a new folder to the playlist.";

  public static final String ADDFOLDER_BUTTON_DEFAULT_ICON = RSC_FOLDER_NAME + "/bbloc/add_folder.png";
  public static final String ADDFOLDER_BUTTON_PRESSED_ICON = RSC_FOLDER_NAME + "/bbloc/add_folder_pressed.png";

  public static final String SETTINGS_BUTTON_DEFAULT_ICON = RSC_FOLDER_NAME + "/bbloc/settings_normal.png";
  public static final String SETTINGS_BUTTON_PRESSED_ICON = RSC_FOLDER_NAME + "/bbloc/settings_pressed.png";
  public static final String SETTINGS_BUTTON_TOOLTIP = "Open the settings dialog.";

  public static final String PROJECTPAGE_BUTTON_TOOLTIP = "Visit this project's GitHub page.";

  public static final String REFRESH_BUTTON_TOOLTIP = "Refresh the playlist.";
  /// BBLOC BUTTONS Config END

  /// AUDIOINFO Window Config START
  public static final String AUDIOINFO_WIN_TITLE = "Halcyon - Audio Info";
  public static final int AUDIOINFO_MIN_WIDTH = 500;
  public static final int AUDIOINFO_MIN_HEIGHT = 400;

  public static final int AUDIOINFO_DIVIDER_LOCATION = AUDIOINFO_MIN_WIDTH / 2;

  public static final int AUDIOINFO_ARTWORK_PANE_WIDTH = AUDIOINFO_MIN_WIDTH / 2;
  public static final int AUDIOINFO_INFO_PANE_WIDTH = AUDIOINFO_MIN_WIDTH / 2;
  /// AUDIOINFO Window Config END

  /// LEGALNOTICEDIALOG Config START
  public static final int LEGALNOTICEDIALOG_MIN_WIDTH = 500;
  public static final int LEGALNOTICEDIALOG_MIN_HEIGHT = 550;

  public static final String LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL = RSC_FOLDER_NAME + "/bbloc/legals_normal.png";
  public static final String LEGALNOTICEBBLOC_ICON_BUTTON_PRESSED = RSC_FOLDER_NAME + "/bbloc/legals_pressed.png";

  public static final String LEGAL_NOTICE_DOCS = RSC_FOLDER_NAME + "/files/LICENSE.html";
  public static final String LEGAL_NOTICE_PROPERTIES_DOCS = RSC_FOLDER_NAME + "/files/PROPERTIES_FILE.html";

  public static final int LEGALNOTICEDIALOG_SCROLL_PANE_MIN_HEIGHT = LEGALNOTICEDIALOG_MIN_HEIGHT - 100;
  /// LEGALNOTICEDIALOG Config END
}
