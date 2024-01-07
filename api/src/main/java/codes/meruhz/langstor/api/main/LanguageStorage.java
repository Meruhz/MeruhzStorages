package codes.meruhz.langstor.api.main;

import codes.meruhz.langstor.api.LanguageApi;
import codes.meruhz.langstor.api.MessageStorage;
import codes.meruhz.langstor.api.MessageUtils;
import codes.meruhz.langstor.api.utils.Loader;
import codes.meruhz.langstor.api.utils.model.AbstractConfiguration;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public final class LanguageStorage extends Loader {

    private static @Nullable LanguageApi LANGUAGE_API;
    private static @Nullable MessageUtils<?> MESSAGE_UTILS;
    private static @Nullable File STORAGES_FOLDER;

    public LanguageStorage(@NotNull LanguageApi languageApi, @NotNull MessageUtils<?> messageUtils) {
        super("Language Storage Thread");
        LanguageStorage.LANGUAGE_API = languageApi;
        LanguageStorage.MESSAGE_UTILS = messageUtils;
        LanguageStorage.STORAGES_FOLDER = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "languages" + File.separator);
    }

    public static @NotNull LanguageApi getLanguageApi() {
        return Optional.ofNullable(LanguageStorage.LANGUAGE_API).orElseThrow(() -> new NullPointerException("Language API is not initialized"));
    }

    public static synchronized void setLanguageApi(@NotNull LanguageApi languageApi) {
        LanguageStorage.LANGUAGE_API = languageApi;
    }

    public static @NotNull File getStorageFolder() {
        return Optional.ofNullable(LanguageStorage.STORAGES_FOLDER).orElseThrow(() -> new NullPointerException("Storages folder is not initialized"));
    }

    public static void setStorageFolder(@NotNull File storagesFolder) {
        LanguageStorage.STORAGES_FOLDER = storagesFolder;
    }

    public static @NotNull MessageUtils<?> getMessageUtils() {
        return Optional.ofNullable(LanguageStorage.MESSAGE_UTILS).orElseThrow(() -> new NullPointerException("Message controller is not initialized"));
    }

    public static void setMessageUtils(@NotNull MessageUtils<?> messageUtils) {
        LanguageStorage.MESSAGE_UTILS = messageUtils;
    }

    @Override
    protected @NotNull CompletableFuture<Void> start() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {

                if(LanguageStorage.getStorageFolder().exists()) {
                    @NotNull Stream<Path> files = Files.list(Paths.get(LanguageStorage.getStorageFolder().toURI()));

                    files.forEach(path -> {
                        @NotNull File file = new File(path.toUri());

                        if(file.getName().endsWith(".json") && file.length() != 0) {

                            try {
                                LanguageStorage.getLanguageApi().getStorages().add(LanguageStorage.getLanguageApi().deserialize(JsonParser.parseString(AbstractConfiguration.getFileContent(file))));

                            } catch (IOException e) {
                                throw new RuntimeException("Failed to load storage folder", e);
                            }
                        }

                    });

                    files.close();
                }

                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(10, TimeUnit.SECONDS);
    }

    @Override
    protected @NotNull CompletableFuture<Void> stop() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                for(MessageStorage<?> storage : LanguageStorage.getLanguageApi().getStorages()) {

                    if(!LanguageStorage.getStorageFolder().isDirectory() && !LanguageStorage.getStorageFolder().mkdirs()) {
                        throw new RuntimeException("Storages main folder could not be created");
                    }

                    storage.getJsonModel().setContent(LanguageStorage.getLanguageApi().serialize(storage), true);
                }

                LanguageStorage.getLanguageApi().getStorages().clear();

                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(8, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}