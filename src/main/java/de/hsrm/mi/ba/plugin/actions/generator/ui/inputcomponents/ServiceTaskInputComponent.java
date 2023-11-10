package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.openapi.ui.ComboBox;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;

public class ServiceTaskInputComponent extends InputComponent {
    private final ComboBox<String> comboBox;
    private final String[] options = {"Option 1", "Option 2", "Option 3"};

    public ServiceTaskInputComponent(Variable var) {
        super(var);
        comboBox = new ComboBox<>(options);
        add(comboBox);
    }

    @Override
    public String getInputValue() {
        return options[comboBox.getSelectedIndex()];
    }
}
