package de.hsrm.mi.ba.plugin.actions.generator;

import com.hubspot.jinjava.Jinjava;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import de.hsrm.mi.ba.plugin.actions.generator.ui.CodeGenerationDialog;
import de.hsrm.mi.ba.plugin.extensions.template.model.Template;
import de.hsrm.mi.ba.plugin.extensions.template.model.TemplateSettingsState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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
                    Project project = anActionEvent.getProject();
                    PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
                    if (project == null || psiFile == null) {
                        return;
                    }


                    Jinjava jinjava;
                    ClassLoader curClassLoader = Thread.currentThread().getContextClassLoader();
                    try {
                        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
                        jinjava = new Jinjava();
                    } finally {
                        Thread.currentThread().setContextClassLoader(curClassLoader);
                    }


                    CodeGenerationDialog templateDialog = new CodeGenerationDialog(anActionEvent, template.getVariables());
                    templateDialog.showAndGet();

                    Map<String, Supplier<String>> inputFieldMap = templateDialog.getInputFieldMap();
                    Map<String, String> stringMap = inputFieldMap.entrySet().stream()
                            .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue().get()), Map::putAll);
                    String generatedText = jinjava.render(template.getTemplateText(), stringMap);

//                    WriteCommandAction.runWriteCommandAction(project, () -> {
//                        PsiClass psiClass = getTargetClass(psiFile);
//
//                        if (psiClass != null) {
//                            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
//                              TODO: man braucht noch einen Kontext, was das Template ist. Ob es ein Kommentar ist oder eine Methode oder Klasse etc.
//                            PsiComment generatedMethod = elementFactory.createCommentFromText(generatedText, psiClass);
//
//                            PsiElement lastMethod = psiClass.getMethods()[psiClass.getMethods().length - 1];
//                            psiClass.addAfter(generatedMethod, lastMethod);
//                            CodeStyleManager.getInstance(project).reformat(psiClass);
//                        }
//                    });

                    System.out.println(generatedText);
                };
            }

            private PsiClass getTargetClass(PsiFile psiFile) {
                PsiClass targetClass = null;

                if (psiFile instanceof PsiJavaFile) {
                    PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
                    PsiClass[] classes = psiJavaFile.getClasses();

                    if (classes.length > 0) {
                        targetClass = classes[0];
                    }
                }

                return targetClass;
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
