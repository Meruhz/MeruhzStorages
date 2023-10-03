package codes.meruhz.storages.core.data;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface LocalizedMessage {

    @NotNull Message getMessage();

    @NotNull Locale getLocale();

    @NotNull BaseComponent @NotNull [] getContent();
}
