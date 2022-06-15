package com.jackmeng.app.components.inheritable;

import javax.swing.*;

import com.jackmeng.constant.Manager;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

public class TabButton extends JPanel {

  public static interface RemoveTabListener {
    public void onRemoveTab();
  }

  private final JTabbedPane parentPane;

  private transient RemoveTabListener listener;

  public class CloseTabButton extends JButton implements ActionListener {

    public CloseTabButton() {
      setPreferredSize(new Dimension(Manager.BUTTON_STD_ICON_WIDTH_N_HEIGHT, Manager.BUTTON_STD_ICON_WIDTH_N_HEIGHT));
      setToolTipText("Close Tab");
      setUI(getUI());

      setContentAreaFilled(false);
      setFocusable(false);
      setBorder(null);
      setBorder(null);
      setBorderPainted(false);
      setRolloverEnabled(false);
      addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(1));
      g2.setColor(Color.WHITE);
      g2.drawLine(3, 3, getWidth() - 3, getHeight() - 3);
      g2.drawLine(getWidth() - 3, 3, 3, getHeight() - 3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int i = parentPane.indexOfTabComponent(TabButton.this);
      if (i != -1) {
        parentPane.remove(i);
        dispatchRemoveEvent();
      }
    }
  }

  public TabButton(JTabbedPane parent) {
    this.parentPane = parent;
    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    setOpaque(false);

    JLabel label = new JLabel() {
      @Override
      public String getText() {
        int i = parentPane.indexOfTabComponent(TabButton.this);
        if (i != -1) {
          return parentPane.getTitleAt(i);
        }
        return null;
      }
    };

    add(label);
    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
    add(new CloseTabButton());
    setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
  }

  public CloseTabButton getInternalButton() {
    return (CloseTabButton) getComponent(1);
  }

  public void setListener(RemoveTabListener listener) {
    this.listener = listener;
  }

  public void dispatchRemoveEvent() {
    if (listener != null) {
      listener.onRemoveTab();
    }
  }
}
