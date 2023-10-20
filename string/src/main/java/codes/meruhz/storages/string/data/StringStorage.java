package codes.meruhz.storages.string.data;

import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.string.StringStorages;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StringStorage extends AbstractStorage<String, Locale> {

    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale, boolean autoLoad) {
        super(name, defaultLocale);

        if(autoLoad) {
            this.load();
        }
    }

    @Override
    public @NotNull CompletableFuture<Void> unload() {
        if(!this.isLoaded()) {
            throw new IllegalStateException("Storage '" + this.getName() + "' is not loaded");
        }

        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                super.getJsonContent().setConfiguration(StringStorages.getInstance().getStorageSerializer().serialize(this), true);
                completableFuture.complete(null);
                super.loaded = false;

            } catch (Throwable throwable) {
                completableFuture.completeExceptionally(throwable);
            }
        });

        return completableFuture.orTimeout(3, TimeUnit.SECONDS);
    }
}
