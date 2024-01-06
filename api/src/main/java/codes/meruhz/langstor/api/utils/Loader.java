package codes.meruhz.langstor.api.utils;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class Loader {

    private final @NotNull String id;
    private volatile boolean loaded;

    public Loader(@NotNull String id) {
        this.id = id;
    }

    public final @NotNull String getId() {
        return this.id;
    }

    public final boolean isLoaded() {
        return this.loaded;
    }

    @Blocking
    public final @NotNull CompletableFuture<Void> load() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                if(this.isLoaded()) {
                    throw new NullPointerException("Task '" + this.getId() + "' already is started");
                }

                this.start();
                this.loaded = true;
                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(5, TimeUnit.SECONDS);
    }

    @Blocking
    public final @NotNull CompletableFuture<Void> unload() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {

            try {
                if(!this.isLoaded()) {
                    throw new NullPointerException("Task '" + this.getId() + "' already is not started");
                }

                this.stop();
                this.loaded = false;
                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(5, TimeUnit.SECONDS);
    }

    @ApiStatus.OverrideOnly
    protected abstract void start();

    @ApiStatus.OverrideOnly
    protected abstract void stop();

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Loader loader = (Loader) o;

        return id.equals(loader.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
