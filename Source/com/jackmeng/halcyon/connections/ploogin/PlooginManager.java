package com.jackmeng.halcyon.connections.ploogin;

import com.jackmeng.halcyon.global.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PlooginManager {
  private static final Set<Pair<Class<?>, Ploogin>> ploogins = new HashSet<>();


  /**
   * @return File[]
   */
  private static File[] getListOfPlugins() {
    ArrayList<File> files = new ArrayList<>();
    for (File f : java.util.Objects.requireNonNull(new java.io.File(com.jackmeng.halcyon.constant.ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + com.jackmeng.halcyon.constant.ProgramResourceManager.FILE_SLASH
            + com.jackmeng.halcyon.constant.ProgramResourceManager.RESOURCE_SUBFOLDERS[2]).listFiles())) {
      if (f.getName().endsWith(".jar")) {
        files.add(f);
      }
    }
    return files.toArray(new java.io.File[0]);
  }


  /**
   * @return e
   */
  public static Set<Pair<Class<?>, Ploogin>> getPloogins() {
    return ploogins;
  }


  /**
   * @param args
   */
  public static void main(String... args) {
    File[] list = getListOfPlugins();
    if (list.length > 0) {
      for (File f : list) {
        try (JarFile currPlugin = new JarFile(f)) {
          Enumeration<JarEntry> e = currPlugin.entries();
          URL[] urls = { new URL("jar:file:" + f.getAbsolutePath() + "!/") };
          URLClassLoader cl = URLClassLoader.newInstance(urls);
          while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
              continue;
            }
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class<?> c = cl.loadClass(className);
            if (Ploogin.class.isAssignableFrom(c)) {
              Constructor<?> constructor = c.getConstructor();
              Ploogin plugin = (Ploogin) constructor.newInstance();
              ploogins.add(new Pair<>(c, plugin));
              plugin.run();
            }
          }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException
            | InstantiationException
            | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
