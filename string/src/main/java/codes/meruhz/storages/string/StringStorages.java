package codes.meruhz.storages.string;

import codes.meruhz.storages.core.StoragesCore;
import codes.meruhz.storages.core.data.Storage;
import codes.meruhz.storages.core.serializer.Serializer;
import codes.meruhz.storages.string.serializer.StringStorageSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class StringStorages extends StoragesCore<String, Locale> {

    private static @NotNull StringStorages INSTANCE = new StringStorages();

    public StringStorages() {
        super.setStorageSerializer(new StringStorageSerializer());
    }

    @Override
    public @NotNull Serializer<Storage<String, Locale>> getStorageSerializer() {
        return super.getStorageSerializer();
    }

    @Override
    public void setStorageSerializer(@NotNull Serializer<Storage<String, Locale>> storageSerializer) {
        super.setStorageSerializer(storageSerializer);
    }

    public static @NotNull StringStorages getInstance() {
        return StringStorages.INSTANCE;
    }

    public static void setInstance(@NotNull StringStorages instance) {
        StringStorages.INSTANCE = instance;
    }

    public static void main(String[] args) {
        System.out.println("You are using String storages module :)");
    }
}