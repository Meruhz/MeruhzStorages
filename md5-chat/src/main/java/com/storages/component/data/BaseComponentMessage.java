package com.storages.component.data;

import codes.laivy.mlanguage.lang.Locale;
import com.storages.component.utils.ComponentUtils;
import com.storages.core.data.Storage;
import com.storages.core.data.providers.AbstractMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class BaseComponentMessage extends AbstractMessage<BaseComponent[], Locale> {

    public BaseComponentMessage(@NotNull Storage<BaseComponent[], Locale> storage, @NotNull String id) {
        super(storage, id);
    }

    @Override
    public @NotNull Locale @NotNull [] getLocales() {
        return super.getContents().keySet().toArray(new Locale[0]);
    }

    @Override
    public @NotNull BaseComponent @NotNull [] replace(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        @NotNull String serialized = ComponentUtils.serialize(super.getText(locale));

        for(Object replace : replaces) {
            serialized = serialized.replaceFirst("%s", replace.toString());
        }

        return ComponentSerializer.parse(serialized);
    }

    public @NotNull String getLegacyText(@NotNull Locale locale) {
        return ComponentUtils.getText(super.getText(locale));
    }
}
