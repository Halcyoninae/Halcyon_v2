package com.jackmeng.tailwind;

import java.io.File;

public interface Transcoder {
  final int MP3_FORMAT = 1, WAV_FORMAT = 2, OGG_FORMAT = 3;
  void transcode(int inFormat, int outFormat, File inLocale, File outLocale);

}
