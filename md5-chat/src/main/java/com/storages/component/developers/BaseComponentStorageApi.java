package com.storages.component.developers;

import com.storages.component.data.BaseComponentMessage;
import com.storages.component.data.BaseComponentStorage;
import com.storages.component.serializers.BaseComponentStorageSerializer;
import com.storages.core.StoragesCore;
import com.storages.core.data.Storage;
import com.storages.core.developers.provider.StorageApiProvider;
import com.storages.core.serializer.Serializer;
import com.storages.core.utils.configuration.JsonConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class BaseComponentStorageApi extends StorageApiProvider<BaseComponent[]> {

    public BaseComponentStorageApi() {
        super(new BaseComponentStorageSerializer());
    }

    public BaseComponentStorageApi(@NotNull Serializer<Storage<BaseComponent[]>> serializer) {
        super(serializer);
    }

    @Override
    public @NotNull BaseComponentStorage createStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        @NotNull Path filePath = StoragesCore.getStorages().toPath().resolve(name.replace(" ", "_") + ".json");

        if(Files.exists(filePath)) {

            if(super.getStorage(name).isPresent()) {
                throw new IllegalStateException("Storage '" + name + "' already is loaded");

            } else {
                return (BaseComponentStorage) super.getSerializer().deserialize(JsonConfiguration.getFromFile(new File(String.valueOf(filePath))));
            }
        }

        @NotNull BaseComponentStorage storage = new BaseComponentStorage(name, defaultLocale);
        super.getStorages().add(storage);

        return storage;
    }

    @Override
    public @NotNull BaseComponentMessage createMessage(@NotNull Storage<BaseComponent[]> storage, @NotNull String id) {
        if(storage.getMessage(id).isPresent()) {
            throw new NullPointerException("An message with id '" + id + "' already exists at storage '" + storage.getName() + "'");
        }

        return new BaseComponentMessage(storage, id);
    }

    @Override
    public void load() {
        if(super.isLoaded()) {
            throw new IllegalStateException("MeruhzStorages API is already loaded");
        }

        @NotNull File dataFolder = StoragesCore.getStorages();

        if(!dataFolder.isDirectory() && !dataFolder.mkdirs()) {
            throw new RuntimeException("Folder '" + dataFolder.getAbsolutePath() + "' could not be created");
        }

        int success = 0, errors = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataFolder.toPath())) {

            for(Path path : stream) {

                try {
                    super.getSerializer().deserialize(JsonConfiguration.getFromFile(path.toFile()));
                    success++;

                } catch (Throwable throwable) {
                    StoragesCore.getLogger().severe("Failed to load storage from file '" + path.toFile().getName() + "'");
                    throwable.printStackTrace();
                    errors++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading storage files", e);
        }

        StoragesCore.getLogger().info("Successfully loaded " + success + " storage(s) with " + errors + " error(s)");
        super.loaded = true;
    }
}
