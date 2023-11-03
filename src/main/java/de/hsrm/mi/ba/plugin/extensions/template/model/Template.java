package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Template implements Serializable {
    private String name;
    private String templateText;
    private final ArrayList<Variable> variables = new ArrayList<>();

    public Template() {
//        leerer Konstruktor, damit es serializable ist
    }

    public Template(String name) {
        this.name = name;
    }

    public Template(String name, String templateText) {
        this.name = name;
        this.templateText = templateText;
    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(name, template.name) && Objects.equals(templateText, template.templateText) && Objects.equals(variables, template.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, templateText, variables);
    }
}
