package codes.meruhz.storages.md5.chat.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.md5.chat.BaseComponentStorages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorage extends AbstractStorage<BaseComponent[], Locale> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    public void unload() {
        super.getJsonContent().setConfiguration(BaseComponentStorages.getInstance().getStorageSerializer().serialize(this), true);
    }
}
