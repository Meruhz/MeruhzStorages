package codes.meruhz.storages.string.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.impl.AbstractMessage;
import codes.meruhz.storages.api.utils.ContentUtils;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StringMessage extends AbstractMessage<String> {

    public StringMessage(@NotNull StringStorage storage, @NotNull String id) {
        super(storage, id);
    }

    @Override
    public @NotNull List<@NotNull String> getArrayText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return super.getContentsMap().get(locale).getAsArrayText().stream().map(text -> ContentUtils.replace(text, replaces)).collect(Collectors.toCollection(LinkedList::new));

        } catch (NullPointerException ex) {
            try {
                return super.getContentsMap().get(super.getStorage().getDefaultLocale()).getAsArrayText().stream().map(text -> ContentUtils.replace(text, replaces)).collect(Collectors.toCollection(LinkedList::new));

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale '" + locale + "' and the default locale '" + super.getStorage().getDefaultLocale() + "' for message '" + super.getId() + "' at storage '" + super.getStorage().getName() + "'");
            }
        }
    }
}
