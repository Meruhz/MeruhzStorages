package codes.meruhz.storages.string.data;

import codes.meruhz.storages.core.data.impl.AbstractStorage;
import codes.meruhz.storages.string.StringStorages;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class StringStorage extends AbstractStorage<String, Locale> {

    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    public void unload() {
        super.getJsonContent().setConfiguration(StringStorages.getInstance().getStorageSerializer().serialize(this), true);
    }
}
