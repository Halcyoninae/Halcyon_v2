package com.halcyoninae.cosmos.special;

import java.beans.ConstructorProperties;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * A buton that can hold an attribute
 *
 * @author Jack Meng
 * @since 3.3
 */
public class AttributableButton extends JButton {
  private transient String attribute;

  public AttributableButton(Icon icon) {
    super((String) null, icon);
  }

  @ConstructorProperties({ "text" })
  public AttributableButton(String text) {
    super(text, (Icon) null);
  }

  /**
   * @param e The attribute to be set
   */
  public void setAttribute(String e) {
    this.attribute = e;
  }

  /**
   *
   * @return The attribute that was set
   */
  public String getAttribute() {
    return attribute;
  }
}