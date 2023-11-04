package codes.meruhz.storages.core;

import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializer.Serializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

public abstract class StoragesCore<M, L> {

    private static @NotNull File STORAGES_SOURCE = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages" + File.separator);

    private @Nullable Serializer<Storage<M, L>> serializer;

    public static @NotNull File getSource() {
        return StoragesCore.STORAGES_SOURCE;
    }

    public static void setSource(@NotNull File source) {
        StoragesCore.STORAGES_SOURCE = source;
    }

    public @NotNull Serializer<Storage<M, L>> getStorageSerializer() {
        return Optional.ofNullable(this.serializer).orElseThrow(() -> new NullPointerException("Storage serializer is not initialized"));
    }

    public void setStorageSerializer(@NotNull Serializer<Storage<M, L>> storageSerializer) {
        this.serializer = storageSerializer;
    }

    public static void main(String[] args) {
        System.out.println("You are using MeruhzStorages core :)");
    }
}
