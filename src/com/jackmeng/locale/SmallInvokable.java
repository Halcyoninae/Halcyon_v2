/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.locale;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Jack Meng
 * @since 3.4.1
 */
public class SmallInvokable {

  /**
   * @param str
   * @return String
   */
  @Invokable()
  public String echo(String[] str) {
    StringBuilder sb = new StringBuilder();
    for (String strx : str) {
      sb.append(strx).append(" ");
    }
    return sb.toString();
  }


  /**
   * @param str
   * @return String
   */
  @Invokable(aliases = "root_exec")
  public String rexec(String[] str) {
    try {
      Process t = Runtime.getRuntime().exec(str);

      BufferedReader stdOut = new BufferedReader(new InputStreamReader(t.getInputStream()));

      BufferedReader stdErr = new BufferedReader(new InputStreamReader(t.getErrorStream()));

      StringBuilder b = new StringBuilder(CommandPrompt
          .wrap("<strong><u>Root_Execution_In Returns (stdOut):</u></strong><br>", Color.BLACK, Color.GREEN));
      String temp;
      while ((temp = stdOut.readLine()) != null) {
        b.append(temp).append("<br>");
      }

      b.append(CommandPrompt.wrap("<strong><u>Root_Execution_Err Returns (stdErr):</u></strong><br>", Color.BLACK,
          Color.ORANGE));
      String temp2;
      while ((temp2 = stdErr.readLine()) != null) {
        b.append(temp2).append("<br>");
      }
      return b.toString();
    } catch (IOException e) {
      return CommandPrompt.wrap(e.getMessage(), null, Color.RED);
    }
  }
}
