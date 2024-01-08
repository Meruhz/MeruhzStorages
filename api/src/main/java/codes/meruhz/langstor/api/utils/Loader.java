package codes.meruhz.langstor.api.utils;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * This abstract class provides a base implementation for creating loaders
 * that perform asynchronous loading and unloading tasks.
 */
public abstract class Loader {

    private final @NotNull String id;
    private volatile boolean loaded;

    /**
     * Constructs a Loader with the specified identifier.
     *
     * @param id The unique identifier for the loader.
     */
    public Loader(@NotNull String id) {
        this.id = id;
    }

    /**
     * Retrieves the identifier of the loader.
     *
     * @return The identifier of the loader.
     */
    public final @NotNull String getId() {
        return this.id;
    }

    /**
     * Checks if the loader is currently loaded.
     *
     * @return {@code true} if the loader is loaded, {@code false} otherwise.
     */
    public final boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Asynchronously loads the resources or performs the required initialization.
     *
     * @return A CompletableFuture representing the loading task.
     */
    @Blocking
    public final @NotNull CompletableFuture<Void> load() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                if (this.isLoaded()) {
                    throw new NullPointerException("Task '" + this.getId() + "' is already started");
                }

                this.start().join();
                this.loaded = true;
                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(5, TimeUnit.SECONDS);
    }

    /**
     * Asynchronously unloads the resources or performs the required cleanup.
     *
     * @return A CompletableFuture representing the unloading task.
     */
    @Blocking
    public final @NotNull CompletableFuture<Void> unload() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                if (!this.isLoaded()) {
                    throw new NullPointerException("Task '" + this.getId() + "' is not started");
                }

                this.stop().join();
                this.loaded = false;
                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture.orTimeout(10, TimeUnit.SECONDS);
    }

    /**
     * This method is called during the asynchronous loading process.
     * Subclasses should provide their loading logic in this method.
     *
     * @return A CompletableFuture representing the start task.
     */
    @ApiStatus.OverrideOnly
    protected abstract @NotNull CompletableFuture<Void> start();

    /**
     * This method is called during the asynchronous unloading process.
     * Subclasses should provide their unloading logic in this method.
     *
     * @return A CompletableFuture representing the stop task.
     */
    @ApiStatus.OverrideOnly
    protected abstract @NotNull CompletableFuture<Void> stop();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loader loader = (Loader) o;

        return id.equals(loader.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
