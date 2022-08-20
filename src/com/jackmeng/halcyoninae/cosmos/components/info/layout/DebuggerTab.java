package com.jackmeng.halcyoninae.cosmos.components.info.layout;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons.LegalNoticeButton;
import com.jackmeng.halcyoninae.cosmos.components.info.InformationTab;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a tab for the InformationDialog that shows
 * a bunch of property and information regarding the internals
 * of the system and program.
 *
 * @author Jack Meng
 * @since 3.2
 */
public class DebuggerTab extends JScrollPane implements InformationTab {
    private final JPanel panel;
    private final JLabel memLabel;
    private final String defaultMemLabel = "<html><p><strong>Memory (mB):</strong></p></html>";

    public DebuggerTab() {
        setPreferredSize(new Dimension(LegalNoticeButton.LEGALNOTICEDIALOG_MIN_WIDTH, LegalNoticeButton.LEGALNOTICEDIALOG_MIN_HEIGHT));
        setFocusable(false);

        panel = new JPanel();
        panel.setPreferredSize(getPreferredSize());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*
         * Configure the memory display elements
         * Includes the memory progress bar
         * Includes the memory text display
         */

        memLabel = new JLabel(defaultMemLabel + " ");

        JProgressBar memBar = new JProgressBar(0, 5);
        memBar.setPreferredSize(new Dimension(getPreferredSize().width - 50, 10));
        memBar.setForeground(ColorManager.MAIN_FG_THEME);
        memBar.setBackground(ColorManager.MAIN_BG_THEME);
        memBar.setStringPainted(false);
        memBar.setBorderPainted(false);

        JPanel memoryContainer = new JPanel();
        memoryContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        memoryContainer.add(memLabel);
        memoryContainer.add(memBar);

        panel.add(memoryContainer);

        getViewport().add(panel);

        new Thread(() -> {
            while (true) {
                long total = Runtime.getRuntime().totalMemory();
                long free = Runtime.getRuntime().freeMemory();
                synchronized (memLabel) {
                    memLabel.setText("<html><p><strong>Memory (mB):</strong></p></html>" + " " + (total - free) / 1024 / 1024
                            + "/" + total / 1024 / 1024);
                    memLabel.setToolTipText((total - free) / 1024 / 1024
                            + "/" + total / 1024 / 1024);
                }
                memBar.setMaximum(((int) total) / 1024 / 1024);
                memBar.setValue((int) (total - free) / 1024 / 1024);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // IGNORED
                }
            }
        }).start();
    }

    /**
     * @return String
     */
    @Override
    public String getName() {
        return "System";
    }

    /**
     * @return String
     */
    @Override
    public String getToolTip() {
        return "View the machine's information";
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

}
