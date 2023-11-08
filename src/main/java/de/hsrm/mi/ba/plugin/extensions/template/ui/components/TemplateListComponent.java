package de.hsrm.mi.ba.plugin.extensions.template.ui.components;

import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleListCellRenderer;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TemplateListComponent extends JPanel{
    private final JBList<Template> myTemplatesList = new JBList<>();

    public TemplateListComponent(TemplateSettingsConfigurable controller) {
        myTemplatesList.setModel(controller.getTemplatesModel());
        try {
            myTemplatesList.setSelectedIndex(TemplateSettingsState.getInstance().getSelectedTemplateIndex());
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        myTemplatesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myTemplatesList.setCellRenderer(new SimpleListCellRenderer<>() {

            @Override
            public void customize(@NotNull JList<? extends Template> jList, Template template, int i, boolean b, boolean b1) {
                this.setText(template.getName());
            }
        });
        myTemplatesList.addListSelectionListener(e -> {
            if (!myTemplatesList.isSelectionEmpty()) {
                controller.updateDetailedTemplateSettingsComponent(myTemplatesList.getSelectedIndex());
            }
        });

        // Liste mit einem CommonActionsPanel erstellen
        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(myTemplatesList)
                .setScrollPaneBorder(JBUI.Borders.empty())
                .setPanelBorder(JBUI.Borders.customLine(JBColor.border(), 1, 1, 0, 1))
                .setAddAction((__) -> controller.addTemplate())
                .setRemoveAction((__) -> controller.removeTemplate(myTemplatesList.getSelectedIndex()))
                .disableUpDownActions();
        setLayout(new BorderLayout());
        add(decorator.createPanel());
    }

    public void setTemplates(DefaultListModel<Template> templatesModel) {
        myTemplatesList.setModel(templatesModel);
        try {
            myTemplatesList.setSelectedIndex(TemplateSettingsState.getInstance().getSelectedTemplateIndex());
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public void selectElementAtIndex(int index) {
        myTemplatesList.setSelectedIndex(index);
    }

    public int getSelectedTemplateIndex() {
        return myTemplatesList.getSelectedIndex();
    }
}
