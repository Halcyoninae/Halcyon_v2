/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;
import org.jaudiotagger.tag.FieldKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * This is a window popup that shows information regarding the current
 * track by using the AudioInfo class.
 * <p>
 * This is non resusable and a new instance must be initiated on every
 * AudioInfo launch.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class AudioInfoDialog extends JFrame implements Runnable {

    /// AUDIOINFO Window Config START
    final String AUDIOINFO_WIN_TITLE       = "Halcyon - Audio Info";
    final int AUDIOINFO_MIN_WIDTH          = 600;
    final int AUDIOINFO_MIN_HEIGHT         = 400;

    final int AUDIOINFO_ARTWORK_PANE_WIDTH = AUDIOINFO_MIN_WIDTH - 200;
    final int AUDIOINFO_INFO_PANE_WIDTH    = AUDIOINFO_MIN_WIDTH - 150;
    /// AUDIOINFO Window Config END

    private final JPanel artWork;
    private final JPopupMenu rightClickArtwork;
    private final transient AudioInfoArtworkDialog aiad;
    private final transient BufferedImage img;
    // Transient Components
    private transient boolean toOpenArtwork = true;
    private transient AudioInfo info;

    public AudioInfoDialog(AudioInfo info) {
        setTitle(AUDIOINFO_WIN_TITLE);
        setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(AUDIOINFO_MIN_WIDTH, AUDIOINFO_MIN_HEIGHT));
        setMinimumSize(new Dimension(AUDIOINFO_MIN_WIDTH, AUDIOINFO_MIN_HEIGHT));

        aiad = new AudioInfoArtworkDialog(info);
        aiad.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                toOpenArtwork = true;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                toOpenArtwork = true;
            }
        });

        rightClickArtwork = new JPopupMenu();
        JMenuItem rightClickArtworkShowBig = new JMenuItem("View Artwork");
        rightClickArtworkShowBig.setToolTipText("View the artwork in a bigger window");
        rightClickArtworkShowBig.addActionListener(x -> {
            if (toOpenArtwork) {
                SwingUtilities.invokeLater(aiad);
                toOpenArtwork = false;
            } else {
                Debugger.warn("Already launched an Artwork Viewport, not running another!!");
            }
        });
        JMenuItem saveArtwork = new JMenuItem("Export Artwork");
        saveArtwork.setToolTipText("Extract the artwork to a working directory.");
        saveArtwork.addActionListener(x -> AudioInfo.extractArtwork(info));

        img = CloudSpin.resizeToFitViewport(new Dimension(AUDIOINFO_ARTWORK_PANE_WIDTH, AUDIOINFO_MIN_HEIGHT),
                info.getArtwork());

        img.setAccelerationPriority(0.9F);

        artWork = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (this.isVisible() || this.isShowing()) {
                    g.drawImage(img, (artWork.getWidth() - img.getWidth()) / 2,
                            (artWork.getHeight() - img.getHeight()) / 2,
                            rootPane);
                    g.dispose();
                }
            }
        };

        JScrollPane artWorkPanel = new JScrollPane();
        artWorkPanel.setViewportView(artWork);
        artWorkPanel.setBorder(BorderFactory.createEmptyBorder());
        artWorkPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        artWorkPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        artWorkPanel.setPreferredSize(new Dimension(AUDIOINFO_ARTWORK_PANE_WIDTH, AUDIOINFO_MIN_HEIGHT));
        artWorkPanel.setMinimumSize(artWorkPanel.getPreferredSize());

        JEditorPane infoText = new JEditorPane();
        infoText.setEditable(false);
        infoText.setContentType("text/html");
        infoText.setText(infoToHtml(info));

        JScrollPane infoPanel = new JScrollPane();
        infoPanel.setViewportView(infoText);
        infoPanel.setBorder(BorderFactory.createEmptyBorder());
        infoPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        infoPanel.setPreferredSize(new Dimension(AUDIOINFO_INFO_PANE_WIDTH, AUDIOINFO_MIN_HEIGHT));

        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artWorkPanel, infoPanel);
        mainPane.setPreferredSize(getPreferredSize());

        rightClickArtwork.add(rightClickArtworkShowBig);
        rightClickArtwork.add(saveArtwork);

        artWorkPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    rightClickArtwork.show(artWork, e.getX() + 10, e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    rightClickArtwork.show(artWork, e.getX() + 10, e.getY());
            }
        });

        getContentPane().add(mainPane);
    }

    /**
     * @param key
     * @param ti
     * @return String
     */
    private static synchronized String parseAsProperty(String key, String ti) {
        return "<u><b>" + key + "</b></u>: " + ti + "<br>";
    }

    /**
     * @param in
     * @return String
     */
    private static synchronized String infoToHtml(AudioInfo in) {
        return "<html><body>" +
                parseAsProperty("Title", in.getTag(AudioInfo.KEY_MEDIA_TITLE)) +
                parseAsProperty("Artist", in.getTag(AudioInfo.KEY_MEDIA_ARTIST)) +
                parseAsProperty("Album", in.getTag(AudioInfo.KEY_ALBUM)) +
                parseAsProperty("Genre", in.getTag(AudioInfo.KEY_GENRE)) +
                parseAsProperty("Bitrate", in.getTag(AudioInfo.KEY_BITRATE)) +
                parseAsProperty("Duration",
                        TimeParser.fromSeconds(Integer.parseInt(in.getTag(AudioInfo.KEY_MEDIA_DURATION))))
                +
                parseAsProperty("Sample Rate", in.getTag(AudioInfo.KEY_SAMPLE_RATE)) +
                parseAsProperty("File Name", in.getTag(AudioInfo.KEY_FILE_NAME)) +
                parseAsProperty("File Path", in.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)) +
                parseAsProperty("BPM", in.getRaw(FieldKey.BPM)) +
                parseAsProperty("Track", in.getRaw(FieldKey.TRACK)) +
                parseAsProperty("Year", in.getRaw(FieldKey.YEAR)) +
                parseAsProperty("Language", in.getRaw(FieldKey.LANGUAGE)) +
                parseAsProperty("Album Artist", in.getRaw(FieldKey.ALBUM_ARTIST)) +
                parseAsProperty("Composer", in.getRaw(FieldKey.COMPOSER)) +
                parseAsProperty("Disc", in.getRaw(FieldKey.DISC_NO)) +
                parseAsProperty("Comment", in.getRaw(FieldKey.COMMENT)) +
                "</body></html>";
    }

    /**
     * @return This instance's AudioInfo object that is being used to generate the
     *         compiled information.
     */
    public AudioInfo getInfo() {
        return info;
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            pack();
            setLocationByPlatform(true);
            setVisible(true);
            artWork.repaint(20);
        });
    }
}
