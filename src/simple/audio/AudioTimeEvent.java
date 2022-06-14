package simple.audio;

/**
 * Represents the event fired
 * by the listener implemented:
 * {@link  simple.audio.AudioTimeListener}.
 *
 * @author Jack Meng
 */
public class AudioTimeEvent {
  private long timeInStream = 0L;
  private long framePosition = 0L;

  public AudioTimeEvent(long timeInStream, long framePosition) {
    this.timeInStream = timeInStream;
    this.framePosition = framePosition;
  }

  public long getFramePosition() {
    return framePosition;
  }

  public long getTimeInStream() {
    return timeInStream;
  }

  public String toString() {
    return (
      "AudioTimeEvent [timeInStream=" +
      timeInStream +
      ", framePosition=" +
      framePosition +
      "]"
    );
  }
}
