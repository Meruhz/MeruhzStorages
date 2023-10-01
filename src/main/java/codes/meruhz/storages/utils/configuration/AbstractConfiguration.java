package codes.meruhz.storages.utils.configuration;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class AbstractConfiguration {

    private final @NotNull File folder, file;
    private final @NotNull String name;

    protected AbstractConfiguration(@NotNull File folder, @NotNull String name) {
        this.folder = folder;
        this.name = name;
        this.getFolder().mkdirs();
        this.file = new File(folder, name);
    }

    public @NotNull File getFolder() {
        return this.folder;
    }

    public @NotNull File getFile() {
        return this.file;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public abstract void load();
}
