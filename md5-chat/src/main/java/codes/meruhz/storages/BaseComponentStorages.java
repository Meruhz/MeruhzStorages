package codes.meruhz.storages;

import codes.meruhz.storages.developers.BaseComponentStorageApi;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorages {

    private static final @NotNull BaseComponentStorages BASE_COMPONENT_STORAGES = new BaseComponentStorages();

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

    public static @NotNull BaseComponentStorages storages() {
        return BaseComponentStorages.BASE_COMPONENT_STORAGES;
    }

    public static void main(String[] args) {
        System.out.println("You are using BaseComponent message storages :)");
    }
}
