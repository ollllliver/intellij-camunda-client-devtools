package de.hsrm.mi.ba.plugin.extensions.camunda.model;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "de.hsrm.mi.ba.plugin.extensions.template.ServerSettingsState",
        storages = @Storage("serverSettingsState.xml")
)
public class ServerSettingsState implements PersistentStateComponent<ServerSettingsState> {

    private ServerSettings settings = new ServerSettings();

    public static ServerSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ServerSettingsState.class);
    }

    @Override
    public @Nullable ServerSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ServerSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }


    public ServerSettings getSettings() {
        return SerializationUtils.clone(settings);
    }

    public void setSettings(ServerSettings settings) {
        this.settings = SerializationUtils.clone(settings);
    }

}
