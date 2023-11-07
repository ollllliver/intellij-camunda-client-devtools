package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;
import java.util.Objects;

public class Variable implements Serializable {
    private String name = "";
    private Enum<VarType> type = VarType.STRING;
    private String description = "";

    public Variable() {
    }

    public Variable(String name, Enum<VarType> type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enum<VarType> getType() {
        return type;
    }

    public void setType(Enum<VarType> type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name) && Objects.equals(type, variable.type) && Objects.equals(description, variable.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, description);
    }
}
