package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.ui.components.JBTextField;

public class StringInputComponent extends JBTextField implements InputComponentInterface {

    @Override
    public String getInputValue() {
        return getText();
    }
}
