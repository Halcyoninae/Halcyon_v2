package com.jackmeng.app.components.dialog;

import javax.swing.*;

import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.utils.TimeParser;
import com.jackmeng.audio.AudioInfo;

import java.awt.*;

public class AudioInfoDialog extends JFrame implements Runnable {
  private JSplitPane mainPane;
  private JScrollPane artWorkPanel;
  private JPanel artWork;
  private JScrollPane infoPanel;
  private JEditorPane infoText;

  // Non Gui Components
  private AudioInfo info;

  public AudioInfoDialog(AudioInfo info) {
    setTitle(Manager.AUDIOINFO_WIN_TITLE);
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    setPreferredSize(new Dimension(Manager.AUDIOINFO_MIN_WIDTH, Manager.AUDIOINFO_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.AUDIOINFO_MIN_WIDTH, Manager.AUDIOINFO_MIN_HEIGHT));

    artWork = new JPanel() {
      @Override
      public synchronized void paint(Graphics g) {
        super.paint(g);
        g.drawImage(info.getArtwork(), 0, 0, this);
      }
    };
    artWork.setPreferredSize(new Dimension(info.getArtwork().getWidth(), info.getArtwork().getHeight()));

    artWorkPanel = new JScrollPane();
    artWorkPanel.setViewportView(artWork);
    artWorkPanel.setBorder(BorderFactory.createEmptyBorder());
    artWorkPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    artWorkPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    artWorkPanel.setPreferredSize(new Dimension(Manager.AUDIOINFO_ARTWORK_PANE_WIDTH, Manager.AUDIOINFO_MIN_HEIGHT));

    infoText = new JEditorPane();
    infoText.setEditable(false);
    infoText.setContentType("text/html");
    infoText.setText(infoToHtml(info));

    infoPanel = new JScrollPane();
    infoPanel.setViewportView(infoText);
    infoPanel.setBorder(BorderFactory.createEmptyBorder());
    infoPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    infoPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    infoPanel.setPreferredSize(new Dimension(Manager.AUDIOINFO_INFO_PANE_WIDTH, Manager.AUDIOINFO_MIN_HEIGHT));

    mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artWorkPanel, infoPanel);
    mainPane.setPreferredSize(getPreferredSize());
    mainPane.setDividerLocation(Manager.AUDIOINFO_DIVIDER_LOCATION);

    getContentPane().add(mainPane);
  }

  public AudioInfo getInfo() {
    return info;
  }

  private static synchronized String parseAsProperty(String key, String ti) {
    return "<u><b>"+key+"</b></u>: "+ti+"<br>";
  }

  private static synchronized String infoToHtml(AudioInfo in) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body>");
    sb.append(parseAsProperty("Title", in.getTag(AudioInfo.KEY_MEDIA_TITLE)));
    sb.append(parseAsProperty("Artist", in.getTag(AudioInfo.KEY_MEDIA_ARTIST)));
    sb.append(parseAsProperty("Album", in.getTag(AudioInfo.KEY_ALBUM)));
    sb.append(parseAsProperty("Genre", in.getTag(AudioInfo.KEY_GENRE)));
    sb.append(parseAsProperty("Bitrate", in.getTag(AudioInfo.KEY_BITRATE)));
    sb.append(parseAsProperty("Duration", TimeParser.fromSeconds(Integer.parseInt(in.getTag(AudioInfo.KEY_MEDIA_DURATION)))));
    sb.append(parseAsProperty("Sample Rate", in.getTag(AudioInfo.KEY_SAMPLE_RATE)));
    sb.append(parseAsProperty("File Name", in.getTag(AudioInfo.KEY_FILE_NAME)));
    sb.append(parseAsProperty("File Path", in.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)));
    sb.append("</body></html>");
    return sb.toString();
  }

  @Override
  public void run() {
    pack();
    setVisible(true);
  }
}
