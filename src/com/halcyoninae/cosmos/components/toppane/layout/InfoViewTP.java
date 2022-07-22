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

package com.halcyoninae.cosmos.components.toppane.layout;

import de.ralleytn.simple.image.SimpleImage;

import javax.swing.*;

import com.halcyoninae.halcyon.Halcyon;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.connections.resource.ResourceDistributor;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.DeImage;
import com.halcyoninae.halcyon.utils.TimeParser;
import com.halcyoninae.tailwind.AudioInfo;
import com.halcyoninae.tailwind.TailwindEvent;
import com.halcyoninae.tailwind.TailwindListener;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
 * @see com.halcyoninae.cosmos.components.dialog.AudioInfoDialog
 *
 * @author Jack Meng
 * @since 3.0
 */
public class InfoViewTP extends JPanel implements ComponentListener {

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
  private boolean artWorkIsDefault = true;

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
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

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
          original = new SimpleImage(original).crop(new Rectangle(original.getWidth() / 3, original.getHeight() / 3,
              backPanel.getWidth(), backPanel.getHeight())).toBufferedImage();
        }
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GREYSCALE).equals("true")) {
          original = DeImage.grayScale(original);
        }
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GRADIENT).equals("true")) {
          original = DeImage.createGradientVertical(original, 255, 0);
          switch (com.halcyoninae.halcyon.connections.properties.ResourceFolder.pm
              .get(com.halcyoninae.halcyon.constant.ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE)) {
            case "focused":
              original = com.halcyoninae.halcyon.utils.DeImage.createGradient(original, 255, 0,
                  com.halcyoninae.halcyon.utils.DeImage.Directional.BOTTOM);
              break;
            case "left":
              original = com.halcyoninae.halcyon.utils.DeImage.createGradient(original, 255, 0,
                  com.halcyoninae.halcyon.utils.DeImage.Directional.LEFT);
              break;
            case "right":
              original = com.halcyoninae.halcyon.utils.DeImage.createGradient(original, 255, 0,
                  com.halcyoninae.halcyon.utils.DeImage.Directional.RIGHT);
              break;
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
    bi = DeImage.createRoundedBorder(bi, 20, ColorManager.BORDER_THEME);
    artWork = new JLabel(new ImageIcon(bi));
    artWork.setBorder(null);
    artWork.setHorizontalAlignment(SwingConstants.CENTER);
    artWork.setVerticalAlignment(SwingConstants.CENTER);

    infoTitle = ResourceFolder.pm.get(
        ProgramResourceManager.KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER)
        .equals("true")
            ? info.getTag(AudioInfo.KEY_MEDIA_TITLE)
            : new File(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)).getName();
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

      if (info.hasArtwork()) {
        Debugger.warn("Artwork found for drawing!");
        BufferedImage bi = DeImage.resizeNoDistort(info.getArtwork(), 108, 108);
        bi = DeImage.createRoundedBorder(bi, 20, ColorManager.BORDER_THEME);
        artWork.setIcon(new ImageIcon(bi));
        SwingUtilities.invokeLater(backPanel::repaint);
        artWorkIsDefault = false;
      } else {
        Debugger.warn("Artwork reset!");
        BufferedImage bi = DeImage
            .resizeNoDistort(DeImage.imageIconToBI(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON_ICON), 108, 108);
        bi = DeImage.createRoundedBorder(bi, 20, ColorManager.BORDER_THEME);
        artWork.setIcon(new ImageIcon(bi));
        SwingUtilities.invokeLater(backPanel::repaint);
        artWorkIsDefault = true;
      }
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
        "</span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 8px\">" +
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
   * @return AudioInfo
   */
  public AudioInfo getInfo() {
    return info;
  }

  /**
   * @param e
   */
  @Override
  public void componentResized(ComponentEvent e) {
    // FOR FUTURE IMPLEMENTATIONS
  }

  /**
   * @param e
   */
  @Override
  public void componentMoved(ComponentEvent e) {
    // IGNORED
  }

  /**
   * @param e
   */
  @Override
  public void componentShown(ComponentEvent e) {
    // IGNORED
  }

  /**
   * @param e
   */
  @Override
  public void componentHidden(ComponentEvent e) {
    // IGNORED
  }
}
