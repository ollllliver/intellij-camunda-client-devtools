package de.hsrm.mi.ba.plugin.extensions.template.ui.components;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.JBColor;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import de.hsrm.mi.ba.plugin.extensions.template.model.VarType;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class DetailedTemplateSettingsComponent extends JPanel {

    private final JBTextField templateName = new JBTextField(20);
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "Typ", "Description"}, 0); //TODO: Model muss aus der View raus!
    private final JBTable table = new JBTable(tableModel);
    private final JBTextArea templateText = new JBTextArea();

    public DetailedTemplateSettingsComponent(TemplateSettingsConfigurable controller) {
        boolean aTemplateIsSelected = false;
        DefaultListModel<Template> templatesModel = controller.getTemplatesModel();
        if (!templatesModel.isEmpty()) {
            int selectedTemplateIndex = TemplateSettingsState.getInstance().getSelectedTemplateIndex();
            Template selectedTemplate;
            if (selectedTemplateIndex<0){
                selectedTemplate = templatesModel.get(0);
            } else {
                aTemplateIsSelected = true;
                selectedTemplate = templatesModel.get(selectedTemplateIndex);
            }
            setTemplate(selectedTemplate);
        }

        DefaultCellEditor comboBoxCellEditor = createComboBoxCellEditor(controller);
        DefaultCellEditor textFieldCellEditor = createTextFieldCellEditor(controller);

        table.getColumnModel().getColumn(0).setCellEditor(textFieldCellEditor);
        table.getColumnModel().getColumn(1).setCellEditor(comboBoxCellEditor);
        table.getColumnModel().getColumn(2).setCellEditor(textFieldCellEditor);

        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(table)
                .setScrollPaneBorder(JBUI.Borders.empty())
                .setPanelBorder(JBUI.Borders.customLine(JBColor.border(), 1, 1, 0, 1))
                .setAddAction((__) -> controller.addVariable())
                .setRemoveAction((__) -> controller.removeVariable())
                .disableUpDownActions();
        setLayout(new BorderLayout());
        JPanel variablesTablePanel = decorator.createPanel();

        templateName.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                controller.changeName(templateName.getText());
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JPanel labeledTextField = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Name:");
        labeledTextField.add(label);
        labeledTextField.add(templateName);

        add(labeledTextField, gbc);

        templateText.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                controller.changeTemplateText(templateText.getText());
            }
        });

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(variablesTablePanel, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        add(templateText, gbc);

        setComponentVisibility(aTemplateIsSelected);
    }

    private DefaultCellEditor createComboBoxCellEditor(TemplateSettingsConfigurable controller) {
        String[] dropdownOptions = Arrays.stream(VarType.values()).map(VarType::toString).toArray(String[]::new);
        DefaultCellEditor comboBoxCellEditor = new DefaultCellEditor(new ComboBox<>(dropdownOptions)){
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                table.setRowSelectionInterval(row,row); //set selected row, so in the CellEditorListener getting the selected row is possible
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }
        };
        comboBoxCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                int selectedRow = table.getSelectedRow();
                controller.editVariable(selectedRow, 1, table.getValueAt(selectedRow, 1).toString());
                table.setRowSelectionInterval(selectedRow, selectedRow); //set focus back on row, not on dropdown editor
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
        return comboBoxCellEditor;
    }

    @NotNull
    private DefaultCellEditor createTextFieldCellEditor(TemplateSettingsConfigurable controller) {
        DefaultCellEditor textFieldCellEditor = new DefaultCellEditor(new JBTextField());
        textFieldCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                int selectedColumn = table.getSelectedColumn();
                int selectedRow = table.getSelectedRow();
                controller.editVariable(selectedRow, selectedColumn, table.getValueAt(selectedRow, selectedColumn).toString());
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
        return textFieldCellEditor;
    }

    public void setTemplate(Template template) {
        templateName.setText(template.getName());
        templateText.setText(template.getTemplateText());
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        for (Variable var : template.getVariables()) {
            tableModel.addRow(new String[]{var.getName(), var.getType().toString(), var.getDescription()});
        }
        setComponentVisibility(true);
    }

    public void setComponentVisibility(boolean aTemplateIsSelected) {
        for (Component component :
                getComponents()) {
            component.setVisible(aTemplateIsSelected);
        }
    }

    public boolean getComponentVisibility() {
        for (Component component : getComponents()) {
            if (!component.isVisible()) {
                return false;
            }
        }
        return true;
    }

    public void removeEditor(){
        table.removeEditor();
    }

    public int getSelectedVariableIndex() {
        return table.getSelectedRow();
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
