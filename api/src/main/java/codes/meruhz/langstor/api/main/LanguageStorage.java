package codes.meruhz.langstor.api.main;

import codes.meruhz.langstor.api.LanguageApi;
import codes.meruhz.langstor.api.MessageUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

public final class LanguageStorage {

    private static final @NotNull LanguageStorage INSTANCE = new LanguageStorage();

    private @Nullable LanguageApi languageApi;
    private @Nullable MessageUtils<?> messageUtils;
    private @NotNull File storageFolder;

    public LanguageStorage() {
        this.storageFolder = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "languages" + File.separator);
    }

    public @NotNull LanguageApi getLanguageApi() {
        return Optional.ofNullable(this.languageApi).orElseThrow(() -> new NullPointerException("Language API is not initialized"));
    }

    public synchronized void setLanguageApi(@NotNull LanguageApi languageApi) {
        this.languageApi = languageApi;
    }

    public @NotNull File getStorageFolder() {
        return this.storageFolder;
    }

    public @NotNull MessageUtils<?> getMessageUtils() {
        return Optional.ofNullable(this.messageUtils).orElseThrow(() -> new NullPointerException("Message controller is not initialized"));
    }

    public void setMessageUtils(@NotNull MessageUtils<?> messageUtils) {
        this.messageUtils = messageUtils;
    }

    public void setStorageFolder(@NotNull File storageFolder) {
        this.storageFolder = storageFolder;
    }

    public static @NotNull LanguageStorage langstor() {
        return LanguageStorage.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}