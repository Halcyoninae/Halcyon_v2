package com.jackmeng.app.components.dialog;

import com.jackmeng.app.components.dialog.ConfirmWindow.ConfirmationListener;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import javax.swing.*;
import java.awt.*;

public class LegalNoticeDialog extends JFrame implements Runnable {
    private String legalContext = "";
    private transient ConfirmationListener[] listeners;

    public LegalNoticeDialog(String legalContext, ConfirmationListener... listeners) {
        this.legalContext = legalContext;
        this.listeners = listeners;

        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setTitle("License Agreement");
        setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
        setResizable(false);
        setFocusable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.setPreferredSize(
                new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));

        getContentPane().add(mainPane);

        JTextArea legalNotice = new JTextArea(legalContext);
        legalNotice.setLineWrap(true);
        legalNotice.setWrapStyleWord(true);
        legalNotice.setEditable(false);
        legalNotice.setAlignmentY(Component.CENTER_ALIGNMENT);

        JScrollPane inheritedScrollPane = new JScrollPane(legalNotice);
        inheritedScrollPane.setPreferredSize(
                new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_SCROLL_PANE_MIN_HEIGHT));

        JButton confirm = new JButton("Ok");
        confirm.addActionListener(e -> dispatchEvents(true));

        JButton cancel = new JButton("Close");
        cancel.addActionListener(e -> dispatchEvents(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(confirm);
        buttonPanel.add(cancel);

        mainPane.add(inheritedScrollPane, BorderLayout.CENTER);
        mainPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    public synchronized ConfirmationListener[] getConfirmListeners() {
        return listeners;
    }

    public String getLegalText() {
        return legalContext;
    }

    private void dispatchEvents(boolean status) {
        if (listeners != null) {
            new Thread(() -> {
                for (ConfirmationListener listener : listeners) {
                    listener.onStatus(status);
                }
            }).start();
        }
        dispose();
    }

    @Override
    public void run() {
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }
}
