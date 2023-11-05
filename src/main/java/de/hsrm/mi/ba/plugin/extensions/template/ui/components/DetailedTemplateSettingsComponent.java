package de.hsrm.mi.ba.plugin.extensions.template.ui.components;

import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.JBColor;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsConfigurable;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DetailedTemplateSettingsComponent extends JPanel {

    private final JBTextField templateName = new JBTextField(20);
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "Typ", "Description"}, 0);
    private final JBTable table = new JBTable(tableModel);
    private final JBTextField templateText = new JBTextField();

    public DetailedTemplateSettingsComponent(TemplateSettingsConfigurable controller) {

        Template selectedTemplate = controller.getTemplatesModel().get(0);

        templateName.setText(selectedTemplate.getName());
        templateText.setText(selectedTemplate.getTemplateText());

        for (Variable var : selectedTemplate.getVariables()) {
            tableModel.addRow(new String[]{var.getName(), var.getTyp().toString(), var.getDescription()});
        }

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

//        TODO: Tabelle kleiner machen
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JPanel labeledTextField = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Label:");
        labeledTextField.add(label);
        labeledTextField.add(templateName);

        add(labeledTextField, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 0, 0, 0); // Ändern Sie die Abstände nach Bedarf

        add(variablesTablePanel, gbc);
        add(templateText, gbc);
    }

    public void setTemplate(Template template) {
        templateName.setText(template.getName());
        templateText.setText(template.getTemplateText());
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        for (Variable var :
                template.getVariables()) {
            tableModel.addRow(new String[]{var.getName(), var.getTyp().toString(), var.getDescription()});
        }
    }

    public int getSelectedVariableIndex() {
        return table.getSelectedRow();
    }
}
