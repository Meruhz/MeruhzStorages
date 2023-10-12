package codes.meruhz.storages.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.BaseComponentStorages;
import codes.meruhz.storages.data.providers.AbstractStorage;
import codes.meruhz.storages.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorage extends AbstractStorage<BaseComponent[], Locale> {

    public BaseComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    @Override
    public @NotNull BaseComponent @NotNull [] getText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        @NotNull BaseComponent @NotNull [] text = super.getText(locale, id, replaces);

        if(!ComponentUtils.isLegacyText(text)) {
            return ComponentSerializer.parse(ComponentUtils.getText(text));
        }

        return text;
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return ComponentUtils.getText(this.getText(locale, id, replaces));
    }

    @Override
    public void save() {
        super.getJsonContent().setConfiguration(BaseComponentStorages.storages().getBaseComponentStorageApi().getSerializer().serialize(this), true);
    }
}
