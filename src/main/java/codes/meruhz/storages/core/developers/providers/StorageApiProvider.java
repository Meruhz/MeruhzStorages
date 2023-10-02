package codes.meruhz.storages.core.developers.providers;

import codes.meruhz.storages.core.LocaleEnum;
import codes.meruhz.storages.core.data.LocalizedMessage;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.data.providers.LocalizedMessageProvider;
import codes.meruhz.storages.core.data.providers.MessageProvider;
import codes.meruhz.storages.core.data.providers.StorageProvider;
import codes.meruhz.storages.core.developers.StorageApi;
import codes.meruhz.storages.core.developers.StorageApiCore;
import codes.meruhz.storages.core.serializers.Serializer;
import codes.meruhz.storages.core.serializers.providers.StorageSerializerProvider;
import codes.meruhz.storages.utils.configuration.JsonConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StorageApiProvider implements StorageApi {

    private final @NotNull Set<@NotNull Storage> storages = new HashSet<>();

    @Override
    public @NotNull Set<@NotNull Storage> getStorages() {
        return this.storages;
    }

    @Override
    public @NotNull Storage createStorage(@NotNull String name, @NotNull LocaleEnum defaultLocale, @NotNull Message... messages) {
        if(this.getStorage(name).isPresent()) {
            throw new IllegalArgumentException("An storage named '" + name + "' already exists");
        }

        @NotNull StorageProvider storage = new StorageProvider(name, defaultLocale);
        Arrays.stream(messages).forEach(message -> storage.getMessages().add(message));

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

        storage.setJson(new JsonConfiguration(target, name));
        return storage;
    }

    @Override
    public @NotNull Message createMessage(@NotNull Storage storage, @NotNull String id) {
        storage.getMessages().stream().filter(message -> message.getId().equals(id)).findFirst().ifPresent(ex -> {
            throw new NullPointerException("An message with id '" + id + "' already exists at storage '" + storage.getName() + "'");
        });

        return new MessageProvider(storage, id);
    }

    @Override
    public @NotNull LocalizedMessage createLocalizedMessage(@NotNull Message message, @NotNull LocaleEnum locale, @NotNull BaseComponent @NotNull [] content) {
        message.getLocalizedMessages().stream().filter(lc -> lc.getMessage().equals(message) && lc.getLocale().equals(locale)).findFirst().ifPresent(lc -> {
            throw new NullPointerException("There already exists a localized message with id '" + message.getId() + "' and locale '" + locale + "' at storage '" + message.getStorage().getName() + "'");
        });

        return new LocalizedMessageProvider(message, locale, content);
    }

    public static class ApiCore implements StorageApiCore {

        private final @NotNull StorageApi storageApi;
        private final @NotNull Serializer<Storage> storageSerializer;

        public ApiCore() {
            this(new StorageApiProvider(), new StorageSerializerProvider());
        }

        public ApiCore(@NotNull StorageApi storageApi, @NotNull Serializer<Storage> storageSerializer) {
            this.storageApi = storageApi;
            this.storageSerializer = storageSerializer;
        }

        @Override
        public @NotNull Serializer<Storage> getSerializer() {
            return this.storageSerializer;
        }

        @Override
        public @NotNull StorageApi getApi() {
            return this.storageApi;
        }

        @Override
        public boolean isLoaded() {
            return !this.getApi().getStorages().isEmpty();
        }

        @Override
        public void unload() {
            if(!this.isLoaded()) {
                throw new IllegalStateException("MeruhzStorages core is not loaded");
            }

            for(Storage storage : this.getApi().getStorages()) {
                this.getSerializer().serialize(storage);
            }

            this.getApi().getStorages().clear();
        }

        @Override
        public void load() {
            if(this.isLoaded()) {
                throw new IllegalStateException("MeruhzStorages core already is loaded");
            }
        }
    }
}
