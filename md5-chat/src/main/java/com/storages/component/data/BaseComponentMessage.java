package com.storages.component.data;

import com.storages.component.utils.ComponentUtils;
import com.storages.core.data.providers.MessageProvider;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BaseComponentMessage extends MessageProvider<BaseComponent[]> {

    public BaseComponentMessage(@NotNull BaseComponentStorage storage, @NotNull String id) {
        super(storage, id);
    }

    public @NotNull String getLegacyText(@NotNull Locale locale) {
        return BaseComponent.toLegacyText(super.getText(locale));
    }

    @Override
    public @NotNull BaseComponent @NotNull [] replace(@NotNull Locale locale, @NotNull Object @NotNull [] replaces) {
        @NotNull String serialized = ComponentUtils.serialize(this.getText(locale));

        for(Object replace : replaces) {
            serialized = serialized.replaceFirst("%s", replace.toString());
        }

        return ComponentSerializer.parse(serialized);
    }
}
