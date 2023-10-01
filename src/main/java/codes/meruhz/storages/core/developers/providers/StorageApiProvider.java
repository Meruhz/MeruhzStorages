package codes.meruhz.storages.core.developers.providers;

import codes.meruhz.storages.MeruhzStorages;
import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.data.providers.StorageProvider;
import codes.meruhz.storages.core.developers.StorageApi;
import codes.meruhz.storages.core.serializers.Serializer;
import codes.meruhz.storages.core.serializers.providers.StorageSerializerProvider;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class StorageApiProvider implements StorageApi {

    private final @NotNull Set<@NotNull Storage> storages = new HashSet<>();
    private final @NotNull Serializer<Storage> storageSerializer;

    public StorageApiProvider() {
        this(new StorageSerializerProvider());
    }

    public StorageApiProvider(@NotNull Serializer<Storage> storageSerializer) {
        this.storageSerializer = storageSerializer;
    }

    @Override
    public @NotNull Set<@NotNull Storage> getStorages() {
        return this.storages;
    }

    @Override
    public @NotNull Serializer<Storage> getSerializer() {
        return this.storageSerializer;
    }

    @Override
    public @NotNull Storage createStorage(@NotNull String name, @NotNull LocaleEnum defaultLocale, @NotNull Message... messages) {
        @NotNull StorageProvider storage = new StorageProvider(name, defaultLocale, messages);
        @NotNull File target = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages");

        if(Files.isDirectory(target.toPath())) {

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(target.toPath())) {

                for(Path file : stream) {
                    @NotNull String fileName = file.getFileName().toString();

                    if(fileName.equals(name + ".json")) {
                        throw new IllegalArgumentException("An storage named '" + name + "' already exists on the folder '" + target.getAbsolutePath() + "'");
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        storage.setConfiguration(new JsonConfiguration(target, name));
        storage.getConfiguration().setConfiguration(MeruhzStorages.getInstance().getStorageApi().getSerializer().serialize(storage), true);

        return storage;
    }
}
