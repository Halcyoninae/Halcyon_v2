package com.jackmeng;

/**
 * This interface primarily manages
 * any links and does not seem to manage
 * any data. It is similar to Manager
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.constant.Manager
 */
public interface ProjectManager {
  public static final String PROJECT_GITHUB_PAGE = "https://github.com/Exoad4JVM/mp4j";
  public static final String SPOTIFY_PAGE = "https://open.spotify.com/";
  public static final String YOUTUBE_PAGE = "https://www.youtube.com/";
  public static final String SOUNDCLOUD_PAGE = "https://soundcloud.com/";

  public static final String FILE_SLASH = System.getProperty("file.separator");
}
