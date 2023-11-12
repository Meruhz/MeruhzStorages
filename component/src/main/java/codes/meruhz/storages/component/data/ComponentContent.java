package codes.meruhz.storages.component.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.data.impl.AbstractContent;
import codes.meruhz.storages.component.utils.ComponentUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ComponentContent extends AbstractContent<BaseComponent[]> {

    public ComponentContent(@NotNull ComponentMessage message, @NotNull Locale locale, BaseComponent @NotNull [] text) {
        super(message, locale, text);
    }

    public ComponentContent(@NotNull ComponentMessage message, @NotNull Locale locale, @NotNull List<BaseComponent @NotNull []> arrayText) {
        super(message, locale, arrayText);
    }

    @Override
    public BaseComponent @NotNull [] replace(Object @NotNull [] replaces) {
        return ComponentUtils.replace(replaces, super.getText());
    }
}
