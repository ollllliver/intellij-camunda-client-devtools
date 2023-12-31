package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Template implements Serializable {
    private String name;
    private ArrayList<Variable> variables = new ArrayList<>();
    private String templateText = "";

    public Template() {
    }

    public Template(String name) {
        this.name = name;
    }

    public Template(String name, ArrayList<Variable> variables, String templateText) {
        this.name = name;
        this.templateText = templateText;
        this.variables = variables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(name, template.name) && Objects.equals(variables, template.variables) && Objects.equals(templateText, template.templateText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, variables, templateText);
    }
}
