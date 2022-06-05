package com.jackmeng.app;

import javax.swing.ImageIcon;

/**
 * A global scope project manager that contains
 * constants to most used objects and values throughout the
 * program.
 */
public interface Manager {
  public static final int BUTTON_STD_ICON_WIDTH_N_HEIGHT = 16;

  /// BIGCONTAINER Size Config START ///
  public static final int MIN_WIDTH = 480;
  public static final int MIN_HEIGHT = 540;

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
  public static final String SPOTIFY_LOGO_LIGHT = RSC_FOLDER_NAME + "/external/spotify_green.png";
  public static final String YOUTUBE_LOGO_RED = RSC_FOLDER_NAME + "/external/youtube_red.png";
  public static final String SOUNDCLOUD_LOGO_ORANGE = RSC_FOLDER_NAME + "/external/soundcloud_orange.png";

  public static final String INFOVIEW_DISK_NO_FILE_LOADED_ICON = RSC_FOLDER_NAME + "/infoview/disk.png";
  public static final String BUTTONCTRL_PLAY_PAUSE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/play_button.png";
  public static final String BUTTONCTRL_FWD_ICON = RSC_FOLDER_NAME + "/buttoncontrol/forward_button.png";
  public static final String BUTTONCTRL_BWD_ICON = RSC_FOLDER_NAME + "/buttoncontrol/backward_button.png";
  public static final String BUTTONCTRL_LOOP_ICON = RSC_FOLDER_NAME + "/buttoncontrol/loop_button.png";
  public static final String BUTTONCTRL_SHUFFLE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/shuffle_button.png";
  public static final String BUTTONCTRL_MUTED_ICON = RSC_FOLDER_NAME + "/buttoncontrol/mute_button.png";
  public static final String BUTTONCTRL_NOMUTED_ICON = RSC_FOLDER_NAME + "/buttoncontrol/nomute_button.png";
  public static final String BUTTONCTRL_LIKE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/like_button.png";
  public static final String BUTTONCTRL_NOLIKE_ICON = RSC_FOLDER_NAME + "/buttoncontrol/nolike_button.png";

  public static final ImageIcon INFOVIEW_DISK_NO_FILE_LOADED_ICON_ICON = Global.rd.getFromAsImageIcon(INFOVIEW_DISK_NO_FILE_LOADED_ICON);

  public static final String PROGRAM_ICON_LOGO = RSC_FOLDER_NAME + "/app/logo.png";
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

  public static final int INFOVIEW_INFODISPLAY_MAX_CHARS = 25;

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
  public static final String ATTRIBUTES_TAB_ICON = RSC_FOLDER_NAME + "/tabsview/attributes_tab.png";
  public static final String PLAYLIST_TAB_ICON = RSC_FOLDER_NAME + "/tabsview/playlist_tab.png";

  public static final String FILEVIEW_DEFAULT_TAB_NAME = "Playlist";
  public static final String ATTRIBUTES_DEFAULT_TAB_NAME = "Attributes";

  public static final String FILEVIEW_DEFAULT_TAB_TOOLTIP = "View your selected playlist(s) here.";
  public static final String ATTRIBUTES_DEFAULT_TAB_TOOLTIP = "View important properties regarding the program.";

  public static final int TAB_VIEW_MIN_TEXT_STRIP_LENGTH = 10;
  /// TABBED View Config End

  /// BBLOC BUTTONS Config START
  public static final String ADDFOLDER_BUTTON_TEXT = "+";
  public static final String ADDFOLDER_BUTTON_TOOLTIP = "Add a new folder to the playlist.";

  public static final String PROJECTPAGE_BUTTON_TOOLTIP = "Visit this project's GitHub page.";
  public static final String SPOTIFYPAGE_BUTTON_TOOLTIP = "Visit Spotify Webplayer!";
  public static final String YOUTUBEPAGE_BUTTON_TOOLTIP = "Visit YouTube!";
  public static final String SOUNDCLOUD_BUTTON_TOOLTIP = "Visit SoundCloud!";
  /// BBLOC BUTTONS Config END
}
