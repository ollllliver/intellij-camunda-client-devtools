package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.ui.components.JBTextField;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;

public class StringInputComponent extends InputComponent  {
    private final JBTextField textField = new JBTextField();


    public StringInputComponent(Variable var) {
        super(var);
        add(textField);
    }

    @Override
    public String getInputValue() {
        return textField.getText();
    }
}
