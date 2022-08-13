package com.halcyoninae.cosmos.identifier;

import javax.swing.*;
import java.beans.ConstructorProperties;

/**
 * A buton that can hold an attribute
 *
 * @author Jack Meng
 * @since 3.3
 */
public class AttributableButton extends JButton {
    private transient String attribute;

    public AttributableButton(Icon icon) {
        super(null, icon);
    }

    @ConstructorProperties({"text"})
    public AttributableButton(String text) {
        super(text, null);
    }

    /**
     * @return The attribute that was set
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * @param e The attribute to be set
     */
    public void setAttribute(String e) {
        this.attribute = e;
    }
}