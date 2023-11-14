package de.hsrm.mi.ba.plugin.extensions.camunda.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpmnDefinitionsDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("targetNamespace")
    private String targetNamespace;

    @JsonProperty("exporter")
    private String exporter;

    @JsonProperty("exporterVersion")
    private String exporterVersion;

    @JsonProperty("executionPlatform")
    private String executionPlatform;

    @JsonProperty("executionPlatformVersion")
    private String executionPlatformVersion;

    @JsonProperty("process")
    private BpmnProcessDTO process;

    @Override
    public String toString() {
        return "BpmnDefinitionsDTO{" +
                "id='" + id + '\'' +
                ", targetNamespace='" + targetNamespace + '\'' +
                ", exporter='" + exporter + '\'' +
                ", exporterVersion='" + exporterVersion + '\'' +
                ", executionPlatform='" + executionPlatform + '\'' +
                ", executionPlatformVersion='" + executionPlatformVersion + '\'' +
                ", process=" + process +
                '}';
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetNamespace() {
        return targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }

    public String getExporterVersion() {
        return exporterVersion;
    }

    public void setExporterVersion(String exporterVersion) {
        this.exporterVersion = exporterVersion;
    }

    public String getExecutionPlatform() {
        return executionPlatform;
    }

    public void setExecutionPlatform(String executionPlatform) {
        this.executionPlatform = executionPlatform;
    }

    public String getExecutionPlatformVersion() {
        return executionPlatformVersion;
    }

    public void setExecutionPlatformVersion(String executionPlatformVersion) {
        this.executionPlatformVersion = executionPlatformVersion;
    }

    public BpmnProcessDTO getProcess() {
        return process;
    }

    public void setProcess(BpmnProcessDTO process) {
        this.process = process;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BpmnProcessDTO {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("isExecutable")
        private boolean isExecutable;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("startEvent")
        private List<BpmnStartEventDTO> startEvent;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("endEvent")
        private List<BpmnEndEventDTO> endEvent;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("serviceTask")
        private List<BpmnServiceTaskDTO> serviceTasks;

        @Override
        public String toString() {
            return "BpmnProcessDTO{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", isExecutable=" + isExecutable +
                    ", startEvent=" + startEvent +
                    ", endEvent=" + endEvent +
                    ", serviceTasks=" + serviceTasks +
                    '}';
        }

        // getters and setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isExecutable() {
            return isExecutable;
        }

        public void setExecutable(boolean executable) {
            isExecutable = executable;
        }

        public List<BpmnStartEventDTO> getStartEvent() {
            return startEvent;
        }

        public void setStartEvent(List<BpmnStartEventDTO> startEvent) {
            this.startEvent = startEvent;
        }

        public List<BpmnEndEventDTO> getEndEvent() {
            return endEvent;
        }

        public void setEndEvent(List<BpmnEndEventDTO> endEvent) {
            this.endEvent = endEvent;
        }

        public List<BpmnServiceTaskDTO> getServiceTasks() {
            return serviceTasks;
        }

        public void setServiceTasks(List<BpmnServiceTaskDTO> serviceTasks) {
            this.serviceTasks = serviceTasks;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BpmnStartEventDTO {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @Override
        public String toString() {
            return "BpmnStartEventDTO{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        // getters and setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BpmnEndEventDTO {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @Override
        public String toString() {
            return "BpmnEndEventDTO{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        // getters and setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BpmnServiceTaskDTO {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("taskDefinition")
        private ZeebeTaskDefinitionDTO taskDefinition;

        @Override
        public String toString() {
            return "BpmnServiceTaskDTO{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", taskDefinition=" + taskDefinition +
                    '}';
        }

        // getters and setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ZeebeTaskDefinitionDTO getTaskDefinition() {
            return taskDefinition;
        }

        public void setTaskDefinition(ZeebeTaskDefinitionDTO taskDefinition) {
            this.taskDefinition = taskDefinition;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ZeebeTaskDefinitionDTO {
        @JsonProperty("type")
        private String type;

        @Override
        public String toString() {
            return "ZeebeTaskDefinitionDTO{" +
                    "type='" + type + '\'' +
                    '}';
        }

        // getters and setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
