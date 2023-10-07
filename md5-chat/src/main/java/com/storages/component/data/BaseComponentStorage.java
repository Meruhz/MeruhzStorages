package com.storages.component.data;

import com.storages.component.BaseComponentStorages;
import com.storages.core.data.providers.StorageProvider;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BaseComponentStorage extends StorageProvider<BaseComponent[]> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale, Type.BASE_COMPONENT);
    }

    @Override
    public void save() {
        super.getJsonContent().setConfiguration(BaseComponentStorages.component().getBaseComponentStorageApi().getSerializer().serialize(this), true);
    }
}
