package de.hsrm.mi.ba.plugin.actions.generator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import org.jetbrains.annotations.NotNull;

public class MyGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        BaseListPopupStep<Template> step = new BaseListPopupStep<>("Choose Template", TemplateSettingsState.getInstance().getTemplates()) {
            @Override
            public PopupStep onChosen(Template selectedValue, boolean finalChoice) {
                System.out.println(selectedValue.getName());
                return FINAL_CHOICE;
            }

            @NotNull
            @Override
            public String getTextFor(Template value) {
                return value.getName();
            }
        };


        // Popup anzeigen
        JBPopupFactory.getInstance()
                .createListPopup(step)
                .showInBestPositionFor(anActionEvent.getDataContext());

    }
}
