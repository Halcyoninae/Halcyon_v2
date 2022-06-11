package com.jackmeng.app.components.inheritabledialog;

import javax.swing.JFrame;

import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;

/**
 * This class mainly provides the necessary inheritable attributes
 * to the SelectionApplicableFolders
 * {@link com.jackmeng.app.components.dialog.SelectApplicableFolders} dialog
 * 
 * It should not be run by itself as it only provides assets inherits instead
 * of actual component inheritance.
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.app.components.dialog.SelectApplicableFolders
 * @see javax.swing.JFrame
 */
public class FSVDefault extends JFrame {

  /**
   * Constructs the inheritable FSVDefault object to be inherited by
   */
  public FSVDefault() {
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
  }
}
