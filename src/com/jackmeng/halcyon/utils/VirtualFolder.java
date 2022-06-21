package com.jackmeng.halcyon.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that represents a virtual folder,
 * this virtual folder does not exist on the user's
 * actual hard drive and is used to represent a virtual
 * folder that may be used to hold files from different
 * stuffs.
 *
 * The entirety of the methods from FolderInfo are overriden
 * in order to completely encompass a "virtual" folder
 *
 * @author Jack Meng
 * @since 3.1
 */
public class VirtualFolder extends FolderInfo {
  private List<File> list;

  /**
   * A VirtualFolder takes a varargs of file objects
   * in order to represent the folder with files.
   *
   * The absolute path of the folder is replaced
   * by a generic name.
   *
   * @param name  The name of the virtual folder.
   * @param files The files in the virtual folder.
   */
  public VirtualFolder(String name, File... files) {
    super(name);
    this.list = new ArrayList<>(Arrays.asList(files));
  }

  @Override
  public String[] getFilesAsStr() {
    return list.toArray(new String[list.size()]);
  }

  @Override
  public File[] getFiles() {
    return list.toArray(new File[list.size()]);
  }

  @Override
  public String[] getFilesAsStr(String... rules) {
    List<String> buff = new ArrayList<>();
    for (File f : list) {
      for (String rule : rules) {
        if (f.getAbsolutePath().endsWith(rule)) {
          buff.add(f.getName());
        }
      }
    }
    return buff.toArray(new String[buff.size()]);
  }

  @Override
  public File[] getFiles(String... rules) {
    List<File> buff = new ArrayList<>();
    for (File f : list) {
      for (String rule : rules) {
        if (f.getAbsolutePath().endsWith(rule)) {
          buff.add(f);
        }
      }
    }
    return buff.toArray(new File[buff.size()]);
  }

  public boolean addFile(File f) {
    return list.add(f);
  }

  public List<File> getAsListFiles() {
    return list;
  }

  public boolean removeFile(File f) {
    return list.remove(f);
  }

  @Override
  public String toString() {
    return "[ VIRTUAL FOLDER @ " + getName() + "-" + list.toString() + " ]";
  }
}
