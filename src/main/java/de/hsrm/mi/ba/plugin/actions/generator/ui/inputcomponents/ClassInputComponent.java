package de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents;

import com.intellij.openapi.project.Project;
import com.intellij.ui.TextFieldWithAutoCompletion;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassInputComponent extends TextFieldWithAutoCompletion<String> implements InputComponentInterface {

    public ClassInputComponent(Project project) {
        super(project, new ClassLookupProvider(), false, null);
    }

    @Override
    public String getInputValue() {
        return getText();
    }

    private static class ClassLookupProvider extends TextFieldWithAutoCompletion.StringsCompletionProvider {
        ClassLookupProvider() {
            super(new ArrayList<>(Arrays.asList("Affe", "Apfel", "Applaus", "Aprikose", "Apple")), null);
//            TODO: Klassen aus dem Projekt anbieten
        }
    }

}
