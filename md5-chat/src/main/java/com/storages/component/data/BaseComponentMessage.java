package com.storages.component.data;

import com.storages.component.BaseComponentStorages;
import com.storages.component.utils.ComponentUtils;
import com.storages.core.data.Storage;
import com.storages.core.data.providers.MessageProvider;
import com.storages.core.utils.configuration.JsonConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BaseComponentMessage extends MessageProvider<BaseComponent[]> {

    public BaseComponentMessage(@NotNull Storage<BaseComponent[]> storage, @NotNull String id) {
        super(storage, id);
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
        return ComponentUtils.getText(this.getText(locale));
    }
}
