package codes.meruhz.storages.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface Loader {

    boolean isLoaded();

    @NotNull CompletableFuture<Void> load();

    @NotNull CompletableFuture<Void> unload();
}
