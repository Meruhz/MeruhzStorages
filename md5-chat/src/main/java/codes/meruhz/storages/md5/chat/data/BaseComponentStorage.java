package codes.meruhz.storages.md5.chat.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.md5.chat.BaseComponentStorages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BaseComponentStorage extends AbstractStorage<BaseComponent[], Locale> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    @Override
    protected @NotNull CompletableFuture<Void> unload() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                super.getJsonContent().setConfiguration(BaseComponentStorages.getInstance().getStorageSerializer().serialize(this), true);
                completableFuture.complete(null);

            } catch (Throwable throwable) {
                completableFuture.completeExceptionally(throwable);
            }
        });

        return completableFuture.orTimeout(Duration.of(20, ChronoUnit.SECONDS).toMillis(), TimeUnit.MILLISECONDS);
    }
}
