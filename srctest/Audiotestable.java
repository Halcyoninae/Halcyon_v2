

import simple.audio.*;


public class Audiotestable {
  public static void main(String... args) {
    Audio audio;
    try {
      audio = new StreamedAudio("D:/music/future_bass/BB Yukus - Offshore.mp3");
      audio.addAudioListener(event -> {

        if (event.getType() == AudioEvent.Type.REACHED_END) {

          event.getAudio().close();
        }
      });
      audio.open();
      audio.play();
      audio.setVolume(-20);
    } catch (AudioException e) {
      e.printStackTrace();
    }

  }
}
