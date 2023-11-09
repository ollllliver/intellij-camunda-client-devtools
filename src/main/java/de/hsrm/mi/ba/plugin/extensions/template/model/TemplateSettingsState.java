package de.hsrm.mi.ba.plugin.extensions.template.model;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.OptionTag;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

@State(
        name = "de.hsrm.mi.ba.plugin.extensions.template.TemplateSettingsState",
        storages = @Storage("templateSettingsState.xml")
)
public class TemplateSettingsState implements PersistentStateComponent<TemplateSettingsState> {

//    Nach außen wird das Template dieses States nur als DefaultListModel gezeigt (siehe getter und setter).
//    Hier drinnen wird es aber als ArrayList behandelt, damit es als ein serialisierbares Objekt von der IntelliJ API persistable ist

    @OptionTag(converter = TemplateSettingsStateConverter.class)
    private ArrayList<Template> templates = new ArrayList<>();
    private int selectedTemplateIndex = -1;

    public static TemplateSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(TemplateSettingsState.class);
    }

    @Nullable
    @Override
    public TemplateSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TemplateSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    /**
     * @return a DefaultListModel<Template> with deep copied Objects, so you don't change the State by changing this listModel.
     *
     * To change State, pls use setTemplates() methode.
     */
    public ArrayList<Template> getTemplates() {
        ArrayList<Template> templatesModel = new ArrayList<>();
        for (Template template : templates) {
            templatesModel.add(SerializationUtils.clone(template));
        }
        return templatesModel;
    }

    /**
     * @param templates that should be persisted
     *
     * makes a deepCopy before setting the Templates for the state, so the passed templates don't change the state accidentally
     */
    public void setTemplates(ArrayList<Template> templates) {
        ArrayList<Template> arrayList = new ArrayList<>();
        for (Template template : templates) {
            arrayList.add(SerializationUtils.clone(template));
        }
        this.templates = arrayList;
    }

    /**
     * @return index of selectedTemplateIndex. Should be used to save index of last selected Template.
     */
    public int getSelectedTemplateIndex() {
        return selectedTemplateIndex;
    }

    /**
     * @param selectedTemplateIndex should be used to save index of last selected Template.
     */
    public void setSelectedTemplateIndex(int selectedTemplateIndex) {
        this.selectedTemplateIndex = selectedTemplateIndex;
    }
}
