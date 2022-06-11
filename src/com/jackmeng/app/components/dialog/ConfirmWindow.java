package com.jackmeng.app.components.dialog;

import javax.swing.*;

import com.jackmeng.app.Global;
import com.jackmeng.app.constant.Manager;

import java.awt.*;
import java.awt.event.*;

public class ConfirmWindow extends JFrame implements Runnable, ActionListener {
  public interface ConfirmationListener {
    void onStatus(boolean status);
  }

  private JButton confirm, cancel;
  private JTextArea prompt;
  private JPanel jp;
  private JScrollPane container;
  private transient ConfirmationListener[] listeners;

  public ConfirmWindow(String content, ConfirmationListener... listeners) {
    super(Manager.DIALOG_CONFIRM_WIN_TITLE);
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    this.listeners = listeners;
    setPreferredSize(new Dimension(Manager.DIALOG_CONFIRM_MIN_WIDTH, Manager.DIALOG_CONFIRM_MIN_HEIGHT));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    confirm = new JButton("Confirm");
    confirm.setAlignmentY(Component.CENTER_ALIGNMENT);
    confirm.addActionListener(this);

    cancel = new JButton("Cancel");
    cancel.setAlignmentY(Component.CENTER_ALIGNMENT);
    cancel.addActionListener(this);

    prompt = new JTextArea(content);
    prompt.setLineWrap(true);
    prompt.setWrapStyleWord(true);
    prompt.setAlignmentY(Component.CENTER_ALIGNMENT);
    prompt.setEditable(false);

    container = new JScrollPane(prompt);
    container.setPreferredSize(
        new Dimension(Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH, Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT));
    container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    jp = new JPanel();
    jp.setLayout(new FlowLayout(FlowLayout.CENTER));
    jp.setPreferredSize(
        new Dimension(Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH, Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT));
    jp.add(confirm);
    jp.add(cancel);

    setLayout(new BorderLayout());
    setResizable(false);
    add(container, BorderLayout.NORTH);
    add(jp, BorderLayout.SOUTH);

    getContentPane().add(container);
  }

  private void dispatchConfirmationEvents(boolean status) {
    for (ConfirmationListener listener : listeners) {
      listener.onStatus(status);
    }
  }

  @Override
  public void run() {
    pack();
    setAlwaysOnTop(true);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    dispatchConfirmationEvents(e.getSource() == confirm);
    dispose();
  }
}
