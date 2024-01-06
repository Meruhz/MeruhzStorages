package codes.meruhz.langstor.api.providers;

import codes.meruhz.langstor.api.LanguageApi;
import codes.meruhz.langstor.api.MessageStorage;
import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.api.utils.Loader;
import codes.meruhz.langstor.api.utils.model.AbstractConfiguration;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractLanguageApi extends Loader implements LanguageApi {

    private final @NotNull Set<@NotNull MessageStorage<?>> storages;

    public AbstractLanguageApi() {
        this(new HashSet<>());
    }

    public AbstractLanguageApi(@NotNull Set<@NotNull MessageStorage<?>> storages) {
        super("Language Storage API");
        this.storages = storages;
        super.load().join();
    }

    @Override
    public @NotNull Collection<@NotNull MessageStorage<?>> getStorages() {
        return this.storages;
    }

    @Override
    protected void start() {
        try (Stream<Path> files = Files.list(Paths.get(LanguageStorage.langstor().getStorageFolder().toURI()))) {
            files.forEach(path -> {

                try {
                    @NotNull File file = new File(path.toUri());

                    if(file.getName().endsWith(".json")) {
                        this.getStorages().add(deserialize(JsonParser.parseString(AbstractConfiguration.getFileContent(file))));
                    }

                } catch (IOException e) {
                    throw new RuntimeException("Error processing storage: " + e.getMessage(), e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error listing storages in directory: " + e.getMessage(), e);
        }
    }

    @Override
    protected void stop() {
        @NotNull File storageFolder = LanguageStorage.langstor().getStorageFolder();

        for(MessageStorage<?> storage : this.getStorages()) {

            if(!storageFolder.isDirectory() && !storageFolder.mkdirs()) {
                throw new RuntimeException("Storages main folder could not be created");
            }

            storage.getJsonModel().setContent(this.serialize(storage), true);
        }

        this.getStorages().clear();
    }
}
