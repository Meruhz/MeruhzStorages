package com.storages.legacy;

import com.storages.legacy.developers.LegacyStorageApi;
import org.jetbrains.annotations.NotNull;

public class LegacyStorages {

    private static final @NotNull LegacyStorages LEGACY_STORAGE = new LegacyStorages();

    private @NotNull LegacyStorageApi storageApi;

    public LegacyStorages() {
        this.storageApi = new LegacyStorageApi();
    }

    public @NotNull LegacyStorageApi getLegacyStorageApi() {
        return this.storageApi;
    }

    public void setLegacyStorageApi(@NotNull LegacyStorageApi storageApi) {
        this.storageApi = storageApi;
    }

    public static @NotNull LegacyStorages legacy() {
        return LegacyStorages.LEGACY_STORAGE;
    }

    public static void main(String[] args) {
        System.out.println("You are using String message storages :)");
    }
}