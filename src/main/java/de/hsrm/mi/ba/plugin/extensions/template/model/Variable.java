package de.hsrm.mi.ba.plugin.extensions.template.model;

import java.io.Serializable;

public class Variable implements Serializable {
    private String name;
    private String description;
    private Enum<VarTyp> typ;

    public Variable() {
//        leerer Konstruktor, damit es serializable ist
    }
}
