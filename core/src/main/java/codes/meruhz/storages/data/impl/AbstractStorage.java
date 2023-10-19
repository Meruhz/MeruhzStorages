package codes.meruhz.storages.data.impl;

import codes.meruhz.storages.StoragesCore;
import codes.meruhz.storages.data.Message;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.utils.configuration.AbstractConfiguration;
import codes.meruhz.storages.utils.configuration.providers.JsonConfiguration;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public abstract class AbstractStorage<M, L> implements Storage<M, L> {

    private final @NotNull String name;
    private final @NotNull L defaultLocale;
    private final @NotNull Set<@NotNull Message<M, L>> messages;

    private @NotNull JsonConfiguration jsonContent;

    private boolean loaded;

    public AbstractStorage(@NotNull String name, @NotNull L defaultLocale) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>();
        this.jsonContent = new JsonConfiguration(StoragesCore.getCore().getStoragesDirectory(), name);
    }

    public @NotNull JsonConfiguration getJsonContent() {
        return this.jsonContent;
    }

    public void setJsonContent(@NotNull JsonConfiguration jsonContent) {
        this.jsonContent = jsonContent;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull L getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Set<@NotNull Message<M, L>> getMessages() {
        return this.messages;
    }

    @Override
    public @NotNull M getText(@NotNull L locale, @NotNull String id, @NotNull Object... replaces) {
        @NotNull Optional<Message<M, L>> optionalMessage = this.getMessage(id);

        if(!optionalMessage.isPresent()) {
            throw new NullPointerException("Could not be found a message with id '" + id + "' at storage '" + this.getName() + "'");
        }

        @NotNull AbstractMessage<M, L> message = (AbstractMessage<M, L>) optionalMessage.get();

        if(!message.getContents().containsKey(locale)) {
            throw new NullPointerException("Could not be found the specified locale from message '" + id + "' at storage '" + this.getName() + "'");
        }

        return message.replace(locale, replaces);
    }

    @Override
    public boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public @NotNull CompletableFuture<Void> load() {
        if(this.isLoaded()) {
            throw new IllegalStateException("Storage '" + this.getName() + "' already is loaded");
        }

        return CompletableFuture.runAsync(() -> {
            @NotNull File directory = StoragesCore.getCore().getStoragesDirectory();

            if(!directory.isDirectory() && !directory.mkdirs()) {
                throw new RuntimeException("Directory '" + directory.getAbsolutePath() + "' could not be created");
            }

            try(Stream<Path> stream = Files.list(directory.toPath()).filter(path -> path.getFileName().toString().equals(this.getJsonContent().getName()))) {
                @NotNull Optional<Path> optionalPath = stream.findFirst();

                if(!optionalPath.isPresent()) {
                    throw new NullPointerException("Storage '" + this.getJsonContent().getName() + "' on directory '" + directory.getAbsolutePath() + "'");
                }

                StoragesCore.getCore().getStorageSerializer().deserialize(JsonParser.parseString(AbstractConfiguration.getFileContent(optionalPath.get().toFile())));
                this.loaded = true;

            } catch (IOException e) {
                StoragesCore.getLogger().severe("Failed to load storage '" + this.getName() + "'");
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<Void> unload() {
        if(!this.isLoaded()) {
            throw new IllegalStateException("Storage '" + this.getName() + "' is not loaded");
        }

        return CompletableFuture.runAsync(() -> {
            try {
                this.save();
                this.loaded = false;

            } catch (Throwable throwable) {
                StoragesCore.getLogger().severe("Failed to unload storage '" + this.getName() + "'");
                throw new RuntimeException(throwable);
            }
        });
    }

    public abstract void save();
}
