package de.hsrm.mi.ba.plugin.extensions.template.model;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class VariablesTableModel extends AbstractTableModel implements Serializable, Iterable<Variable>{
    private ArrayList<Variable> variables = new ArrayList<>();

    public VariablesTableModel(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public VariablesTableModel() {
    }

    @Override
    public int getRowCount() {
        return variables.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Variable rowData = variables.get(rowIndex);
        // Hier sollten Sie den Zugriff auf die Daten entsprechend der Spaltennummer ändern
        switch (columnIndex) {
            case 0:
                return rowData.getName();
            case 1:
                return rowData.getTyp();
            case 2:
                return rowData.getDescription();
            default:
                return null;
        }
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public void add(Variable newVar) {
        variables.add(newVar);
        fireTableStructureChanged();
        fireTableRowsInserted(variables.size() - 1, variables.size() - 1);
    }

    public void remove(int rowIndex) {
        variables.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariablesTableModel variables1 = (VariablesTableModel) o;
        return Objects.equals(variables, variables1.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variables);
    }

    @NotNull
    @Override
    public Iterator<Variable> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Variable> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < variables.size();
        }

        @Override
        public Variable next() {
            return variables.get(currentIndex++);
        }
    }
}

