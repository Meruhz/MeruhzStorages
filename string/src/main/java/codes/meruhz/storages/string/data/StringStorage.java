package codes.meruhz.storages.string.data;

import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.string.StringStorages;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StringStorage extends AbstractStorage<String, Locale> {

    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    @Override
    protected @NotNull CompletableFuture<Void> stop() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                super.getJsonContent().setConfiguration(StringStorages.getInstance().getStorageSerializer().serialize(this), true);
                completableFuture.complete(null);

            } catch (Throwable throwable) {
                completableFuture.completeExceptionally(throwable);
            }
        });

        return completableFuture.orTimeout(Duration.of(20, ChronoUnit.SECONDS).toMillis(), TimeUnit.MILLISECONDS);
    }
}
