package codes.meruhz.storages.compatibility.providers;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.compatibility.LanguageAdapter;
import codes.meruhz.storages.data.BaseComponentStorage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MeruhzStoragesAdapter extends LanguageAdapter<BaseComponentStorage> {

    @Override
    public @NotNull Optional<BaseComponentStorage> getStorage(@NotNull String name) {
        return super.getStorages().stream().filter(storage -> storage.getName().equals(name)).findFirst();
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        for(BaseComponentStorage storage : super.getStorages()) {
            return storage.getText(locale, id, replaces);
        }

        throw new NullPointerException("Could not be found a message with id '" + id + "'");
    }
}
