package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.MessageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils implements MessageUtils<String> {

    @Override
    public @NotNull String replaceText(@NotNull String text, Object @NotNull [] replaces) {
        for(Object replace : replaces) {
            text = text.replaceFirst("%s", replace.toString());
        }

        return text;
    }

    @Override
    public @NotNull List<@NotNull String> replaceArray(@NotNull List<@NotNull String> arrayText, Object @NotNull [] replaces) {
        return arrayText.stream().map(text -> this.replaceText(text, replaces)).collect(Collectors.toList());
    }
}
