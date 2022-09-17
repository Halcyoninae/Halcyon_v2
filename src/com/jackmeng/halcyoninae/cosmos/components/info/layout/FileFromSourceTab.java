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

package com.jackmeng.halcyoninae.cosmos.components.info.layout;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons.LegalNoticeButton;
import com.jackmeng.halcyoninae.cosmos.components.info.InformationTab;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This is a special tab that can be used to write pure text to.
 * The programmar can also decide to use the builtin {@link #getContent(String)}
 * method to read the contents of a file.
 * All content can be in HTML or standard TEXT
 *
 * @author Jack Meng
 * @since 3.2
 */
public class FileFromSourceTab extends JScrollPane implements InformationTab {
    private final JEditorPane text;
    private String tabName = "";

    /**
     * Two arguments are accepted in the construction of this custom tab.
     *
     * @param tabName The preferred tab name (should be kept at a small length)
     * @param textTab The content of the tab {@link #getContent(String)}
     * @see #getContent(String)
     */
    public FileFromSourceTab(String tabName, String textTab) {
        setPreferredSize(new Dimension(LegalNoticeButton.LEGALNOTICEDIALOG_MIN_WIDTH,
            LegalNoticeButton.LEGALNOTICEDIALOG_MIN_HEIGHT));
        setFocusable(true);

        this.tabName = tabName;

        text = new JEditorPane();
        text.setEditable(false);
        text.setContentType("text/html");
        text.setText(textTab);
        text.setCaretPosition(0);

        getViewport().add(text);
    }

    /**
     * If the user decides to read from a file, this method should be used
     * to grab the content of the file. This method should be only used
     * in the constructor of this GUI object.
     *
     * @param file A String representing the file location
     * @return String The content of the file in the native encoding.
     */
    public static String getContent(String file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            ExternalResource.dispatchLog(e);
        }
        return sb.toString();
    }

    /**
     * @return String
     */
    @Override
    public String getName() {
        return tabName;
    }

    /**
     * @return String
     */
    @Override
    public String getToolTip() {
        return "View Legal Information regarding this program.";
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }
}
