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

package com.jackmeng.halcyon.app.components.toppane.layout;

import com.jackmeng.halcyon.Halcyon;
import com.jackmeng.halcyon.audio.AudioInfo;
import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.ColorManager;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.utils.DeImage;
import com.jackmeng.halcyon.utils.TimeParser;
import com.jackmeng.halcyon.utils.DeImage.Directional;
import com.jackmeng.simple.audio.PlaylistEvent;
import com.jackmeng.simple.audio.PlaylistListener;

import de.ralleytn.simple.image.SimpleImage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class sits on the most upper part of the GUI frame.
 * It displays a simple list of information regarding the current
 * stream and nothing else.
 *
 * This panel does not show every information available, but only a specific
 * part.
 *
 * If the user wants to know more about the audio file
 *
 * @see com.jackmeng.halcyon.app.components.dialog.AudioInfoDialog
 *
 * @author Jack Meng
 * @since 3.0
 */
public class InfoViewTP extends JPanel implements ComponentListener, PlaylistListener {

  /**
   * An extended listener for any classes that want
   * to get events regarding any info changes.
   *
   * @author Jack Meng
   * @since 3.0
   */
  public static interface InfoViewUpdateListener {
    void infoView(AudioInfo info);
  }

  private JPanel topPanel, backPanel;
  private transient AudioInfo info;
  private JLabel infoDisplay, artWork;
  private transient ArrayList<InfoViewUpdateListener> listeners;
  private String infoTitle;

