package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Template implements Serializable {
    private String name;
    private String templateText;
    private final VariablesTableModel variables = new VariablesTableModel(new ArrayList<>(Arrays.asList(new Variable("B"), new Variable("A"))));

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

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public VariablesTableModel getVariables() {
        return variables;
    }

    public void setVariables(VariablesTableModel variables) {
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        for (int i = 0; i < variables.getRowCount(); i++) {
            variableArrayList.add(new Variable((String) variables.getValueAt(i, 0), (String) variables.getValueAt(i, 2)));
        }
        this.variables.setVariables(variableArrayList);
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
