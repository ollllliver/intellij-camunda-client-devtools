package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.ui.components.JBPanel;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;

import javax.swing.*;

public abstract class InputComponent extends JBPanel<InputComponent> implements InputComponentInterface {

    public InputComponent(Variable var){
        add(new JLabel(var.getName()));
    }

    @Override
    public abstract String getInputValue();
}
