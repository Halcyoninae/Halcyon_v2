package com.jackmeng.app.utils;

import java.io.File;

import com.jackmeng.debug.Debugger;

/**
 * A simple class that inits and holds
 * a folder information.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class FolderInfo {
  private String absPath = ".";

  public FolderInfo(String absolutePath) {
    this.absPath = absolutePath;
  }

  public String getAbsolutePath() {
    return absPath;
  }

  public String[] getFilesAsStr() {
    File[] f = new File(absPath).listFiles();
    String[] s = new String[f.length];
    for (int i = 0; i < f.length; i++) {
      s[i] = f[i].getName();
    }
    return s;
  }

  public File[] getFiles() {
    return new File(absPath).listFiles();
  }

  public String getName() {
    return new File(absPath).getName();
  }

  public String[] getFilesAsStr(String... rules) {
    File[] f = new File(absPath).listFiles();
    String[] s = new String[f.length];
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile()) {
        String curr = f[i].getAbsolutePath();
        rulesLoop: for (String r : rules) {
          if (curr.endsWith(r)) {
            s[i] = curr;
            break rulesLoop;
          }
        }
      }
    }
    return s;
  }

  public File[] getFiles(String... rules) {
    File[] f = new File(absPath).listFiles();
    File[] s = new File[f.length];
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile()) {
        String curr = f[i].getAbsolutePath();
        rulesLoop: for (String r : rules) {
          if (curr.endsWith(r)) {
            s[i] = f[i];
            break rulesLoop;
          }
        }
      }
    }
    return s;
  }

  public String toString() {
    return absPath + "[" + getFilesAsStr() + "]";
  }
}
