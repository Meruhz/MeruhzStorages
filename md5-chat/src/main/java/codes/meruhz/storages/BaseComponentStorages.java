package codes.meruhz.storages;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.serializer.BaseComponentStorageSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorages {

    public BaseComponentStorages() {
        @NotNull StoragesCore<BaseComponent[], Locale> core = (StoragesCore<BaseComponent[], Locale>) StoragesCore.getCore();
        core.setStorageSerializer(new BaseComponentStorageSerializer());
    }

    public static void main(String[] args) {
        System.out.println("You are using BaseComponent storages module :)");
    }
}
