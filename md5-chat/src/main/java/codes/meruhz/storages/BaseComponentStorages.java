package codes.meruhz.storages;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.data.Storage;
import codes.meruhz.storages.serializer.BaseComponentStorageSerializer;
import codes.meruhz.storages.serializer.Serializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public class BaseComponentStorages extends StoragesCore<BaseComponent[], Locale> {

    private static @NotNull BaseComponentStorages INSTANCE = new BaseComponentStorages();

    public BaseComponentStorages() {
        super.setStorageSerializer(new BaseComponentStorageSerializer());
    }

    @Override
    public @NotNull Serializer<Storage<BaseComponent[], Locale>> getStorageSerializer() {
        return super.getStorageSerializer();
    }

    @Override
    public void setStorageSerializer(@NotNull Serializer<Storage<BaseComponent[], Locale>> storageSerializer) {
        super.setStorageSerializer(storageSerializer);
    }

    public static @NotNull BaseComponentStorages getInstance() {
        return BaseComponentStorages.INSTANCE;
    }

    public static void setInstance(@NotNull BaseComponentStorages instance) {
        BaseComponentStorages.INSTANCE = instance;
    }

    public static void main(String[] args) {
        System.out.println("You are using BaseComponent storages module :)");
    }
}
