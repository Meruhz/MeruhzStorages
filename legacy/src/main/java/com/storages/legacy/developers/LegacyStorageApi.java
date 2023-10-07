package com.storages.legacy.developers;

import com.storages.core.data.Storage;
import com.storages.core.developers.provider.StorageApiProvider;
import com.storages.core.serializer.Serializer;
import com.storages.core.utils.configuration.JsonConfiguration;
import com.storages.legacy.data.LegacyMessage;
import com.storages.legacy.data.LegacyStorage;
import com.storages.legacy.serializers.LegacyStorageSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class LegacyStorageApi extends StorageApiProvider<String> {

    public LegacyStorageApi() {
        super(new LegacyStorageSerializer());
    }

    public LegacyStorageApi(@NotNull Serializer<Storage<String>> serializer) {
        super(serializer);
    }

    @Override
    public @NotNull LegacyStorage createStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        @NotNull File target = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "legacy storages");
        @NotNull Path filePath = target.toPath().resolve(name.replace(" ", "_") + ".json");

        if(Files.exists(filePath)) {

            if(super.getStorage(name).isPresent()) {
                throw new IllegalStateException("Storage '" + name + "' already is loaded");

            } else {
                super.getStorages().add(super.getSerializer().deserialize(JsonConfiguration.getFromFile(new File(String.valueOf(filePath)))));
            }
        }

        @NotNull LegacyStorage storage = new LegacyStorage(name, defaultLocale);
        storage.setJsonContent(new JsonConfiguration(target, name));

        this.getStorages().add(storage);
        return storage;
    }

    @Override
    public @NotNull LegacyMessage createMessage(@NotNull Storage<String> storage, @NotNull String id) {
        if(storage.getMessage(id).isPresent()) {
            throw new NullPointerException("An message with id '" + id + "' already exists at storage '" + storage.getName() + "'");
        }

        return new LegacyMessage((LegacyStorage) storage, id);
    }

    @Override
    public void load() {
        if(this.isLoaded()) {
            throw new IllegalStateException("MeruhzStorages API already is loaded");
        }

        @NotNull File target = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "legacy storages");
        if(Files.isDirectory(target.toPath())) {

            try (@NotNull DirectoryStream<Path> stream = Files.newDirectoryStream(target.toPath())) {

                for(Path path : stream) {
                    this.getStorages().add(this.getSerializer().deserialize(JsonConfiguration.getFromFile(path.toFile())));
                }

            } catch (IOException e) {
                throw new RuntimeException("An error occurred while reading storage files", e);
            }
        }

        super.loaded = true;
    }
}
