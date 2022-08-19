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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jaudiotagger.tag.FieldKey;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;

/**
 * This is a window popup that shows information regarding the current
 * track by using the AudioInfo class.
 *
 * This is non resusable and a new instance must be initiated on every
 * AudioInfo launch.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class AudioInfoDialog extends JFrame implements Runnable {

    /// AUDIOINFO Window Config START
    final String AUDIOINFO_WIN_TITLE = "Halcyon - Audio Info";
    final int AUDIOINFO_MIN_WIDTH = 600;
    final int AUDIOINFO_MIN_HEIGHT = 400;

    final int AUDIOINFO_ARTWORK_PANE_WIDTH = AUDIOINFO_MIN_WIDTH - 200;
    final int AUDIOINFO_INFO_PANE_WIDTH = AUDIOINFO_MIN_WIDTH - 150;
    /// AUDIOINFO Window Config END

    private final JSplitPane mainPane;
    private final JScrollPane artWorkPanel;
    private final JPanel artWork;
    private final JScrollPane infoPanel;
    private final JPopupMenu rightClickArtwork;
    private final JMenuItem rightClickArtworkShowBig;

    // Transient Components
    private transient boolean toOpenArtwork = true;
    private transient AudioInfoArtworkDialog aiad;
    private transient BufferedImage img;
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
            }

        });

        rightClickArtwork = new JPopupMenu();
        rightClickArtworkShowBig = new JMenuItem("View Artwork");
        rightClickArtworkShowBig.setToolTipText("View the artwork in a bigger window");
        rightClickArtworkShowBig.addActionListener(x -> {
            if (toOpenArtwork) {
                SwingUtilities.invokeLater(aiad::run);
                toOpenArtwork = false;
            } else {
                Debugger.warn("Already launched an Artwork Viewport, not running another!!");
            }
        });

        img = CloudSpin.resizeToFitViewport(new Dimension(AUDIOINFO_ARTWORK_PANE_WIDTH, AUDIOINFO_MIN_HEIGHT),
                info.getArtwork());

        artWork = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(img, (artWork.getWidth() - img.getWidth()) / 2, (artWork.getHeight() - img.getHeight()) / 2,
                        rootPane);
                g.dispose();
            }
        };
        artWork.setDoubleBuffered(true);

        artWorkPanel = new JScrollPane();
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

        infoPanel = new JScrollPane();
        infoPanel.setViewportView(infoText);
        infoPanel.setBorder(BorderFactory.createEmptyBorder());
        infoPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        infoPanel.setPreferredSize(new Dimension(AUDIOINFO_INFO_PANE_WIDTH, AUDIOINFO_MIN_HEIGHT));

        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artWorkPanel, infoPanel);
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
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append(parseAsProperty("Title", in.getTag(AudioInfo.KEY_MEDIA_TITLE)));
        sb.append(parseAsProperty("Artist", in.getTag(AudioInfo.KEY_MEDIA_ARTIST)));
        sb.append(parseAsProperty("Album", in.getTag(AudioInfo.KEY_ALBUM)));
        sb.append(parseAsProperty("Genre", in.getTag(AudioInfo.KEY_GENRE)));
        sb.append(parseAsProperty("Bitrate", in.getTag(AudioInfo.KEY_BITRATE)));
        sb.append(
                parseAsProperty("Duration",
                        TimeParser.fromSeconds(Integer.parseInt(in.getTag(AudioInfo.KEY_MEDIA_DURATION)))));
        sb.append(parseAsProperty("Sample Rate", in.getTag(AudioInfo.KEY_SAMPLE_RATE)));
        sb.append(parseAsProperty("File Name", in.getTag(AudioInfo.KEY_FILE_NAME)));
        sb.append(parseAsProperty("File Path", in.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)));
        sb.append(parseAsProperty("BPM", in.getRaw(FieldKey.BPM)));
        sb.append(parseAsProperty("Track", in.getRaw(FieldKey.TRACK)));
        sb.append(parseAsProperty("Year", in.getRaw(FieldKey.YEAR)));
        sb.append(parseAsProperty("Language", in.getRaw(FieldKey.LANGUAGE)));
        sb.append(parseAsProperty("Album Artist", in.getRaw(FieldKey.ALBUM_ARTIST)));
        sb.append(parseAsProperty("Composer", in.getRaw(FieldKey.COMPOSER)));
        sb.append(parseAsProperty("Disc", in.getRaw(FieldKey.DISC_NO)));
        sb.append(parseAsProperty("Comment", in.getRaw(FieldKey.COMMENT)));
        sb.append("</body></html>");
        return sb.toString();
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
