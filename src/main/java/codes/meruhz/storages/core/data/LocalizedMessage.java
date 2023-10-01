package codes.meruhz.storages.core.data;

import codes.meruhz.storages.core.LocaleEnum;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface LocalizedMessage {

    @NotNull Message getMessage();

    @NotNull LocaleEnum getLocale();

    @NotNull BaseComponent[] getContent();

    @NotNull BaseComponent[] replace(@NotNull Object... replaces);

    @NotNull List<@NotNull Object> getReplacements();
}
