package de.hsrm.mi.ba.plugin.extensions;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainConfigurable implements Configurable {

    @Nls
    @Override
    public String getDisplayName() {
        return "Camunda-Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        JPanel mainPanel = new JPanel();
        JLabel infoLabel = new JLabel("Info: Dies ist die Konfigurationsseite des Camunda-Plugins.");
        mainPanel.add(infoLabel);
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
