package mp4j.components.inheritabledialog;

import javax.swing.JFrame;

import mp4j.Global;
import mp4j.Manager;

/**
 * This class mainly provides the necessary inheritable attributes
 * to the SelectionApplicableFolders
 * {@link mp4j.components.dialog.SelectApplicableFolders} dialog
 * 
 * It should not be run by itself as it only provides assets inherits instead
 * of actual component inheritance.
 * 
 * @author Jack Meng
 * @since 3.0
 * @see mp4j.components.dialog.SelectApplicableFolders
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
