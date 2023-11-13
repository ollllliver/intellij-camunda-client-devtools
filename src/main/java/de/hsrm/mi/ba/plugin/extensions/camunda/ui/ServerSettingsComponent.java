package de.hsrm.mi.ba.plugin.extensions.camunda.ui;

import com.intellij.ui.PortField;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import de.hsrm.mi.ba.plugin.extensions.camunda.model.ServerSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerSettingsComponent {
    private JBCheckBox activatedCheckBox;
    private JBCheckBox sslCheckBox;
    private JBTextField hostTextField;
    private PortField portField;
    private JBCheckBox authCheckBox;
    private JBTextField userTextField;
    private JBPasswordField passwordField;
    private JPanel mainPanel;
    private JLabel hostLabel;
    private JLabel poetLabel;
    private JLabel userLabel;
    private JLabel passwordLabel;

    public ServerSettingsComponent() {
        activatedCheckBox.addActionListener(e -> updateEdibility());
        authCheckBox.addActionListener(e -> updateEdibility());
    }

    private void updateEdibility() {
        hostTextField.setEditable(activatedCheckBox.isSelected());
        sslCheckBox.setEnabled(activatedCheckBox.isSelected());
        portField.setEnabled(activatedCheckBox.isSelected());
        authCheckBox.setEnabled(activatedCheckBox.isSelected());
        hostLabel.setEnabled(activatedCheckBox.isSelected());
        poetLabel.setEnabled(activatedCheckBox.isSelected());

        userTextField.setEditable(authCheckBox.isSelected()&&activatedCheckBox.isSelected());
        passwordField.setEditable(authCheckBox.isSelected()&&activatedCheckBox.isSelected());
        userLabel.setEnabled(authCheckBox.isSelected()&&activatedCheckBox.isSelected());
        passwordLabel.setEnabled(authCheckBox.isSelected()&&activatedCheckBox.isSelected());
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void setData(ServerSettings data) {
        activatedCheckBox.setSelected(data.isActivated());
        sslCheckBox.setSelected(data.isSsl());
        hostTextField.setText(data.getHost());
        portField.setNumber(data.getPort());
        userTextField.setText(data.getUser());
        passwordField.setText(data.getPassword());
        authCheckBox.setSelected(data.isAuth());
    }

    public void getData(ServerSettings data) {
        data.setActivated(activatedCheckBox.isSelected());
        data.setSsl(sslCheckBox.isSelected());
        data.setHost(hostTextField.getText());
        data.setPort(portField.getNumber());
        data.setUser(userTextField.getText());
        data.setPassword(new String(passwordField.getPassword()));
        data.setAuth(authCheckBox.isSelected());
    }

}
