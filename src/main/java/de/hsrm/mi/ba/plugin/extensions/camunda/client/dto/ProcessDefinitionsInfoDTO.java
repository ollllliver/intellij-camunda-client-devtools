package de.hsrm.mi.ba.plugin.extensions.camunda.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProcessDefinitionsInfoDTO {
    @JsonAlias({"items" })
    private List<ProcessDefinition> processDefinitions;
    private List<String> sortValues;
    private int total;

    public List<ProcessDefinition> getProcessDefinitions() {
        return processDefinitions;
    }

    public void setItems(List<ProcessDefinition> processDefinitions) {
        this.processDefinitions = processDefinitions;
    }

    public List<String> getSortValues() {
        return sortValues;
    }

    public void setSortValues(List<String> sortValues) {
        this.sortValues = sortValues;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class ProcessDefinition {
        private long key;
        private String name;
        private int version;
        private String bpmnProcessId;

        public long getKey() {
            return key;
        }

        public void setKey(long key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getBpmnProcessId() {
            return bpmnProcessId;
        }

        public void setBpmnProcessId(String bpmnProcessId) {
            this.bpmnProcessId = bpmnProcessId;
        }
    }

}

