package codes.meruhz.storages.api.utils;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

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
    public final void load() {
        if(this.isLoaded()) {
            throw new NullPointerException("Task '" + this.getId() + "' already is started");
        }

        this.start();
        this.loaded = true;
    }

    @Blocking
    public final void unload() {
        if(!this.isLoaded()) {
            throw new NullPointerException("Task '" + this.getId() + "' is not started");
        }

        this.stop();
        this.loaded = false;
    }

    @ApiStatus.OverrideOnly
    protected abstract void start();

    @ApiStatus.OverrideOnly
    protected abstract void stop();
}
