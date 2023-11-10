package de.hsrm.mi.ba.plugin.actions.generator.ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.ClassInputComponent;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.ServiceTaskInputComponent;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.StringInputComponent;
import de.hsrm.mi.ba.plugin.extensions.template.model.VarType;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CodeGenerationDialog extends DialogWrapper {

    private final List<Variable> variables;
    private final Map<String, Supplier<String>> inputFieldMap = new HashMap<>();
    private AnActionEvent anActionEvent;

    public CodeGenerationDialog(AnActionEvent anActionEvent, List<Variable> variables) {
        super(true);
        setTitle("Template Configuration");
        this.variables = variables;
        this.anActionEvent = anActionEvent;

        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        JComponent panel = new JBPanel<>();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(5);


        for (int i = 0; i < variables.size(); i++) {
            Variable var = variables.get(i);

            JComponent[] jComponents = new JComponent[3];
            jComponents[0] = new JLabel(var.getName());

            JComponent input;
            if (VarType.CLASS.equals(var.getType())) {
                ClassInputComponent classInput = new ClassInputComponent(anActionEvent.getProject());
                inputFieldMap.put(var.getName(), classInput::getInputValue);
                input = classInput;
            } else if (VarType.SERVICE_TASK.equals(var.getType())) {
                ServiceTaskInputComponent serviceTaskInput = new ServiceTaskInputComponent(new String[]{"Option 1", "Option 2", "Option 3"});
//                TODO: Liste von ServiceTask IDs mitgeben
                inputFieldMap.put(var.getName(), serviceTaskInput::getInputValue);
                input = serviceTaskInput;
            } else {
                StringInputComponent stringInput = new StringInputComponent();
                inputFieldMap.put(var.getName(), stringInput::getInputValue);
                input = stringInput;

            }
            jComponents[1] = input;

            jComponents[2] = new JLabel(var.getDescription());

            gbc.gridy = i;
            for (int j = 0; j < jComponents.length; j++) {
                JComponent component = jComponents[j];
                gbc.gridx = j;
                panel.add(component, gbc);
            }
        }
        return panel;
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


    public Map<String, Supplier<String>> getInputFieldMap() {
        return inputFieldMap;
    }
}
