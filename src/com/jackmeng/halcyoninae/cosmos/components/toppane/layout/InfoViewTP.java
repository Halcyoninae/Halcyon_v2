/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.cosmos.components.toppane.layout;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.cloudspin.CloudSpinFilters;
import com.jackmeng.halcyoninae.cloudspin.lib.hinter.GradientImg;
import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.CLIStyles;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.debug.TConstr;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;
import com.jackmeng.halcyoninae.tailwind.audioinfo.AudioInfo;

/**
 * This class sits on the most upper part of the GUI frame.
 * It displays a simple list of information regarding the current
 * stream and nothing else.
 * <p>
 * This panel does not show every information available, but only a specific
 * part.
 * <p>
 * If the user wants to know more about the audio file
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.tailwind.audioinfo.AudioInfoDialog
 * @since 3.0
 */
public class InfoViewTP extends JPanel implements ComponentListener {

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

    private final JPanel topPanel;
    private final JPanel backPanel;
    private final JLabel infoDisplay;
    private final JLabel artWork;
    private final transient ArrayList<InfoViewUpdateListener> listeners;
    private transient BufferedImage backPanelArt;
    private transient AudioInfo info;
    private String infoTitle;
    private boolean artWorkIsDefault = true;

    public InfoViewTP() {
        super();
        info = new AudioInfo();
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
                if (Halcyon.bgt.getFrame().isVisible() && Halcyon.bgt.getFrame().isShowing() && backPanelArt != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    float compositeAlpha = 0.5f;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);

                    if (ExternalResource.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE)
                            .equals("top")) {
                        compositeAlpha = 0.2f;
                    } else {
                        compositeAlpha = 0.6f;
                    }
                    g2d.setComposite(
                            AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER,
                                    compositeAlpha));
                    g2d.drawImage(CloudSpin.grabCrop(backPanelArt, backPanel.getVisibleRect()), 0, 0,
                            backPanel.getWidth(), backPanel.getHeight(), null);
                    g2d.dispose();
                }
            }
        };

        __refresh_draw_bg_img(true);

        backPanel.setPreferredSize(
                getPreferredSize());
        backPanel.setOpaque(false);

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

        infoTitle = ExternalResource.pm.get(
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
     * WARNING: Does not call a refresh of the backpanel art which may
     * result in artifacts being left behind when calling a redraw.
     */
    private void __refresh_draw_bg_img(boolean draw_as_first) {
        Debugger.alert(new TConstr(new CLIStyles[] { CLIStyles.GREEN_BG, CLIStyles.WHITE_TXT },
                "Defaulting a new background image."));
        BufferedImage img = info.getArtwork();
        if (!draw_as_first && info.hasArtwork()) {
            img = CloudSpin.grabCrop(img,
                    new Rectangle(0, 0, img.getWidth(), this.getPreferredSize().height));
            if (ExternalResource.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GRADIENT)
                    .equals("true")) {
                img = GradientImg.createGradientFade(img, 255, 0, GradientImg.TOP);

                switch (com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource.pm
                        .get(com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager.KEY_INFOVIEW_BACKDROP_GRADIENT_STYLE)) {
                    case "focused":
                        img = GradientImg.createGradientFade(img, 255, 0, GradientImg.BOTTOM);
                        break;
                    case "left":
                        img = GradientImg.createGradientFade(img, 255, 0, GradientImg.LEFT);
                        break;
                    case "right":
                        img = GradientImg.createGradientFade(img, 255, 0, GradientImg.RIGHT);
                        break;

                }
            }

            if (ExternalResource.pm.get(ProgramResourceManager.KEY_INFOVIEW_BACKDROP_USE_GREYSCALE)
                    .equals("true")) {
                img = CloudSpinFilters.filters[CloudSpinFilters.AFF_GREY].filter(img, img);
            }
        } else {
            img = null;
        }

        backPanelArt = img;
        img = null;
    }

    /**
     * This method is pinged whenever the information regarding
     * the current audio file needs updating.
     * <p>
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
                Debugger.warn("Using beSmart toolkit...");
            }
            if (!beSmart) {
                infoTitle = ExternalResource.pm.get(
                        ProgramResourceManager.KEY_USE_MEDIA_TITLE_AS_INFOVIEW_HEADER)
                        .equals("true")
                                ? info.getTag(AudioInfo.KEY_MEDIA_TITLE)
                                : new File(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)).getName();
                infoDisplay.setText(infoToString(info, infoTitle, false));
                infoDisplay.setToolTipText(infoToString(info, info.getTag(AudioInfo.KEY_MEDIA_TITLE), false));
                Debugger.info("Using nonSmart (no guessing) toolkit. Phew!");
            } else {
                infoTitle = f.getName();
                infoDisplay.setText(infoToString(info, infoTitle, true));
                infoDisplay.setToolTipText(infoToString(info, infoTitle, true));
                Debugger.warn("Using beSmart (guessing) toolkit. Got: " + infoTitle);
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
                bi = null;
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
            __refresh_draw_bg_img(false);
            backPanel.repaint(100);
            __dispatch_();
            System.gc();
        }
    }

    /**
     * This method alerts every linked listener about
     * the new info being updated
     * <p>
     * This method is threaded in order to blocking
     * other functionalities.
     */
    private void __dispatch_() {
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
                    "</strong></span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 10px\">"
                    +
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
                    "</strong></span></p><p style=\"text-align: left;\"><span style=\"color: #ffffff;font-size: 10px\">"
                    +
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
}
