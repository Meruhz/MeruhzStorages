package codes.meruhz.storages.md5.chat.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.md5.chat.BaseComponentStorages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BaseComponentStorage extends AbstractStorage<BaseComponent[], Locale> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale, boolean autoLoad) {
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
