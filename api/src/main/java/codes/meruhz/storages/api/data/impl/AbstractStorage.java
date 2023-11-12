package codes.meruhz.storages.api.data.impl;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.Message;
import codes.meruhz.storages.api.data.Storage;
import codes.meruhz.storages.api.utils.Loader;
import codes.meruhz.storages.api.utils.configuration.JsonConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractStorage<T> extends Loader implements Storage<T> {

    private final @NotNull File source;
    private final @NotNull String name;
    private final @NotNull Locale defaultLocale;
    private final @NotNull JsonConfiguration jsonContent;
    private final @NotNull Set<@NotNull Message<T>> messages;

    public AbstractStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        this(name, defaultLocale, new LinkedHashSet<>());
    }

    public AbstractStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<T>> messages) {
        this(new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages" + File.separator), name, defaultLocale, messages);
    }

    public AbstractStorage(@NotNull File source, @NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<T>> messages) {
        super(name);
        this.source = source;
        this.name = name;
        this.defaultLocale = defaultLocale;
        this.messages = messages;
        this.jsonContent = new JsonConfiguration(this.getSource(), this.getName());
    }

    public @NotNull JsonConfiguration getJsonContent() {
        return this.jsonContent;
    }

    @Override
    public @NotNull File getSource() {
        return this.source;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public @NotNull Set<@NotNull Message<T>> getMessages() {
        return this.messages;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        AbstractStorage<?> that = (AbstractStorage<?>) o;

        if(!source.equals(that.source)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
