package codes.meruhz.storages.component.data;

import codes.laivy.mlanguage.lang.Locale;
import codes.meruhz.storages.api.MeruhzStorages;
import codes.meruhz.storages.api.data.Message;
import codes.meruhz.storages.api.data.impl.AbstractStorage;
import codes.meruhz.storages.api.utils.configuration.AbstractConfiguration;
import codes.meruhz.storages.component.serializer.ComponentStorageSerializer;
import codes.meruhz.storages.component.utils.ComponentUtils;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ComponentStorage extends AbstractStorage<BaseComponent[]> {

    public ComponentStorage(@NotNull String name, @NotNull Locale defaultLocale) {
        super(name, defaultLocale);
    }

    public ComponentStorage(@NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<BaseComponent[]>> messages) {
        super(name, defaultLocale, messages);
    }

    public ComponentStorage(@NotNull File source, @NotNull String name, @NotNull Locale defaultLocale, @NotNull Set<@NotNull Message<BaseComponent @NotNull[]>> messages) {
        super(source, name, defaultLocale, messages);
    }

    public @NotNull String getLegacyText(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return ComponentUtils.getText(super.getText(locale, id, replaces));
    }

    public @NotNull List<@NotNull String> getLegacyArray(@NotNull Locale locale, @NotNull String id, @NotNull Object... replaces) {
        return ComponentUtils.getArrayText(super.getArrayText(locale, id, replaces));
    }

    @Override
    protected void start() {
        if(this.getSource().exists()) {

            try (@NotNull Stream<Path> stream = Files.list(this.getSource().toPath()).filter(path -> path.getFileName().toString().equals(this.getJsonContent().getName()))) {
                @NotNull Optional<Path> optionalPath = stream.findFirst();

                if(optionalPath.isPresent() && Files.exists(optionalPath.get())) {
                    MeruhzStorages.getInstance().getStorages().add(new ComponentStorageSerializer().deserialize(JsonParser.parseString(AbstractConfiguration.getFileContent(optionalPath.get().toFile()))));
                }

            } catch (IOException e) {
                throw new RuntimeException("Storage '" + this.getName() + "' could not be loaded from folder '" + this.getSource().getAbsolutePath() + "'", e);
            }
        }
    }

    @Override
    protected void stop() {
        @NotNull File source = super.getSource();

        if(!source.isDirectory() && !source.mkdirs()) {
            throw new RuntimeException("Storage '" + source.getName() + "' could not be created at source '" + this.getSource().getAbsolutePath() + "'");
        }

        super.getJsonContent().setContent(new ComponentStorageSerializer().serialize(this), true);
        MeruhzStorages.getInstance().getStorages().remove(this);
    }
}
