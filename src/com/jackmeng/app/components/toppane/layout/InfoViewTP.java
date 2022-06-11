package com.jackmeng.app.components.toppane.layout;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jackmeng.app.ColorManager;
import com.jackmeng.app.Global;
import com.jackmeng.app.Halcyon;
import com.jackmeng.app.Manager;
import com.jackmeng.app.StringManager;
import com.jackmeng.app.utils.DeImage;
import com.jackmeng.app.utils.TextParser;
import com.jackmeng.app.utils.TimeParser;
import com.jackmeng.audio.AudioInfo;
import com.jackmeng.debug.Debugger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.awt.*;

public class InfoViewTP extends JPanel implements TreeSelectionListener {
  public static interface InfoViewUpdateListener {
    void infoView(AudioInfo info);
  }

  private transient AudioInfo info;
  private JLabel infoDisplay, artWork;
  private ArrayList<InfoViewUpdateListener> listeners;

  public InfoViewTP() {
    super();
    listeners = new ArrayList<InfoViewUpdateListener>();
    setPreferredSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.INFOVIEW_MAX_WIDTH, Manager.INFOVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setOpaque(false);
    info = new AudioInfo();
    setLayout(new FlowLayout(FlowLayout.CENTER, Manager.INFOVIEW_FLOWLAYOUT_HGAP,
        getPreferredSize().height / Manager.INFOVIEW_FLOWLAYOUT_VGAP_DIVIDEN));
    infoDisplay = new JLabel(infoToString(info, true));
    infoDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    infoDisplay.setVerticalAlignment(SwingConstants.CENTER);
    infoDisplay.setToolTipText(infoToString(info, false));
    BufferedImage bi = DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
    bi = DeImage.resizeNoDistort(bi, Manager.INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT,
        Manager.INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);
    artWork = new JLabel(new ImageIcon(bi));
    artWork.setBorder(null);
    artWork.setHorizontalAlignment(SwingConstants.CENTER);
    artWork.setVerticalAlignment(SwingConstants.CENTER);
    infoDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    infoDisplay.setVerticalAlignment(SwingConstants.CENTER);
    add(artWork);
    add(infoDisplay);
  }

  private void setAssets(File f) {
    if (f.exists() && f.isFile()) {
      info = new AudioInfo(f);
      infoDisplay.setText(infoToString(info, true));
      infoDisplay.setToolTipText(infoToString(info, false));
      if (infoDisplay.getPreferredSize().width >= (getPreferredSize().width - artWork.getPreferredSize().width
          - Manager.INFOVIEW_FLOWLAYOUT_HGAP * 2)) {
        if (Halcyon.bgt != null) {
          Halcyon.bgt.getFrame().setSize(new Dimension(Manager.MAX_WIDTH, Halcyon.bgt.getFrame().getMinimumSize().height));
        }
      }

      if (info.getArtwork() != null) {
        BufferedImage bi = DeImage.resizeNoDistort(info.getArtwork(), 96, 96);
        artWork.setIcon(new ImageIcon(bi));
      } else {
        BufferedImage bi = DeImage
            .imageIconToBI(Global.rd.getFromAsImageIcon(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
        bi = DeImage.resizeNoDistort(bi, Manager.INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT,
            Manager.INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);
        artWork.setIcon(new ImageIcon(bi));
      }
      infoDisplay.revalidate();
      revalidate();
      dispatchEvents();
    }
  }

  private void dispatchEvents() {
    new Thread(() -> {
      for (InfoViewUpdateListener l : listeners) {
        l.infoView(info);
      }
    }).start();

  }

  public void addInfoViewUpdateListener(InfoViewUpdateListener l) {
    listeners.add(l);
  }

  private static String infoToString(AudioInfo info, boolean enforceLength) {
    return "<html><body style=\"font-family='Trebuchet MS', sans-serif;\"><p style=\"text-align: left;\"><span style=\"color: "
        + ColorManager.MAIN_FG_STR + ";font-size: 12px;\"><strong>"
        + (enforceLength ? TextParser.strip(info.getTag(AudioInfo.KEY_MEDIA_TITLE),
            Manager.INFOVIEW_INFODISPLAY_MAX_CHARS) : info.getTag(AudioInfo.KEY_MEDIA_TITLE))
        + "</strong></span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 10px\">"
        + info.getTag(AudioInfo.KEY_MEDIA_ARTIST)
        + "</span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 7.5px\">"
        + info.getTag(AudioInfo.KEY_BITRATE) + "kpbs," + info.getTag(AudioInfo.KEY_SAMPLE_RATE) + "kHz,"
        + TimeParser.fromSeconds(Integer.parseInt(info.getTag(AudioInfo.KEY_MEDIA_DURATION)))
        + "</span></p></body></html>";
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    TreePath path = Global.f.getTree().getSelectionPath();
    if (path != null) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
      if (node.isLeaf() && !node.isRoot()) {
        if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
          setAssets(new File(Global.f.findKey(node.getParent().toString()) + "/" + node.toString()));
        }
      }
    }
  }
}
