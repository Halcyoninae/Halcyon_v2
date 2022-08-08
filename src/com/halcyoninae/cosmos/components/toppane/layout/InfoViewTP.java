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

import com.halcyoninae.cloudspin.CloudSpin;
import com.halcyoninae.cloudspin.CloudSpinFilters;
import com.halcyoninae.halcyon.Halcyon;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.DeImage;
import com.halcyoninae.halcyon.utils.TimeParser;
import com.halcyoninae.tailwind.AudioInfo;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
  public interface InfoViewUpdateListener {
    void infoView(AudioInfo info);
  }

  private final JPanel topPanel;
  private final JPanel backPanel;
  private transient AudioInfo info;
  private final JLabel infoDisplay;
  private final JLabel artWork;
  private final transient ArrayList<InfoViewUpdateListener> listeners;
  private String infoTitle;
  private boolean artWorkIsDefault = true;

  /// InfoView Config START
  public static final String INFOVIEW_DISK_NO_FILE_LOADED_ICON = Manager.RSC_FOLDER_NAME + "/infoview/disk.png";
  final ImageIcon INFOVIEW_DISK_NO_FILE_LOADED_ICON_ICON = Global.rd.getFromAsImageIcon(
      INFOVIEW_DISK_NO_FILE_LOADED_ICON);
  final int INFOVIEW_MIN_WIDTH = Manager.MIN_WIDTH;
  final int INFOVIEW_MIN_HEIGHT = Manager.MIN_HEIGHT / 4;
  final int INFOVIEW_MAX_WIDTH = Manager.MAX_WIDTH;
  final int INFOVIEW_MAX_HEIGHT = Manager.MAX_HEIGHT / 4;
  final int INFOVIEW_INFODISPLAY_MAX_CHARS = 22;
  final int INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT = 108;
  final int INFOVIEW_FLOWLAYOUT_HGAP = 30;
  final int INFOVIEW_FLOWLAYOUT_VGAP_DIVIDEN = 6;
  /// InfoView Config END

  public InfoViewTP() {
    super();
    listeners = new ArrayList<>();
    setPreferredSize(
        new Dimension(INFOVIEW_MIN_WIDTH, INFOVIEW_MIN_HEIGHT));
    setMinimumSize(
        new Dimension(INFOVIEW_MIN_WIDTH, INFOVIEW_MIN_HEIGHT));
    setOpaque(false);

    topPanel = new JPanel();
    topPanel.setPreferredSize(
        new Dimension(INFOVIEW_MIN_WIDTH, INFOVIEW_MIN_HEIGHT));
    topPanel.setMinimumSize(
        new Dimension(INFOVIEW_MIN_WIDTH, INFOVIEW_MIN_HEIGHT));
    topPanel.setOpaque(false);

    backPanel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Halcyon.bgt.getFrame().isVisible() && Halcyon.bgt.getFrame().isShowing()) {
          Graphics2D g2d = (Graphics2D) g;
          float compositeAlpha = 0.5f;
          g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
          g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);

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
          original = CloudSpin.grabCrop(original, backPanel.getVisibleRect());
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
          if (ResourceFolder.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GREYSCALE).equals("true")) {
            original = CloudSpinFilters.filters[CloudSpinFilters.AFF_GREY].filter(original, original);
            g2d.drawImage(original, 0, 0, backPanel.getWidth(), backPanel.getHeight(), null);
          } else {
            g2d.drawImage(original, 0, 0, backPanel.getWidth(), backPanel.getHeight(),
                0, 0, original.getWidth(), original.getHeight(), null);

          }
        }
      }
    };
    backPanel.setPreferredSize(
        getPreferredSize());
    backPanel.setOpaque(false);
    backPanel.setDoubleBuffered(true);

    info = new AudioInfo();
    BufferedImage bi = DeImage.imageIconToBI(
        Global.rd.getFromAsImageIcon(INFOVIEW_DISK_NO_FILE_LOADED_ICON));
    bi = DeImage.resizeNoDistort(
        bi,
        INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT,
        INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);
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
    infoDisplay = new JLabel(infoToString(info, infoTitle, false));

    infoDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    infoDisplay.setVerticalAlignment(SwingConstants.CENTER);
    infoDisplay.setToolTipText(infoToString(info, info.getTag(AudioInfo.KEY_MEDIA_TITLE), false));
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
      boolean beSmart = false;
      try {
        info = new AudioInfo(f, false);
      } catch (InvalidAudioFrameException | CannotReadException | IOException | TagException
          | ReadOnlyFileException e) {
        beSmart = true;
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put(AudioInfo.KEY_ABSOLUTE_FILE_PATH, f.getAbsolutePath());
        defaultMap.put(AudioInfo.KEY_FILE_NAME, f.getName());
        defaultMap.put(AudioInfo.KEY_ALBUM, "Unknown");
        defaultMap.put(AudioInfo.KEY_MEDIA_DURATION, "Unknown");
        defaultMap.put(AudioInfo.KEY_MEDIA_TITLE, "Unknown");
        defaultMap.put(AudioInfo.KEY_BITRATE, "Unknown");
        defaultMap.put(AudioInfo.KEY_SAMPLE_RATE, "Unknown");
        defaultMap.put(AudioInfo.KEY_ALBUM, "Unknown");
        defaultMap.put(AudioInfo.KEY_GENRE, "Unknown");
        defaultMap.put(AudioInfo.KEY_MEDIA_ARTIST, "Unknown");
        defaultMap.put(AudioInfo.KEY_ARTWORK, "Unknown");
        info.forceSet(defaultMap);
      }
      if (!beSmart) {
        infoTitle = ResourceFolder.pm.get(
            ProgramResourceManager.KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER)
            .equals("true")
                ? info.getTag(AudioInfo.KEY_MEDIA_TITLE)
                : new File(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)).getName();
        infoDisplay.setText(infoToString(info, infoTitle, false));
        infoDisplay.setToolTipText(infoToString(info, info.getTag(AudioInfo.KEY_MEDIA_TITLE), false));
      } else {
        infoTitle = f.getName();
        Debugger.warn(">>> " + infoTitle);
        infoDisplay.setText(infoToString(info, infoTitle, true));
        infoDisplay.setToolTipText(infoToString(info, infoTitle, true));
      }
      if (infoDisplay.getPreferredSize().width >= (getPreferredSize().width -
          artWork.getPreferredSize().width -
          INFOVIEW_FLOWLAYOUT_HGAP *
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
        BufferedImage bi = null;
        if (!beSmart)
          bi = DeImage.resizeNoDistort(info.getArtwork(), INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT,
              INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);
        else
          bi = DeImage.resizeNoDistort(
              bi,
              INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT,
              INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);
        bi = DeImage.createRoundedBorder(bi, 20, ColorManager.BORDER_THEME);
        artWork.setIcon(new ImageIcon(bi));
        artWork.repaint(30);
        artWorkIsDefault = false;
      } else {
        Debugger.warn("Artwork reset!");
        BufferedImage bi = DeImage
            .resizeNoDistort(DeImage.imageIconToBI(INFOVIEW_DISK_NO_FILE_LOADED_ICON_ICON),
                INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT, INFOVIEW_ARTWORK_RESIZE_TO_HEIGHT);
        bi = DeImage.createRoundedBorder(bi, 20, ColorManager.BORDER_THEME);
        artWork.setIcon(new ImageIcon(bi));
        artWork.repaint(30);
        artWorkIsDefault = true;
      }
      backPanel.repaint(100);
      dispatchEvents();
      System.gc();
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
    Debugger.warn("InfoView Preparing a dispatch: " + info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
    SwingUtilities.invokeLater(
        () -> {
          for (InfoViewUpdateListener l : listeners) {
            l.infoView(info);
          }
        });
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
   * @param info    The track to generate the information off of
   * @param text    The title of the track
   * @param beSmart Tells the parser to be smart and guess certain details.
   * @return An HTML string that can be used by html supporting GUI Components to
   *         display the information.
   */
  private String infoToString(AudioInfo info, String text, boolean beSmart) {
    if (!beSmart) {
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
    } else {
      String author = text.contains("-") ? text.split("-", 2)[0] : "Unknown";
      return ("<html><body style=\"font-family='Trebuchet MS';\"><p style=\"text-align: left;\"><span style=\"color: "
          +
          ColorManager.MAIN_FG_STR +
          ";font-size: 12px;\"><strong>" +
          text
          +
          "</strong></span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 10px\">" +
          author +
          "</span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 8px\">" +
          "Unknown" +
          "kpbs," +
          "Unknown" +
          "kHz," +
          "N:N:N"
          +
          "</span></p></body></html>");
    }
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
