package com.storages.component.adapters.mzstr;

import com.storages.component.adapters.LanguageAdapter;
import com.storages.component.data.BaseComponentStorage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

public class MeruhzStoragesAdapter extends LanguageAdapter<BaseComponentStorage, Locale> {

    @Override
    public @NotNull Optional<BaseComponentStorage> getStorage(@NotNull String name) {
        return super.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        for(BaseComponentStorage storage : super.getStorages()) {
            return storage.getText(locale, id, replaces);
        }

        throw new NullPointerException("Could not be found a message with id '" + id + "'");
    }
}
