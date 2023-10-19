package codes.meruhz.storages.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.StoragesCore;
import codes.meruhz.storages.data.impl.AbstractStorage;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorage extends AbstractStorage<BaseComponent[], Locale> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale, boolean autoLoad) {
        super(name, defaultLocale);

        if(autoLoad) {
            super.load();
        }
    }

    @Override
    public void save() {
        @NotNull StoragesCore<BaseComponent[], Locale> core = (StoragesCore<BaseComponent[], Locale>) StoragesCore.getCore();
        core.getStorageSerializer().serialize(this);
    }
}
