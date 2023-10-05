package codes.meruhz.storages.developers.providers;

import codes.meruhz.storages.data.Message;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.data.providers.MessageProvider;
import codes.meruhz.storages.data.providers.StorageProvider;
import codes.meruhz.storages.developers.StorageApi;
import codes.meruhz.storages.serializers.Serializer;
import codes.meruhz.storages.serializers.providers.StorageSerializerProvider;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class StorageApiProvider implements StorageApi {

    private final @NotNull Set<@NotNull Storage> storages = new HashSet<>();

    @Override
    public @NotNull Set<@NotNull Storage> getStorages() {
        return this.storages;
    }

    @Override
    public @NotNull Storage createStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Message... messages) {
        if(this.getStorage(name).isPresent()) {
            throw new IllegalArgumentException("An storage named '" + name + "' already exists");
        }

        @NotNull File target = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages");

        if(Files.exists(target.toPath().resolve(name + ".json"))) {
            throw new IllegalArgumentException("An storage named '" + name + "' already exists on the folder '" + target.getAbsolutePath() + "'");
        }

        @NotNull StorageProvider storage = new StorageProvider(name, defaultLocale, messages);
        storage.setJson(new JsonConfiguration(target, name));

        this.getStorages().add(storage);
        return storage;
    }

    @Override
    public @NotNull Message createMessage(@NotNull Storage storage, @NotNull String id) {
        storage.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().ifPresent(ex -> {
            throw new NullPointerException("An message with id '" + id + "' already exists at storage '" + storage.getName() + "'");
        });

        return new MessageProvider(storage, id);
    }

    public static class StorageLoader {

        private final @NotNull StorageApi storageApi;
        private final @NotNull Serializer<Storage> storageSerializer;

        public StorageLoader() {
            this(new StorageApiProvider(), new StorageSerializerProvider());
        }

        public StorageLoader(@NotNull StorageApi storageApi, @NotNull Serializer<Storage> storageSerializer) {
            this.storageApi = storageApi;
            this.storageSerializer = storageSerializer;
        }

        public @NotNull Serializer<Storage> getSerializer() {
            return this.storageSerializer;
        }

        public @NotNull StorageApi getApi() {
            return this.storageApi;
        }

        public boolean isLoaded() {
            return !this.getApi().getStorages().isEmpty();
        }

        public void unload() {
            if(!this.isLoaded()) {
                throw new IllegalStateException("MeruhzStorages core is not loaded");
            }

            for(Storage storage : this.getApi().getStorages()) {
                storage.save();
            }

            this.getApi().getStorages().clear();
        }

        public void load() {
            if(this.isLoaded()) {
                throw new IllegalStateException("MeruhzStorages core already is loaded");
            }

            @NotNull File target = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages");
            if(Files.isDirectory(target.toPath())) {

                try (@NotNull DirectoryStream<Path> stream = Files.newDirectoryStream(target.toPath())) {

                    for(Path path : stream) {
                        this.getApi().getStorages().add(this.getSerializer().deserialize(JsonConfiguration.getFromFile(path.toFile())));
                    }

                } catch (IOException e) {
                    throw new RuntimeException("An error occurred while reading storage files: " + e.getMessage(), e);
                }
            }
        }
    }
}
