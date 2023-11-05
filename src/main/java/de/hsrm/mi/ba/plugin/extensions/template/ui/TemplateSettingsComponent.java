package de.hsrm.mi.ba.plugin.extensions.template.ui;

import com.intellij.ui.*;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.ui.components.DetailedTemplateSettingsComponent;
import de.hsrm.mi.ba.plugin.extensions.template.ui.components.TemplateListComponent;

import javax.swing.*;
import java.awt.*;

public class TemplateSettingsComponent {

    private final JPanel mainPanel = new JPanel();
    private final TemplateListComponent templateListComponent;
    private final DetailedTemplateSettingsComponent detailedTemplateSettingsComponent;
    private final JBSplitter jbSplitter;

    public TemplateSettingsComponent(TemplateSettingsConfigurable controller) {
        templateListComponent = new TemplateListComponent(controller);
        detailedTemplateSettingsComponent = new DetailedTemplateSettingsComponent(controller);

        // JBSplitter erstellen und Liste und Template Detail Ansicht hinzufüge
        jbSplitter = new JBSplitter(false, 0.3f);
        jbSplitter.setFirstComponent(templateListComponent);
        jbSplitter.setSecondComponent(detailedTemplateSettingsComponent);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(jbSplitter, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JBSplitter getJbSplitter() {
        return jbSplitter;
    }

    public TemplateListComponent getTemplateListComponent() {
        return templateListComponent;
    }

    public DetailedTemplateSettingsComponent getDetailedTemplateSettingsComponent() {
        return detailedTemplateSettingsComponent;
    }
}
