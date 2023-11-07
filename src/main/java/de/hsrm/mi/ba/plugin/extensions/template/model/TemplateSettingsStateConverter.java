package de.hsrm.mi.ba.plugin.extensions.template.model;

import com.intellij.util.xmlb.Converter;
import com.thoughtworks.xstream.XStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TemplateSettingsStateConverter extends Converter<ArrayList<Template>> {
    @Nullable
    @Override
    public ArrayList<Template> fromString(@NotNull String value) {
        XStream xStream2 = new XStream();
        xStream2.setClassLoader(Template.class.getClassLoader());
        return (ArrayList<Template>) xStream2.fromXML(value);
    }

    @Nullable
    @Override
    public String toString(@NotNull ArrayList<Template> value) {
        XStream xStream = new XStream();
        return xStream.toXML(value);
    }
}