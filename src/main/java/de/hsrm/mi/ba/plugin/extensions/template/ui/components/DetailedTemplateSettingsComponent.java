package de.hsrm.mi.ba.plugin.extensions.template.ui.components;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBTextField;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

public class DetailedTemplateSettingsComponent extends JPanel {

    private final JBTextField templateName = new JBTextField();

    public DetailedTemplateSettingsComponent(TemplateSettingsConfigurable controller) {
        templateName.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                controller.changeName(templateName.getText());
            }
        });
        add(LabeledComponent.create(templateName, "Template name: "));
    }

    public void setTemplate(Template template) {
        this.templateName.setText(template.getName());
    }
}
