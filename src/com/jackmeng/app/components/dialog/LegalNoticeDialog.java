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
    }

    public synchronized ConfirmationListener[] getConfirmListeners() {
        return listeners;
    }

    public String getLegalText() {
        return legalContext;
    }

    private void dispatchEvents(boolean status) {
        new Thread(() -> {
            for (ConfirmationListener listener : listeners) {
                listener.onStatus(status);
            }
        }).start();
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
