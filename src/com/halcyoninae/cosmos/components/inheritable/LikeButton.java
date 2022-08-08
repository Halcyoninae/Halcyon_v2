/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.halcyoninae.cosmos.components.inheritable;

import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.tailwind.AudioInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LikeButton extends JButton implements ActionListener {
  private final ImageIcon like;
    private final ImageIcon noLike;

  public LikeButton(ImageIcon noLike, ImageIcon like) {
    super(noLike);
    this.like = like;
    this.noLike = noLike;
    addActionListener(this);
    setToolTipText("Like/Unlike");
  }

  public void noLike() {
    setIcon(noLike);
  }

  public void like() {
    setIcon(like);
  }

  /**
   * @param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.getIcon().equals(noLike)) {
      this.setIcon(like);
      Global.ll.set(Global.ifp.getInfo().getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
    } else {
      this.setIcon(noLike);
      Global.ll.unset(Global.ifp.getInfo().getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
    }
  }
}
