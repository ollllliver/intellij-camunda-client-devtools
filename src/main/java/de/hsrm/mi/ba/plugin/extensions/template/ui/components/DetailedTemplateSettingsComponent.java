package de.hsrm.mi.ba.plugin.extensions.template.ui.components;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBTextField;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;

import javax.swing.*;

public class DetailedTemplateSettingsComponent extends JPanel {

    private final JBTextField templateName = new JBTextField();

    public DetailedTemplateSettingsComponent() {
        add(LabeledComponent.create(templateName, "Template name: "));
    }

    public void setTemplate(Template template) {
        this.templateName.setText(template.getName());
    }
}