  public InfoViewTP() {
    super();
    listeners = new ArrayList<>();
    setPreferredSize(
        new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setMinimumSize(
        new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setOpaque(false);

    topPanel = new JPanel();
    topPanel.setPreferredSize(
        new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    topPanel.setMinimumSize(
        new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    topPanel.setOpaque(false);

    backPanel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float compositeAlpha = 0.5f;

        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE).equals("top")) {
          compositeAlpha = 0.2f;
        } else {
          compositeAlpha = 0.6f;
        }
        g2d.setComposite(
            AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                compositeAlpha));

        BufferedImage original = Global.ifp.getInfo().getArtwork();
        if (original.getWidth() > backPanel.getWidth()
            || original.getHeight() > backPanel.getHeight()) {
          original = new SimpleImage(original).crop(new Rectangle(original.getWidth() / 2, original.getHeight() / 2,
              backPanel.getWidth(), backPanel.getHeight())).toBufferedImage();
        }
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GREYSCALE).equals("true")) {
          original = DeImage.grayScale(original);
        }
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GRADIENT).equals("true")) {
          original = DeImage.createGradientVertical(original, 255, 0);
          if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE).equals("focused")) {
            original = DeImage.createGradient(original, 255, 0, Directional.BOTTOM);
          } else if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE)
              .equals("left")) {
            original = DeImage.createGradient(original, 255, 0, Directional.LEFT);
          } else if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE)
              .equals("right")) {
            original = DeImage.createGradient(original, 255, 0, Directional.RIGHT);
          }
        }
        g2d.drawImage(original, (backPanel.getSize().width - original.getWidth()) / 2,
            (backPanel.getSize().height - original.getHeight()) / 2, this);

      }
    };
    backPanel.setPreferredSize(
        new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    backPanel.setMaximumSize(new Dimension(Manager.INFOVIEW_MAX_WIDTH, Manager.INFOVIEW_MAX_HEIGHT));
    backPanel.setOpaque(false);

    info = new AudioInfo();
    BufferedImage bi = DeImage.imageIconToBI(
        Global.rd.getFromAsImageIcon(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
    if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GREYSCALE).equals("true")) {
      bi = DeImage.grayScale(bi);
    }
    bi = DeImage.resizeNoDistort(
        bi,
        Manager.INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT,
        Manager.INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);

    artWork = new JLabel(new ImageIcon(bi));
    artWork.setBorder(null);
    artWork.setHorizontalAlignment(SwingConstants.CENTER);
    artWork.setVerticalAlignment(SwingConstants.CENTER);

    infoTitle = ResourceFolder.pm.get(
        ProgramResourceManager.KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER)
        .equals("true")
            ? info.getTag(AudioInfo.KEY_MEDIA_TITLE)
            : new File(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)).getName();
    // topPanel.setLayout(
    // new FlowLayout(
    // FlowLayout.CENTER,
    // Manager.INFOVIEW_FLOWLAYOUT_HGAP,
    // getPreferredSize().height / Manager.INFOVIEW_FLOWLAYOUT_VGAP_DIVIDEN));
    topPanel.setLayout(new GridLayout(1, 3, 15,
        topPanel.getPreferredSize().height / 2));
    infoDisplay = new JLabel(infoToString(info, infoTitle));

    infoDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    infoDisplay.setVerticalAlignment(SwingConstants.CENTER);
    infoDisplay.setToolTipText(infoToString(info, info.getTag(AudioInfo.KEY_MEDIA_TITLE)));
    infoDisplay.setHorizontalTextPosition(SwingConstants.LEADING);
    infoDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    infoDisplay.setVerticalAlignment(SwingConstants.CENTER);
    topPanel.add(artWork);
    topPanel.add(infoDisplay);
    addComponentListener(this);
    setLayout(new OverlayLayout(this));
    add(topPanel);
    add(backPanel);
    topPanel.setOpaque(false);
  }

  /**
   * This method is pinged whenever the information regarding
   * the current audio file needs updating.
   *
   * Mostly when the user selects a new track to play.
   *
   * @param f The audio track to play {@link java.io.File}
   */
  public void setAssets(File f) {
    if (f.exists() && f.isFile()) {
      info = new AudioInfo(f);
      infoTitle = ResourceFolder.pm.get(
          ProgramResourceManager.KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER)
          .equals("true")
              ? info.getTag(AudioInfo.KEY_MEDIA_TITLE)
              : new File(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)).getName();
      infoDisplay.setText(infoToString(info, infoTitle));
      infoDisplay.setToolTipText(infoToString(info, info.getTag(AudioInfo.KEY_MEDIA_TITLE)));

      if (infoDisplay.getPreferredSize().width >= (getPreferredSize().width -
          artWork.getPreferredSize().width -
          Manager.INFOVIEW_FLOWLAYOUT_HGAP *
              2)
          && Halcyon.bgt != null) {
        Halcyon.bgt.getFrame()
            .setSize(
                new Dimension(
                    Manager.MAX_WIDTH,
                    Halcyon.bgt.getFrame().getMinimumSize().height));

      }

      if (info.getArtwork() != null) {
        BufferedImage bi = DeImage.resizeNoDistort(info.getArtwork(), 108, 108);
        artWork.setIcon(new ImageIcon(bi));
        backPanel.repaint();
      }
      artWork.setToolTipText(coverIMGToolTip(info));
      infoDisplay.revalidate();
      revalidate();
      dispatchEvents();
    }
  }

  /**
   * This method alerts every linked listener about
   * the new info being updated
   *
   * This method is threaded in order to blocking
   * other functionalities.
   */
  private void dispatchEvents() {
    new Thread(
        () -> {
          for (InfoViewUpdateListener l : listeners) {
            l.infoView(info);
          }
        })
        .start();
  }

  /**
   * Adds a listener to this GUI component, if this listener
   * from that class wants information regarding any updates.
   *
   * @param l A listener that can be called
   */
  public void addInfoViewUpdateListener(InfoViewUpdateListener l) {
    listeners.add(l);
  }

  /**
   * This internal method converts the given audio info into
   * the string information. This string text displays the following
   * information;
   * <ul>
   * <li>Title of the track</li>
   * <li>Artist of the track</li>
   * <li>Bitrate, SampleRate, and Duration</li>
   * </ul>
   *
   * @param info The track to generate the information off of
   * @return An HTML string that can be used by html supporting GUI Components to
   *         display the information.
   */
  private String infoToString(AudioInfo info, String text) {
    return ("<html><body style=\"font-family='Trebuchet MS';\"><p style=\"text-align: left;\"><span style=\"color: "
        +
        ColorManager.MAIN_FG_STR +
        ";font-size: 12px;\"><strong>" +
        text
        +
        "</strong></span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 10px\">" +
        info.getTag(AudioInfo.KEY_MEDIA_ARTIST) +
        "</span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 7.5px\">" +
        info.getTag(AudioInfo.KEY_BITRATE) +
        "kpbs," +
        info.getTag(AudioInfo.KEY_SAMPLE_RATE) +
        "kHz," +
        TimeParser.fromSeconds(
            Integer.parseInt(info.getTag(AudioInfo.KEY_MEDIA_DURATION)))
        +
        "</span></p></body></html>");
  }

  /**
   * This internal method handles converting the given track's
   * artwork into the tooltip information.
   *
   * This tooltip information attached to the artwork label
   * will display the following information:
   * <ul>
   * <li>Artwork (RAW)</li>
   * <li>WIDTH x HEIGHT</li>
   * </ul>
   *
   * Note: this method caches the result into this program's
   * default external folder.
   *
   * @param info The audio track to base off of
   * @return An HTML string that formats the necessary information for the
   *         tooltip.
   */
  private static String coverIMGToolTip(AudioInfo info) {
    return ("<html><body><img src=\"" +
        info.getArtwork().getWidth() +
        "x" +
        info.getArtwork().getHeight() +
        "</p></body></html>");
  }

  public AudioInfo getInfo() {
    return info;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    backPanel.repaint();
  }

  @Override
  public void componentMoved(ComponentEvent e) {
    // IGNORED
  }

  @Override
  public void componentShown(ComponentEvent e) {
    // IGNORED
  }

  @Override
  public void componentHidden(ComponentEvent e) {
    // IGNORED
  }

  @Override
  public void update(PlaylistEvent event) {
    setAssets(new File(event.getAudioInfo().getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)));
  }
}
