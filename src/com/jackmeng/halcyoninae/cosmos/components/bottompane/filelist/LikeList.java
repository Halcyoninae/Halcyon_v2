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

import com.jackmeng.halcyoninae.cosmos.events.FVRightClick.RightClickHideItemListener;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.filesystem.VirtualFolder;
import com.jackmeng.halcyoninae.halcyon.runtime.Program;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A type of non-removable FileList viewable.
 * <p>
 * This represents the liked tracks the user's
 * liked tracks.
 * <p>
 * In order to do such, everything here is handled
 * as a "Virtual Folder" meaning that its just
 * a list of folders to keep.
 * <p>
 * This viewport is static meaning the user
 * cannot close this.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList
 * @since 3.1
 */
public class LikeList extends FileList {
    private static final RightClickHideItemListener itemListener = content -> {
        Global.ll.unset(content);
        Program.cacher.pingLikedTracks();

    };
    private final transient VirtualFolder folder;

    /**
     * Inits the LikeList object as a child of the FileList
     * class with a Virtual Folder to represent this viewport.
     */
    public LikeList() {
        super(new VirtualFolder("Liked", Program.fetchLikedTracks()),
            Global.rd.getFromAsImageIcon(FILEVIEW_ICON_FOLDER_CLOSED),
            Global.rd.getFromAsImageIcon(FILEVIEW_ICON_FOLDER_OPEN),
            Global.rd.getFromAsImageIcon(FILEVIEW_ICON_LIKED_FILE), "Unlike", itemListener);
        folder = (VirtualFolder) getFolderInfo();
    }

    /**
     * Removes a track from the liked list listing.
     *
     * @param file The file's absolute path to remove from.
     */
    public void unset(String file) {
        try {
            Debugger.warn("Requesting Unset: " + file);
            for (File f : getFileMap().keySet()) {
                if (f.getAbsolutePath().equals(file)) {
                    getFileMap().remove(f);
                    folder.removeFile(f);
                    Debugger.info(folder);
                    DefaultTreeModel model = (DefaultTreeModel) getTree().getModel();
                    model.removeNodeFromParent(getFileMap().get(f));
                    model.reload();
                    Debugger.good("Checking FileMap: " + getFileMap().get(f));
                    Debugger.warn(folder.removeFile(f));
                    Debugger.warn("UNSET: " + f.getAbsolutePath());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adss a track to the liked list listing.
     *
     * @param file The file's absolute path to add.
     */
    public void set(String file) {
        Debugger.warn("LIKED_LIST_SET: " + folder.getAsListFiles().contains(new File(file)));
        if (!folder.getAsListFiles().contains(new File(file))) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new File(file).getName());
            getFileMap().put(new File(file), node);
            super.getRoot().add(node);
            getTree().revalidate();
            folder.addFile(new File(file));
            ((DefaultTreeModel) getTree().getModel()).reload();
            Debugger.info(folder);
        }

    }

    @Override
    public void revalidateFiles() {
        List<File> toRemove = new ArrayList<>();
        for (File f : getFileMap().keySet()) {
            if (!f.exists() || !f.isFile()) {
                ((DefaultTreeModel) getTree().getModel()).removeNodeFromParent(getFileMap().get(f));
                toRemove.add(f);
                Debugger.warn("File not found: " + f.getName());
            }
        }
        for (File f : toRemove) {
            getFileMap().remove(f);
        }
    }

    /**
     * Returns the virtual folder representing
     * this viewport.
     *
     * @return {@link com.jackmeng.halcyoninae.halcyon.filesystem.VirtualFolder}
     */
    public VirtualFolder getFolder() {
        return folder;
    }

    /**
     * Checks if a given file is "liked" meaning it
     * exists in the current virtual folder.
     *
     * @param file The file's absolute path
     * @return (true | | false) if the file is contained in the list or not.
     */
    public boolean isLiked(String file) {
        return Arrays.asList(folder.getFiles()).contains(new File(file));
    }


    /**
     * @return String
     */
    @Override
    public String getRootNameTree() {
        return "Liked";
    }

}
