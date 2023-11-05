package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;
import java.util.Objects;

public class Variable implements Serializable {
    private String name;
    private String description;
    private Enum<VarTyp> typ;

    public Variable() {
//        leerer Konstruktor, damit es serializable ist
        typ = VarTyp.STRING;
    }

    public Variable(String name, String description) {
        this.name = name;
        this.description = description;
        this.typ = VarTyp.STRING;
    }

    public Variable(String s) {
        name = s;
        typ = VarTyp.STRING;
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

    public Enum<VarTyp> getTyp() {
        return typ;
    }

    public void setTyp(Enum<VarTyp> typ) {
        this.typ = typ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name) && Objects.equals(description, variable.description) && Objects.equals(typ, variable.typ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, typ);
    }
}
