package codes.meruhz.storages.core.data;

import codes.meruhz.storages.core.LocaleEnum;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public interface LocalizedMessage {

    @NotNull Message getMessage();

    @NotNull LocaleEnum getLocale();

    @NotNull BaseComponent @NotNull [] getContent();
}
