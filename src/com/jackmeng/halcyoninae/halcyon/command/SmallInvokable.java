/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.halcyoninae.halcyon.command;

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
      String temp = null;
      while ((temp = stdOut.readLine()) != null) {
        b.append(temp).append("<br>");
      }

      b.append(CommandPrompt.wrap("<strong><u>Root_Execution_Err Returns (stdErr):</u></strong><br>", Color.BLACK,
          Color.ORANGE));
      String temp2 = null;
      while ((temp2 = stdErr.readLine()) != null) {
        b.append(temp2).append("<br>");
      }
      return b.toString();
    } catch (IOException e) {
      return CommandPrompt.wrap(e.getMessage(), null, Color.RED);
    }
  }
}
