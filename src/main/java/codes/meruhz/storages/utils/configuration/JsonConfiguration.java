package codes.meruhz.storages.utils.configuration;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonConfiguration extends AbstractConfiguration {

    private @NotNull JsonElement configuration;

    public JsonConfiguration(@NotNull File folder, @NotNull String name) {
        super(folder, name.endsWith(".json") ? name : name + ".json");
        this.configuration = new JsonObject();
    }

    public @NotNull JsonElement getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(@NotNull JsonElement configuration, boolean autoLoad) {
        this.configuration = configuration;

        if(autoLoad) {
            this.load();
        }
    }

    protected void save() {
        try (@NotNull FileOutputStream fileOutputStream = new FileOutputStream(this.getFile())) {
            @NotNull BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

            bufferedWriter.write(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(this.getConfiguration()));
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while saving configuration file: " + this.getName(), e);
        }
    }

    @Override
    public void load() {
        if(!this.getFile().getParentFile().exists()) {
            try {

                if(!this.getFile().getParentFile().createNewFile()) {
                    throw new IOException("An error occurred while creating file: " + this.getFile().getParentFile());
                }

            } catch (IOException e) {
                throw new RuntimeException("An error occurred while initializing configuration: " + this.getName(), e);
            }

        } else if(this.getFile().length() != 0) {

            try (@NotNull FileInputStream fileInputStream = new FileInputStream(this.getFile())) {
                @NotNull BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                @NotNull StringBuilder content = new StringBuilder();
                @NotNull String line;

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                this.setConfiguration(JsonParser.parseString(content.toString()), false);
                this.save();

            } catch (IOException e) {
                throw new RuntimeException("An error occurred while loading Json configuration file: " + this.getName(), e);
            }

        } else {
            this.save();
        }
    }

    public static @NotNull JsonElement getFromResources(@NotNull String file) {
        file = "/" + file;

        try (InputStream inputStream = JsonConfiguration.class.getResourceAsStream(file)) {

            if(inputStream == null) {
                throw new NullPointerException("Could not find resources file: " + file);
            }

            return JsonParser.parseReader(new InputStreamReader(inputStream));

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading resources file: " + file, e);
        }
    }
}
