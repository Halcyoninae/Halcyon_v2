package com.jackmeng.music.components.bottompane;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.jackmeng.music.Global;
import com.jackmeng.music.Manager;

/**
 * This interface is for all tabs that are going to be added to the bottom pane.
 * 
 * It allows for future users to implement their own tabs as maybe ploogins into
 * the program!!!
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.music.components.bottompane.BottomPane
 */
public interface BPTabs {
  /**
   * Gets the desired component's
   * name to be displayed as the tab's name
   * in the Bottom pane
   * 
   * @return the name of the component to display on the tab
   */
  default String getTabName() {
    return "";
  }

  /**
   * Limits how many characters can be displayed in the tab's name
   * 
   * @return (true || false) depending on the programmer's wish
   */
  default boolean restrainTabName() {
    return true;
  }

  /**
   * ToolTip for the tab
   * 
   * @return the tooltip
   */
  default String getTabToolTip() {
    return "";
  }

  /**
   * The desired tab icon for the component
   * 
   * @return The desired ImageIcon
   */
  default ImageIcon getTabIcon() {
    return Global.rd.getFromAsImageIcon(Manager.ATTRIBUTES_TAB_ICON);
  }

  /**
   * Gets the actual component to be displayed
   * 
   * @return the component to be displayed
   */
  JComponent getTabContent();
}
