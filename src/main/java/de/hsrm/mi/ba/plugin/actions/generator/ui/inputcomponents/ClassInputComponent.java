package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.openapi.project.Project;
import com.intellij.ui.TextFieldWithAutoCompletion;
import de.hsrm.mi.ba.plugin.extensions.template.model.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ClassInputComponent extends InputComponent {
    private final TextFieldWithAutoCompletion<String> classTextField;

    public ClassInputComponent(Variable var) {
        super(var);
        Project project = Objects.requireNonNull(com.intellij.openapi.project.ProjectManager.getInstance().getOpenProjects())[0];
        classTextField = new TextFieldWithAutoCompletion<>(project, new ClassLookupProvider(), false, null);
        add(classTextField);
    }

    @Override
    public String getInputValue() {
        return classTextField.getText();
    }

    private static class ClassLookupProvider extends TextFieldWithAutoCompletion.StringsCompletionProvider {
        ClassLookupProvider() {
            super(new ArrayList<>(Arrays.asList("Affe", "Apfel")), null);
        }
    }

}
