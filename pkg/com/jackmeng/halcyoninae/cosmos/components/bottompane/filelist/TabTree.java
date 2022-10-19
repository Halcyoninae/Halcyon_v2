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

package com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Represents a standard tree in the bottom pane
 * viewport.
 *
 * @author Jack Meng
 * @since 3.1
 */
public interface TabTree {
    /**
     * Calls the remove function on the JTree instance.
     *
     * @param nodeName The Node to remove from the tree.
     */
    void remove(String nodeName);

    /**
     * Gets a String representation of a selected node.
     *
     * @return The selected node's name.
     */
    String getSelectedNode(DefaultMutableTreeNode node);

    /**
     * @return The Path of the absolute tree being represented
     */
    String getPath();

    /**
     * Gets the absolue parent FileList object
     *
     * @return The parent FileList object
     */
    FileList getFileList();

    /**
     * Determines if the tree holds
     * a virtual folder.
     *
     * @return (true | | false)
     */
    boolean isVirtual();

    /**
     * Gets the default root name
     *
     * @return The root node name string
     */
    default String getRootNameTree() {
        return "";
    }

    /**
     * Sorts the Tree by some method of mostly (A-Z) alphabetical.
     */
    void sort(TabTreeSortMethod e);

    enum TabTreeSortMethod {
        ALPHABETICAL, SIZE, SHUFFLE, REV_ALPHABETICAL
    }
}
