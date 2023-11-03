package de.hsrm.mi.ba.plugin.extensions.template.ui.components;

import com.intellij.ui.JBColor;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;

import javax.swing.*;
import java.awt.*;

public class TemplateListComponent extends JPanel{

    public TemplateListComponent(TemplateSettingsConfigurable controller, JBList<Template> myTemplatesList) {
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


}
