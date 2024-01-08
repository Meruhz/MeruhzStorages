package codes.meruhz.langstor.string;

import codes.meruhz.langstor.api.providers.AbstractMessage;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a message containing strings. It extends AbstractMessage and provides a
 * constructor for creating a StringMessage with the specified ID and associated storage.
 *
 * @see AbstractMessage
 */
public class StringMessage extends AbstractMessage<String> {

    /**
     * Constructs a StringMessage with the specified ID and associated storage.
     *
     * @param id      The ID of the message.
     * @param storage The storage to which the message belongs.
     */
    public StringMessage(@NotNull String id, @NotNull StringStorage storage) {
        super(id, storage);
    }
}
