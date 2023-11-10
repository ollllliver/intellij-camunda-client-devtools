package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.openapi.ui.ComboBox;

public class ServiceTaskInputComponent extends ComboBox<String> implements InputComponentInterface {
    private final String[] options;

    public ServiceTaskInputComponent(String[] options) {
        super(options);
        this.options = options;
    }

    @Override
    public String getInputValue() {
        return options[getSelectedIndex()];
    }
}
