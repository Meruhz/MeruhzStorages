package com.storages.component.adapters;

import com.storages.component.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public abstract class LanguageAdapter<S, L> {

    private final @NotNull Set<@NotNull S> storages;

    public LanguageAdapter() {
        this(new LinkedHashSet<>());
    }

    public LanguageAdapter(@NotNull Set<@NotNull S> storages) {
        this.storages = storages;
    }

    public @NotNull Set<@NotNull S> getStorages() {
        return this.storages;
    }

    public abstract @NotNull Optional<S> getStorage(@NotNull String name);

    public abstract @NotNull BaseComponent @NotNull [] getText(@NotNull L locale, @NotNull String id, @NotNull Object... replaces);

    public @NotNull String getLegacyText(@NotNull L locale, @NotNull String id, @NotNull Object... replaces) {
        return ComponentUtils.getText(this.getText(locale, id, replaces));
    }

    public static boolean hasLvMultiplesLanguages() {
        try {
            Class.forName("codes.laivy.mlanguage.api.bukkit.IBukkitMultiplesLanguagesAPI");
            Class.forName("codes.laivy.mlanguage.main.BukkitMultiplesLanguages");
            return true;

        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
