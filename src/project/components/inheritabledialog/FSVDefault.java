package project.components.inheritabledialog;

import javax.swing.JFrame;

import project.Global;
import project.Manager;

/**
 * This class mainly provides the necessary inheritable attributes
 * to the SelectionApplicableFolders
 * {@link project.components.dialog.SelectApplicableFolders} dialog
 * 
 * It should not be run by itself as it only provides assets inherits instead
 * of actual component inheritance.
 * 
 * @author Jack Meng
 * @since 3.0
 * @see project.components.dialog.SelectApplicableFolders
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
