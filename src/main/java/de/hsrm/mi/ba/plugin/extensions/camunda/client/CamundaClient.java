package de.hsrm.mi.ba.plugin.extensions.camunda.client;

import de.hsrm.mi.ba.plugin.extensions.camunda.client.dto.BpmnDefinitionsDTO;
import de.hsrm.mi.ba.plugin.extensions.camunda.client.dto.ProcessDefinitionsInfoDTO;
import de.hsrm.mi.ba.plugin.extensions.camunda.model.ServerSettings;

public interface CamundaClient {

    boolean testConnection(ServerSettings settings);
    ProcessDefinitionsInfoDTO getProcessDefinitions();
    BpmnDefinitionsDTO getProcess(long key);
    String getErrorMessage();
}
