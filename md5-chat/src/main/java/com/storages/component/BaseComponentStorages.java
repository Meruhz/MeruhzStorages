package com.storages.component;

import com.storages.component.developers.BaseComponentStorageApi;
import org.jetbrains.annotations.NotNull;

public final class BaseComponentStorages {

    private static final @NotNull BaseComponentStorages COMPONENT_STORAGE = new BaseComponentStorages();

    private @NotNull BaseComponentStorageApi storageApi;

    public BaseComponentStorages() {
        this.storageApi = new BaseComponentStorageApi();
    }

    public @NotNull BaseComponentStorageApi getBaseComponentStorageApi() {
        return this.storageApi;
    }

    public void setBaseComponentStorageApi(@NotNull BaseComponentStorageApi storageApi) {
        this.storageApi = storageApi;
    }

    public static @NotNull BaseComponentStorages component() {
        return BaseComponentStorages.COMPONENT_STORAGE;
    }

    public static void main(String[] args) {
        System.out.println("You are using BaseComponent message storages :)");
    }
}