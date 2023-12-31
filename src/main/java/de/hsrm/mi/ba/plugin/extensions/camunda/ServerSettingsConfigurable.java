package de.hsrm.mi.ba.plugin.extensions.camunda;

import com.intellij.openapi.options.Configurable;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.CamundaClient;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.OperateClient;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.dto.BpmnDefinitionsDTO;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.dto.ProcessDefinitionsInfoDTO;
import de.hsrm.mi.ba.plugin.extensions.camunda.model.ServerSettings;
import de.hsrm.mi.ba.plugin.extensions.camunda.model.ServerSettingsState;
import de.hsrm.mi.ba.plugin.extensions.camunda.ui.ServerSettingsComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public class ServerSettingsConfigurable implements Configurable {

    private ServerSettings settings;
    private ServerSettingsComponent serverSettingsComponent;

    @Override
    public String getDisplayName() {
        return "Server Configuration";
    }

    @Override
    @Nullable
    public JComponent createComponent() {
        settings = Objects.requireNonNull(ServerSettingsState.getInstance().getState()).getSettings();
        serverSettingsComponent = new ServerSettingsComponent(this);
        serverSettingsComponent.setData(settings);
        return serverSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        serverSettingsComponent.getData(settings);
        ServerSettingsState settings = ServerSettingsState.getInstance().getState();
        assert settings != null;
        return !this.settings.equals(settings.getSettings());
    }

    @Override
    public void apply() {
        ServerSettingsState templateSettingsState = ServerSettingsState.getInstance();
        templateSettingsState.setSettings(settings);
    }

    @Override
    public void reset() {
        settings = ServerSettingsState.getInstance().getSettings();
        serverSettingsComponent.setData(settings);
    }

//    ##################################################################################################################
//    ########################################## Operations ############################################################
//    ##################################################################################################################

    public void testConnection() {
        serverSettingsComponent.getData(settings);
        CamundaClient client;
        switch (settings.getService()) {
            case OPERATE:
                client = new OperateClient();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + settings.getService());
        }
        if (!client.testConnection(settings)){
            JOptionPane.showMessageDialog(null, client.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Connection successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            ProcessDefinitionsInfoDTO processDefinitionsInfoDTO = client.getProcessDefinitions();
            for (ProcessDefinitionsInfoDTO.ProcessDefinition pd : client.getProcessDefinitions().getProcessDefinitions()) {
                BpmnDefinitionsDTO process = client.getProcess(pd.getKey());

                System.out.println(process);
            }
        }
    }
}
