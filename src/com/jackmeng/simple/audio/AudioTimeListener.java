/*
 * MIT License
 *
 * Copyright (c) 2017 Ralph Niemitz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.jackmeng.simple.audio;

/**
 * This Listener interface is fired
 * in order to update any listeners with any information
 * related to the time of the audio stream.
 *
 * For example the current frame of the audio or the
 * current time of the audio in the stream.
 *
 * @author Jack Meng
 */
@FunctionalInterface
public interface AudioTimeListener {
  /**
   * This method represents the method that any
   * added listeners with the implementation will fire.
   *
   * @param event The event tied in with the proper event being fired
   *
   * @see  com.jackmeng.simple.audio.AudioTimeEvent
   */
  public void timeUpdate(AudioTimeEvent event);
}
