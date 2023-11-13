package de.hsrm.mi.ba.plugin.extensions.camunda.client;

import de.hsrm.mi.ba.plugin.extensions.camunda.model.ServerSettings;

public interface CamundaClient {

    boolean testConnection(ServerSettings settings);
    void getProcessDefinitions();
    String getErrorMessage();
}
