package codes.meruhz.storages.api;

import codes.meruhz.storages.api.data.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MeruhzStorages {

    private static final @NotNull MeruhzStorages INSTANCE = new MeruhzStorages();

    private final @NotNull Set<@NotNull Storage<?>> storages = new HashSet<>();

    public @NotNull Set<@NotNull Storage<?>> getStorages() {
        return this.storages;
    }

    public @NotNull Storage<?> getStorage(@NotNull String name) {
        return this.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst().orElseThrow(() -> new NullPointerException("Storage '" + name + "' could not be found"));
    }

    public static @NotNull MeruhzStorages getInstance() {
        return MeruhzStorages.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
