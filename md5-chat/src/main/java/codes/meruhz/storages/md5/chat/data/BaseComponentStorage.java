package codes.meruhz.storages.md5.chat.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.StoragesCore;
import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.core.utils.configuration.AbstractConfiguration;
import codes.meruhz.storages.md5.chat.BaseComponentStorages;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class BaseComponentStorage extends AbstractStorage<BaseComponent[], Locale> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale, boolean autoLoad) {
        super(name, defaultLocale);

        if(autoLoad) {
            this.load();
        }
    }

    @Override
    public @NotNull CompletableFuture<Void> load() {
        if(this.isLoaded()) {
            throw new IllegalStateException("Storage '" + this.getName() + "' already is loaded");
        }

        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            @NotNull File directory = StoragesCore.getCore().getStoragesDirectory();

            if(!directory.isDirectory() && !directory.mkdirs()) {
                throw new RuntimeException("Directory '" + directory.getAbsolutePath() + "' could not be created");
            }

            try (Stream<Path> stream = Files.list(directory.toPath()).filter(path -> path.getFileName().toString().equals(this.getJsonContent().getName()))) {
                @NotNull Optional<Path> optionalPath = stream.findFirst();

                if(optionalPath.isPresent() && Files.exists(optionalPath.get())) {
                    super.getJsonContent().setConfiguration(JsonParser.parseString(AbstractConfiguration.getFileContent(optionalPath.get().toFile())), true);
                }

                completableFuture.complete(null);
                super.loaded = true;

            } catch (IOException e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(5, TimeUnit.SECONDS);
    }

    @Override
    public @NotNull CompletableFuture<Void> unload() {
        if(!this.isLoaded()) {
            throw new IllegalStateException("Storage '" + this.getName() + "' is not loaded");
        }

        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                super.getJsonContent().setConfiguration(BaseComponentStorages.getInstance().getStorageSerializer().serialize(this), true);
                completableFuture.complete(null);
                super.loaded = false;

            } catch (Throwable throwable) {
                completableFuture.completeExceptionally(throwable);
            }
        });

        return completableFuture.orTimeout(3, TimeUnit.SECONDS);
    }
}
