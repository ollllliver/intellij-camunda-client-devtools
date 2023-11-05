package de.hsrm.mi.ba.plugin.extensions.template.model;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

@State(
        name = "de.hsrm.mi.ba.plugin.extensions.template.Teeessssset",
        storages = @Storage("teeeeregeesstsssts.xml")
)
public class TemplateSettingsState implements PersistentStateComponent<TemplateSettingsState> {

//    Nach außen wird das Template dieses States nur als DefaultListModel gezeigt (siehe getter und setter).
//    Hier drinnen wird es aber als ArrayList behandelt, damit es als ein serialisierbares Objekt von der IntelliJ API persistable ist

    public ArrayList<Template> templates = new ArrayList<>();

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
    public DefaultListModel<Template> getTemplates() {
        DefaultListModel<Template> templatesModel = new DefaultListModel<>();
        for (Template template : templates) {
            templatesModel.addElement(SerializationUtils.clone(template));
        }
        return templatesModel;
    }

    public void setTemplates(DefaultListModel<Template> templates) {
        ArrayList<Template> arrayList = new ArrayList<>();
        for (int i = 0; i < templates.size(); i++) {
            arrayList.add(templates.get(i));
        }
        this.templates = arrayList;
    }
}
