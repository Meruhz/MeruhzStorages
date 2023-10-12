package codes.meruhz.storages.compatibility.providers;

import codes.laivy.mlanguage.api.bungee.components.BaseComponentMessage;
import codes.laivy.mlanguage.api.bungee.components.BaseComponentMessageStorage;
import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.compatibility.LanguageAdapter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class LvMultiplesLanguagesAdapter extends LanguageAdapter<BaseComponentMessageStorage<BaseComponentMessage>> {

    public LvMultiplesLanguagesAdapter() {
        this(new LinkedHashSet<>());
    }

    public LvMultiplesLanguagesAdapter(@NotNull Set<@NotNull BaseComponentMessageStorage<BaseComponentMessage>> storages) {
        super(storages);

        if(!LanguageAdapter.hasLvMultiplesLanguages()) {
            throw new UnsupportedOperationException("Plugin LvMultiplesLanguages is not present");
        }
    }

    @Override
    public @NotNull Optional<BaseComponentMessageStorage<BaseComponentMessage>> getStorage(@NotNull String name) {
        return super.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        for(BaseComponentMessageStorage<BaseComponentMessage> storage : super.getStorages()) {
            return storage.getText(locale, id, replaces);
        }

        throw new NullPointerException("Could not be found a message with id '" + id + "'");
    }
}
