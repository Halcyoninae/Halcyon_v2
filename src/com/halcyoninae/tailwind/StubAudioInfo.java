package com.halcyoninae.tailwind;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class StubAudioInfo extends AudioInfo {
  private File f;
  private Map<String, String> tags;

  public StubAudioInfo(File f) {
    this.f = f;
    initTags();
  }

  public StubAudioInfo(String str) {
    this(new File(str));
  }

  @Override
  public void initTags() {
    tags = new HashMap<>();
    tags.put(KEY_ABSOLUTE_FILE_PATH, f.getAbsolutePath());
  }
}
