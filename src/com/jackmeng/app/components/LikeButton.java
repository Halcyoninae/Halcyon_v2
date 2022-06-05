package com.jackmeng.app.components;

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
