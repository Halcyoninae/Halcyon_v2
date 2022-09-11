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

package com.jackmeng.halcyoninae.tailwind.audioinfo;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
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
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
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
                System.gc();
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
