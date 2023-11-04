package codes.meruhz.storages.md5.chat;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.core.StoragesCore;
import codes.meruhz.storages.md5.chat.serializer.BaseComponentStorageSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorages extends StoragesCore<BaseComponent[], Locale> {

    private static final @NotNull BaseComponentStorages INSTANCE = new BaseComponentStorages();

    public BaseComponentStorages() {
        super.setStorageSerializer(new BaseComponentStorageSerializer());
    }

    public static @NotNull BaseComponentStorages getInstance() {
        return BaseComponentStorages.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("You are using BaseComponent storages module :)");
    }
}
