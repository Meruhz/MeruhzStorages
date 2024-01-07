package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.Message;
import codes.meruhz.langstor.api.providers.AbstractMessageStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Set;

public class StringStorage extends AbstractMessageStorage<String> {

    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    public StringStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<String>> messages) {
        super(name, defaultLocale, messages);
    }
}
