package com.jackmeng.app.components.toppane.layout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

import com.jackmeng.app.ColorManager;
import com.jackmeng.app.Global;
import com.jackmeng.app.Manager;
import com.jackmeng.app.StringManager;
import com.jackmeng.app.utils.DeImage;
import com.jackmeng.audio.AudioInfo;

import java.awt.image.BufferedImage;

import java.awt.*;

public class InfoViewTP extends JPanel implements TreeSelectionListener {
  private transient AudioInfo info;
  private JLabel infoDisplay, artWork;

  public InfoViewTP() {
    super();
    setPreferredSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.INFOVIEW_MAX_WIDTH, Manager.INFOVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setOpaque(false);
    info = new AudioInfo();
    setLayout(new GridLayout(1, 2));
    infoDisplay = new JLabel(
        "<html><body style=\"font-family='Trebuchet MS', sans-serif;\"><p style=\"text-align: center;\"><span style=\"color: "
            + ColorManager.MAIN_FG_STR + ";font-size: 15px;\"><strong>"
            + info.getTag(AudioInfo.KEY_MEDIA_TITLE)
            + "</strong></span></p><p style=\"text-align: center;\"><span style=\"color: #ffffff;font-size: 12px\">"
            + info.getTag(AudioInfo.KEY_MEDIA_ARTIST)
            + "</span></p><p style=\"text-align: center;\"><span style=\"color: #ffffff;font-size: 9px\">"
            + info.getTag(AudioInfo.KEY_BITRATE) + "," + info.getTag(AudioInfo.KEY_SAMPLE_RATE) + ","
            + info.getTag(AudioInfo.KEY_MEDIA_DURATION) + "</span></p></body></html>");

    BufferedImage bi = DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
    bi = DeImage.resize(bi, 96, 96);
    artWork = new JLabel(new ImageIcon(DeImage.createRoundedBorder(bi, 15, 15, 2, ColorManager.BORDER_THEME)));

    add(artWork);
    add(infoDisplay);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    TreePath path = Global.f.getTree().getSelectionPath();
    if (path != null) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
      if (node.isLeaf() && !node.isRoot()) {
        if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
          String parent = node.getParent().toString();
          System.out.println(parent + " " + Global.f.findKey(parent));
        }
      }
    }
  }
}
