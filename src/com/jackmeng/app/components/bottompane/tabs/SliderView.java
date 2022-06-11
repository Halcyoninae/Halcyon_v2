package com.jackmeng.app.components.bottompane.tabs;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import com.jackmeng.app.components.bottompane.BPTabs;
import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;

public class SliderView extends JScrollPane implements BPTabs {

  @Override
  public String getTabToolTip() {
    return Manager.SLIDERS_DEFAULT_TAB_NAME;
  }

  @Override
  public ImageIcon getTabIcon() {
    return Global.rd.getFromAsImageIcon(Manager.SLIDERS_TAB_ICON);
  }

  @Override
  public String getTabName() {
    return Manager.SLIDERS_DEFAULT_TAB_NAME;
  }

  @Override
  public JComponent getTabContent() {
    return this;
  }
  
}
