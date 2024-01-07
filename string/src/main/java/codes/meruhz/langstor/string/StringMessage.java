package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.providers.AbstractMessage;
import org.jetbrains.annotations.NotNull;

public class StringMessage extends AbstractMessage<String> {

    public StringMessage(@NotNull String id, @NotNull StringStorage storage) {
        super(id, storage);
    }
}
