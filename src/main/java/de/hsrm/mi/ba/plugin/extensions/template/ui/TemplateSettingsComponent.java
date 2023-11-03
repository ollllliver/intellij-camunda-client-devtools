package de.hsrm.mi.ba.plugin.extensions.template.ui;

import com.intellij.ui.*;
import com.intellij.ui.components.JBList;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.ui.components.DetailedTemplateSettingsComponent;
import de.hsrm.mi.ba.plugin.extensions.template.ui.components.TemplateListComponent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TemplateSettingsComponent {

    private final JPanel mainPanel = new JPanel();
    private final JBList<Template> myTemplatesList = new JBList<>();
    private final JBSplitter jbSplitter;

    public TemplateSettingsComponent(TemplateSettingsConfigurable controller) {
        myTemplatesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myTemplatesList.setCellRenderer(new SimpleListCellRenderer<>() {

            @Override
            public void customize(@NotNull JList<? extends Template> jList, Template template, int i, boolean b, boolean b1) {
                this.setText(template.getName());
            }
        });
        myTemplatesList.addListSelectionListener(e -> {
            if (!myTemplatesList.isSelectionEmpty()) {
                controller.showDetailedTemplateSettingsOf(myTemplatesList.getSelectedValue());
            }
        });

        // JBSplitter erstellen und Liste hinzufüge
        jbSplitter = new JBSplitter(false, 0.3f);
        jbSplitter.setFirstComponent(new TemplateListComponent(controller, myTemplatesList));
        jbSplitter.setSecondComponent(new DetailedTemplateSettingsComponent()); // Zweite Komponente leer lassen

        // Füge die Liste und das InfoLabel zum Hauptpanel hinzu
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(jbSplitter, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void setTemplates(DefaultListModel<Template> templatesModel) {
        myTemplatesList.setModel(templatesModel);
        myTemplatesList.setSelectedIndex(0);
    }

    public JBSplitter getJbSplitter() {
        return jbSplitter;
    }

    public void selectElementAtIndex(int index) {
        myTemplatesList.setSelectedIndex(index);
    }

}
