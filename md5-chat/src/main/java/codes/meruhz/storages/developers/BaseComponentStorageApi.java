package codes.meruhz.storages.developers;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.StoragesCore;
import codes.meruhz.storages.data.BaseComponentMessage;
import codes.meruhz.storages.data.BaseComponentStorage;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.developers.provider.AbstractStorageApi;
import codes.meruhz.storages.serializer.Serializer;
import codes.meruhz.storages.serializers.BaseComponentStorageSerializer;
import codes.meruhz.storages.utils.configuration.AbstractConfiguration;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseComponentStorageApi extends AbstractStorageApi<BaseComponent[], Locale> {

    public BaseComponentStorageApi() {
        super(new BaseComponentStorageSerializer());
    }

    public BaseComponentStorageApi(@NotNull Serializer<Storage<BaseComponent[], Locale>> serializer) {
        super(serializer);
    }

    @Override
    public @NotNull BaseComponentStorage createStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        @NotNull Path filePath = StoragesCore.getStorages().toPath().resolve(name.replace(" ", "_") + ".json");

        if(Files.exists(filePath)) {

            if(super.getStorage(name).isPresent()) {
                throw new IllegalStateException("Storage '" + name + "' already is loaded");

            } else try {
                return (BaseComponentStorage) super.getSerializer().deserialize(JsonParser.parseString(AbstractConfiguration.getFileContent(filePath.toFile())));

            } catch (IOException e) {
                throw new RuntimeException("Failed to load storage from file: " + filePath.toFile().getName(), e);
            }
        }

        @NotNull BaseComponentStorage storage = new BaseComponentStorage(name, defaultLocale);
        super.getStorages().add(storage);

        return storage;
    }

    @Override
    public @NotNull BaseComponentMessage createMessage(@NotNull Storage<BaseComponent[], Locale> storage, @NotNull String id) {
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

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataFolder.toPath())) {

            for(Path path : stream) {

                try {
                    super.getSerializer().deserialize(JsonParser.parseString(AbstractConfiguration.getFileContent(path.toFile())));

                } catch (IOException e) {
                    StoragesCore.getLogger().severe("Failed to load storage from file '" + path.toFile().getName() + "'");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading storage files", e);
        }

        super.loaded = true;
    }
}
