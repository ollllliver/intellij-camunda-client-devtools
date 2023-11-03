package de.hsrm.mi.ba.plugin.extensions.template;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.JBSplitter;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import de.hsrm.mi.ba.plugin.extensions.template.ui.TemplateSettingsComponent;
import de.hsrm.mi.ba.plugin.extensions.template.ui.components.DetailedTemplateSettingsComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;

public class TemplateSettingsConfigurable implements Configurable {

    private TemplateSettingsComponent templateSettingsComponent;
    private DefaultListModel<Template> templatesModel;

    @Nls
    @Override
    public String getDisplayName() {
        return "My Plugin Configuration";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        templatesModel = TemplateSettingsState.getInstance().getTemplates();
        templateSettingsComponent = new TemplateSettingsComponent(this);
        return templateSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        TemplateSettingsState settings = TemplateSettingsState.getInstance();
        return !Arrays.equals(templatesModel.toArray(), settings.getTemplates().toArray());
    }

    @Override
    public void apply() {
        TemplateSettingsState templateSettingsState = TemplateSettingsState.getInstance();
        templateSettingsState.setTemplates(templatesModel);
    }

    @Override
    public void reset() {
        templatesModel = TemplateSettingsState.getInstance().getTemplates();
        templateSettingsComponent.setTemplates(templatesModel);
    }

    public void addTemplate() {
        String name = JOptionPane.showInputDialog("Geben Sie den Namen des neuen Templates ein:");
        templatesModel.addElement(new Template(name));
        templateSettingsComponent.selectElementAtIndex(templatesModel.size() - 1);
    }

    public void removeTemplate(int index) {
        templatesModel.removeElementAt(index);
        int size = templatesModel.getSize();
        if (index < size) {
            templateSettingsComponent.selectElementAtIndex(index);
        } else if (size != 0) {
            templateSettingsComponent.selectElementAtIndex(size - 1);
        } else templateSettingsComponent.getJbSplitter().setSecondComponent(null);
    }

    public void showDetailedTemplateSettingsOf(Template template) {
        JBSplitter jbSplitter = templateSettingsComponent.getJbSplitter();
        if (jbSplitter.getSecondComponent() == null) {
            jbSplitter.setSecondComponent(new DetailedTemplateSettingsComponent());
        }
        ((DetailedTemplateSettingsComponent) jbSplitter.getSecondComponent()).setTemplate(template);
    }
}
