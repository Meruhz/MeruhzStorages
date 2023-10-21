package codes.meruhz.storages.core.utils;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class Loader {

    private volatile boolean loaded = false;

    @Blocking
    public final void load() throws Throwable {
        if(this.isLoaded()) {
            throw new IllegalStateException("Unable to start when it's already started");
        }

        this.start().get(this.getTimeout().toMillis(), TimeUnit.MICROSECONDS);
        this.loaded = true;
    }

    @Blocking
    public final void unload() throws Throwable {
        this.stop().get(this.getTimeout().toMillis(), TimeUnit.MILLISECONDS);
        this.loaded = false;
    }

    public final boolean isLoaded() {
        return this.loaded;
    }

    public @NotNull Duration getTimeout() {
        return Duration.of(3, ChronoUnit.SECONDS);
    }

    @ApiStatus.OverrideOnly
    protected abstract @NotNull CompletableFuture<Void> start() throws Throwable;

    @ApiStatus.OverrideOnly
    protected abstract @NotNull CompletableFuture<Void> stop() throws Throwable;
}
