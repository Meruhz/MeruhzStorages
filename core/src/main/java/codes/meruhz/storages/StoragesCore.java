package codes.meruhz.storages;

import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.serializer.Serializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

public class StoragesCore {

    private static final @NotNull Logger LOGGER = Logger.getLogger("MeruhzStorages");

    private static @NotNull StoragesCore INSTANCE = new StoragesCore();

    private @Nullable Serializer<Storage<?, ?>> serializer;

    private @NotNull File storagesDirectory;

    public StoragesCore() {
        this.storagesDirectory = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages");
    }

    public @NotNull File getStoragesDirectory() {
        return this.storagesDirectory;
    }

    public void setStoragesDirectory(@NotNull File storagesDirectory) {
        this.storagesDirectory = storagesDirectory;
    }

    public @NotNull Serializer<Storage<?, ?>> getStorageSerializer() {
        return Optional.ofNullable(this.serializer).orElseThrow(() -> new NullPointerException("Storage serializer is not initialized"));
    }

    public void setStorageSerializer(@NotNull Serializer<Storage<?, ?>> storageSerializer) {
        this.serializer = storageSerializer;
    }

    public static @NotNull StoragesCore getCore() {
        return StoragesCore.INSTANCE;
    }

    public static void setCore(@NotNull StoragesCore instance) {
        StoragesCore.INSTANCE = instance;
    }

    public static @NotNull Logger getLogger() {
        return StoragesCore.LOGGER;
    }

    public static void main(String[] args) {
        System.out.println("You are using MeruhzStorages core :)");
    }
}
