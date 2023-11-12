package codes.meruhz.storages.component.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.impl.AbstractMessage;
import codes.meruhz.storages.component.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentMessage extends AbstractMessage<BaseComponent[]> {

    public ComponentMessage(@NotNull ComponentStorage storage, @NotNull String id) {
        super(storage, id);
    }

    @Override
    public @NotNull List<BaseComponent @NotNull []> getArrayText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return super.getContentsMap().get(locale).getAsArrayText().stream().map(text -> ComponentUtils.replace(replaces, text)).collect(Collectors.toCollection(LinkedList::new));

        } catch (NullPointerException ex) {
            try {
                return super.getContentsMap().get(super.getStorage().getDefaultLocale()).getAsArrayText().stream().map(text -> ComponentUtils.replace(replaces, text)).collect(Collectors.toCollection(LinkedList::new));

            } catch (NullPointerException e) {
                throw new RuntimeException("Could not find the specified locale '" + locale + "' and the default locale '" + super.getStorage().getDefaultLocale() + "' for message '" + super.getId() + "' at storage '" + super.getStorage().getName() + "'");
            }
        }
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, Object @NotNull [] replaces) {
        return ComponentUtils.getText(super.getText(locale, replaces));
    }

    public @NotNull List<@NotNull String> getLegacyArray(@NotNull Locale locale, Object @NotNull [] replaces) {
        return ComponentUtils.getArrayText(this.getArrayText(locale, replaces));
    }
}
