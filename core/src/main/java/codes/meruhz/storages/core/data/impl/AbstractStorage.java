package codes.meruhz.storages.core.data.impl;

import codes.meruhz.storages.core.StoragesCore;
import codes.meruhz.storages.core.data.Message;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.utils.Loader;
import codes.meruhz.storages.core.utils.configuration.AbstractConfiguration;
import codes.meruhz.storages.core.utils.configuration.JsonConfiguration;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public abstract class AbstractStorage<M, L> extends Loader implements Storage<M, L> {

    private final @NotNull String name;
    private final @NotNull L defaultLocale;
    private final @NotNull JsonConfiguration jsonContent;
    private final @NotNull Set<@NotNull Message<M, L>> messages;

    public AbstractStorage(@NotNull String name, @NotNull L defaultLocale) {
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = new LinkedHashSet<>();
        this.jsonContent = new JsonConfiguration(StoragesCore.getCore().getStoragesDirectory(), name);
    }

    public @NotNull JsonConfiguration getJsonContent() {
        return this.jsonContent;
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
    protected @NotNull CompletableFuture<Void> start() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            @NotNull File directory = StoragesCore.getCore().getStoragesDirectory();

            if(!directory.isDirectory() && !directory.mkdirs()) {
                throw new RuntimeException("Directory '" + directory.getAbsolutePath() + "' could not be created");
            }

            try (@NotNull Stream<Path> stream = Files.list(directory.toPath()).filter(path -> path.getFileName().toString().equals(this.getJsonContent().getName()))) {
                @NotNull Optional<Path> optionalPath = stream.findFirst();

                if(optionalPath.isPresent() && Files.exists(optionalPath.get())) {
                    this.getJsonContent().setConfiguration(JsonParser.parseString(AbstractConfiguration.getFileContent(optionalPath.get().toFile())), true);
                }

                completableFuture.complete(null);

            } catch (IOException e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(Duration.of(20, ChronoUnit.SECONDS).toMillis(), TimeUnit.MILLISECONDS);
    }
}
