package com.storages.legacy.data;

import com.storages.core.data.providers.StorageProvider;
import com.storages.legacy.LegacyStorages;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class LegacyStorage extends StorageProvider<String> {

    public LegacyStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale, Type.LEGACY);
    }

    @Override
    public void save() {
        this.getJsonContent().setConfiguration(LegacyStorages.legacy().getLegacyStorageApi().getSerializer().serialize(this), true);
    }
}
