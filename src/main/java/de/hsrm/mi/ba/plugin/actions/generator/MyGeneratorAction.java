package de.hsrm.mi.ba.plugin.actions.generator;

import com.hubspot.jinjava.Jinjava;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import de.hsrm.mi.ba.plugin.actions.generator.ui.CodeGenerationDialog;
import de.hsrm.mi.ba.plugin.actions.generator.ui.inputcomponents.InputComponent;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MyGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        BaseListPopupStep<Template> step = new BaseListPopupStep<>("Choose Template", TemplateSettingsState.getInstance().getTemplates()) {
            private Template template;
            @Override
            public PopupStep onChosen(Template template, boolean finalChoice) {
                this.template = template;
                return FINAL_CHOICE;
            }

            @Override
            public Runnable getFinalRunnable() {
                return () -> {
                    System.out.println("lets go");

                    Jinjava jinjava;
                    ClassLoader curClassLoader = Thread.currentThread().getContextClassLoader();
                    try {
                        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
                        jinjava = new Jinjava();
                    } finally {
                        Thread.currentThread().setContextClassLoader(curClassLoader);
                    }

                    CodeGenerationDialog templateDialog = new CodeGenerationDialog(template.getVariables());
                    templateDialog.showAndGet();

                    Map<String, InputComponent> inputFieldMap = templateDialog.getInputFieldMap();
                    Map<String, String> stringMap = new HashMap<>();
                    for (Map.Entry<String, InputComponent> entry : inputFieldMap.entrySet()) {
                        String key = entry.getKey();
                        InputComponent inputComponent = entry.getValue();
                        String value = inputComponent.getInputValue();
                        stringMap.put(key, value);
                    }

                    String generatedText = jinjava.render(template.getTemplateText(), stringMap);
                    System.out.println(generatedText);
                };
            }

            @NotNull
            @Override
            public String getTextFor(Template value) {
                return value.getName();
            }
        };

        JBPopupFactory.getInstance()
                .createListPopup(step)
                .showInBestPositionFor(anActionEvent.getDataContext());

    }
}
