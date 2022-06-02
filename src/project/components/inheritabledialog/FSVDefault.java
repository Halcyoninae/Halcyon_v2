package project.components.inheritabledialog;

import javax.swing.JFrame;

import project.Global;
import project.Manager;

public class FSVDefault extends JFrame {
  
  public FSVDefault() {
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
  }
}
