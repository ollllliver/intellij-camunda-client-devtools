package de.hsrm.mi.ba.plugin.extensions.template;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.JBSplitter;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import de.hsrm.mi.ba.plugin.extensions.template.model.VarType;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;
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
        templateSettingsComponent.getTemplateListComponent().setTemplates(templatesModel);
    }

//    ##################################################################################################################
//    ########################################## Operations ############################################################
//    ##################################################################################################################

    /**
     * Adding new Template to Model by popup window for entering the new Template name.
     * New Template will be selected in the UI Template list.
     */
    public void addTemplate() {
        String name = JOptionPane.showInputDialog("Geben Sie den Namen des neuen Templates ein:");
        templatesModel.addElement(new Template(name));
        templateSettingsComponent.getTemplateListComponent().selectElementAtIndex(templatesModel.size() - 1);
    }

    /**
     * Removes indexed Template from Model.
     * Next element in UI list gets selected.
     * If there is no next, then last element in list.
     * If there is no element in list, then no element gets selected.
     *
     * @param index of Template in list to remove.
     */
    public void removeTemplate(int index) {
        templatesModel.removeElementAt(index);
        int size = templatesModel.getSize();
        if (index < size) {
            templateSettingsComponent.getTemplateListComponent().selectElementAtIndex(index);
        } else if (size != 0) {
            templateSettingsComponent.getTemplateListComponent().selectElementAtIndex(size - 1);
        } else
            templateSettingsComponent.getJbSplitter().setSecondComponent(new DetailedTemplateSettingsComponent(this));
    }

    public void changeName(String name) {
        int selectedTemplateIndex = templateSettingsComponent.getTemplateListComponent().getSelectedTemplateIndex();
        Template selectedTemplate = templatesModel.get(selectedTemplateIndex);
        Template template = new Template(name, selectedTemplate.getVariables(), selectedTemplate.getTemplateText());
        templatesModel.set(selectedTemplateIndex, template);
    }

    public void addVariable() {
        int selectedTemplateIndex = templateSettingsComponent.getTemplateListComponent().getSelectedTemplateIndex();
        Template template = templatesModel.get(selectedTemplateIndex);
        template.getVariables().add(new Variable());
        updateDetailedTemplateSettingsComponent(selectedTemplateIndex);
//        TODO: gibt's einen Weg, die Tabelle von Variablen an die ModelDaten zu knüpfen?
//        Ja, dafür muss aber das Model aus der Component gezogen werden(siehe das to-do in der component)
    }

    public void removeVariable() {
        int selectedTemplateIndex = templateSettingsComponent.getTemplateListComponent().getSelectedTemplateIndex();
        int selectedVariableIndex = templateSettingsComponent.getDetailedTemplateSettingsComponent().getSelectedVariableIndex();
        Template template = templatesModel.get(selectedTemplateIndex);
        template.getVariables().remove(selectedVariableIndex);
        updateDetailedTemplateSettingsComponent(selectedTemplateIndex);
//        TODO: gibt's einen Weg, die Tabelle von Variablen an die ModelDaten zu knüpfen?
//        Ja, dafür muss aber das Model aus der Component gezogen werden(siehe das to-do in der component)
    }

    public void editVariable(int selectedRow, int selectedColumn, String newValue) {
        int selectedTemplateIndex = templateSettingsComponent.getTemplateListComponent().getSelectedTemplateIndex();
        Template template = templatesModel.get(selectedTemplateIndex);
        switch (selectedColumn){
            case 0:
                template.getVariables().get(selectedRow).setName(newValue);
                break;
            case 1:
                template.getVariables().get(selectedRow).setType(VarType.valueOf(newValue));
                break;
            case 2:
                template.getVariables().get(selectedRow).setDescription(newValue);
                break;
        }
    }

    public void changeTemplateText(String templateText) {
        int selectedTemplateIndex = templateSettingsComponent.getTemplateListComponent().getSelectedTemplateIndex();
        Template selectedTemplate = templatesModel.get(selectedTemplateIndex);
        Template template = new Template(selectedTemplate.getName(), selectedTemplate.getVariables(), templateText);
        templatesModel.set(selectedTemplateIndex, template);
    }

//    ##################################################################################################################

    public void updateDetailedTemplateSettingsComponent(int index) {
        TemplateSettingsState.getInstance().setSelectedTemplateIndex(index);
        JBSplitter jbSplitter = templateSettingsComponent.getJbSplitter();
        DetailedTemplateSettingsComponent detailedTemplateSettingsComponent = (DetailedTemplateSettingsComponent) jbSplitter.getSecondComponent();
        if (detailedTemplateSettingsComponent.getComponentVisibility()) {
            detailedTemplateSettingsComponent.setComponentVisibility(true);
        }
        detailedTemplateSettingsComponent.removeEditor();
        detailedTemplateSettingsComponent.setTemplate(templatesModel.get(index));
    }

    public DefaultListModel<Template> getTemplatesModel() {
        return templatesModel;
    }
}
