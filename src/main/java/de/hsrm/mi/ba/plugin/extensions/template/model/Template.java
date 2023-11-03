package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Template implements Serializable {
    private String name;
    private String templateText;
    private final ArrayList<Variable> variables = new ArrayList<>();

    public Template() {
//        leerer Konstruktor, damit es serializable ist
    }

    public String getName() {
        return name;
    }

    public Template(String name) {
        this.name = name;
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
}
