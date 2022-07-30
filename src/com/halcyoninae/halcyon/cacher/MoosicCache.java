package com.halcyoninae.halcyon.cacher;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.halcyoninae.cosmos.components.dialog.ErrorWindow;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;

public class MoosicCache {
  public static String MOOSIC_DEFAULT_LOCALE = ProgramResourceManager.PROGRAM_RESOURCE_FOLDER
      + ProgramResourceManager.FILE_SLASH
      + ProgramResourceManager.RESOURCE_SUBFOLDERS[2] + ProgramResourceManager.FILE_SLASH + "moosic.halcyon";

  public static final String NODE_ROOT = "moosic";
  public static final String NODE_USER_LIKED_TRACKS = "likedTracks";
  public static final String NODE_USER_SAVED_PLAYLISTS = "savedPlaylists";
  public static final String NODE_USER_EXCLUDED_TRACKS = "excludedTracks";

  public Cacher cacher;
  private List<String> excludedFiles, savedPlayLists;
  private Set<String> likedTracks;

  public MoosicCache() {
    init();
  }

  public void init() {
    cacher = new Cacher(new File(MOOSIC_DEFAULT_LOCALE));
    if (!new File(MOOSIC_DEFAULT_LOCALE).exists() || new File(MOOSIC_DEFAULT_LOCALE).length() == 0
        || !new File(MOOSIC_DEFAULT_LOCALE).isFile()) {
      Debugger.warn("Incorrect user cache found! >,< Moosic resetting");
      Map<String, String> content = new HashMap<>();
      content.put(NODE_USER_LIKED_TRACKS, "");
      content.put(NODE_USER_SAVED_PLAYLISTS, "");
      content.put(NODE_USER_EXCLUDED_TRACKS, "");
      try {
        cacher.build(NODE_ROOT, content);
      } catch (TransformerException | ParserConfigurationException e) {
        ResourceFolder.dispatchLog(e);
        e.printStackTrace();
      }
      excludedFiles = new ArrayList<>();
      savedPlayLists = new ArrayList<>();
      likedTracks = new HashSet<>();
    } else {
      Debugger.warn("Loading user cache...!! :D The moosic is on!");
      try {
        excludedFiles = cacher.getContent(NODE_USER_EXCLUDED_TRACKS)[0] != null ? new ArrayList<>(Arrays.asList(
            cacher.getContent(NODE_USER_EXCLUDED_TRACKS)[0].split("\n"))) : new ArrayList<>();
        savedPlayLists = cacher.getContent(NODE_USER_SAVED_PLAYLISTS)[0] != null ? new ArrayList<>(Arrays.asList(
            cacher.getContent(NODE_USER_SAVED_PLAYLISTS)[0].split("\n")))
            : new ArrayList<>();
        likedTracks = cacher.getContent(NODE_USER_LIKED_TRACKS)[0] != null ? new HashSet<>(Arrays.asList(
            cacher.getContent(NODE_USER_LIKED_TRACKS)[0].split("\n"))) : new HashSet<>();
      } catch (Exception e) {
        ResourceFolder.dispatchLog(e);
        e.printStackTrace();
        new ErrorWindow(e.getMessage()).run();
      }
    }
  }


  /**
   * @param path
   * @return boolean
   */
  public boolean isExcluded(String path) {
    return excludedFiles.contains(path);
  }

  public void pingLikedTracks() {
    for (File track : Global.ll.getFolder().getAsListFiles()) {
      if (!likedTracks.contains(track.getAbsolutePath())) {
        synchronized (likedTracks) {
          likedTracks.add(track.getAbsolutePath());
        }
      }
    }
  }

  public void pingSavedPlaylists() {
    for (String s : Global.bp.getStrTabs()) {
      if (!savedPlayLists.contains(s)) {
        synchronized (savedPlayLists) {
          savedPlayLists.add(s);
        }
      }
    }
  }


  /**
   * @param exclude
   */
  public void pingExcludedTracks(String exclude) {
    if (!excludedFiles.contains(exclude)) {
      synchronized (excludedFiles) {
        excludedFiles.add(exclude);
      }
    }
  }


  /**
   * @return List<String>
   */
  public List<String> getExcludedTracks() {
    return excludedFiles;
  }


  /**
   * @return List<String>
   */
  public List<String> getSavedPlaylists() {
    return savedPlayLists;
  }


  /**
   * @return Set<String>
   */
  public Set<String> getLikedTracks() {
    return likedTracks;
  }

  public void forceSave() {
    Map<String, String> content = new HashMap<>();
    StringBuilder sb1 = new StringBuilder();
    excludedFiles.forEach(x -> sb1.append(x).append("\n"));
    content.put(NODE_USER_EXCLUDED_TRACKS, sb1.toString());
    StringBuilder sb2 = new StringBuilder();
    savedPlayLists.forEach(x -> sb2.append(x).append("\n"));
    content.put(NODE_USER_SAVED_PLAYLISTS, sb2.toString());
    StringBuilder sb3 = new StringBuilder();
    likedTracks.forEach(x -> sb3.append(x).append("\n"));
    content.put(NODE_USER_LIKED_TRACKS, sb3.toString());
    try {
      cacher.build(NODE_ROOT, content);
    } catch (TransformerException | ParserConfigurationException e) {
      e.printStackTrace();
    }
  }
}
