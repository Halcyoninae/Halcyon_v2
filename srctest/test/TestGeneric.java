package test;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import com.jackmeng.audio.Player;

public class TestGeneric {
  public static void main(String... args) {
    BigClip t = new BigClip();
    AudioFilePlayer afp = new AudioFilePlayer();
    afp.play("D:/zip/projects/mp4j-test/srctest/test/something.mp3");
    try {
      t = new BigClip(afp.sdl);
      t.open();
      t.start();
    } catch (LineUnavailableException | IOException e) {
      e.printStackTrace();
    }
  }
}
