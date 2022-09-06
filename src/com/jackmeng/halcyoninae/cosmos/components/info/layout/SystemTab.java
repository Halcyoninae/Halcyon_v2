package com.jackmeng.halcyoninae.cosmos.components.info.layout;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons.LegalNoticeButton;
import com.jackmeng.halcyoninae.cosmos.components.info.InformationTab;

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
public class SystemTab extends JScrollPane implements InformationTab {
    private final JEditorPane info;

    public SystemTab() {
        setPreferredSize(new Dimension(LegalNoticeButton.LEGALNOTICEDIALOG_MIN_WIDTH, LegalNoticeButton.LEGALNOTICEDIALOG_MIN_HEIGHT));
        setFocusable(false);

        info = new JEditorPane();
        info.setEditable(false);
        info.setContentType("text/html");
        info.setCaretPosition(0);

        getViewport().add(info);
        SwingUtilities.invokeLater(this::updateText);
    }

    private void updateText() {
        info.setText("<html><body><p>" + getProperties() + "<html><body><p>");
    }


    /**
     * @return String
     */
    private String getProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("JAVA_HOME path: ").append(System.getProperty("java.home")).append("<br>");
        sb.append("Java Vendor: ").append(System.getProperty("java.vendor")).append("<br");
        sb.append("Operating System: ").append(System.getProperty("os.name")).append(" ").append(System.getProperty("os.arch")).append(" ").append(System.getProperty("os.version")).append("<br>");
        sb.append("Operating User: ").append(System.getProperty("user.name")).append("<br>");
        sb.append("Avaliable Processors: ").append(Runtime.getRuntime().availableProcessors()).append("<br>");
        sb.append("Runtime Version: ").append(Runtime.version()).append("<br>");
        sb.append("Free Memory: ").append(Runtime.getRuntime().freeMemory()).append("<br>");
        sb.append("Max Memory: ").append(Runtime.getRuntime().maxMemory()).append("<br>");
        sb.append("Total Memory: ").append(Runtime.getRuntime().totalMemory()).append("<br>");
        sb.append("System Env: ").append(System.getenv().toString()).append("<br>");
        return sb.toString();
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
