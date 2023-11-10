package de.hsrm.mi.ba.plugin.actions.generator.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBPanel;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.ClassInputComponent;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.InputComponent;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.ServiceTaskInputComponent;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.StringInputComponent;
import de.hsrm.mi.ba.plugin.extensions.template.model.VarType;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerationDialog extends DialogWrapper {

    private final List<Variable> variables;
    private final Map<String, InputComponent> inputFieldMap = new HashMap<>();

    public CodeGenerationDialog(List<Variable> variables) {
        super(true);
        setTitle("Template Configuration");
        this.variables = variables;

        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        JBPanel<InputComponent> panel = new JBPanel<>();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Variable variable : variables) {
            JLabel label = new JLabel(variable.getName() + ": ");
            InputComponent inputComponent = createInputComponent(variable);
            inputFieldMap.put(variable.getName(), inputComponent);
            panel.add(label);
            panel.add(inputComponent);
        }

        return panel;
    }

    @NotNull
    private InputComponent createInputComponent(Variable var) {
        if (var.getType() == VarType.STRING) {
            return new StringInputComponent(var);
        } else if (var.getType() == VarType.SERVICE_TASK) {
            return new ServiceTaskInputComponent(var);
        } else if (var.getType() == VarType.CLASS) {
            return new ClassInputComponent(var);
        }

        return null;
    }

    @Override
    protected ValidationInfo doValidate() {
        // Implement custom validation logic if needed
        return super.doValidate();
    }

    @Override
    protected void doOKAction() {
        // Implement logic to process the user input (e.g., retrieve values from inputComponents)
        super.doOKAction();
    }


    public Map<String, InputComponent> getInputFieldMap() {
        return inputFieldMap;
    }
}
