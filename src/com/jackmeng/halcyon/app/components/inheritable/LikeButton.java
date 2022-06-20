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

package com.jackmeng.halcyon.app.components.inheritable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LikeButton extends JButton implements ActionListener {
  private ImageIcon like, noLike;
  public LikeButton(ImageIcon noLike, ImageIcon like) {
    super(noLike);
    this.like = like;
    this.noLike = noLike;
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.getIcon() == noLike) {
      this.setIcon(like);
    } else {
      this.setIcon(noLike);
    }
  }
}
