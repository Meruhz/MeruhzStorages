package com.storages.core.utils.configuration;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class JsonConfiguration extends AbstractConfiguration<JsonElement> {

    public JsonConfiguration(@NotNull File folder, @NotNull String name) {
        super(folder, name.endsWith(".json") ? name : name + ".json");
        super.configuration = new JsonObject();
    }

    @Override
    public void load() {
        super.load();

        if(super.getFile().length() != 0) {
            @NotNull JsonElement configuration;

            try {
                configuration = JsonParser.parseString(getFileContent(super.getFile()));

            } catch (IOException e) {
                throw new RuntimeException("An error occurred while loading content from file: " + super.getFile(), e);
            }

            super.setConfiguration(configuration, false);

            if(!super.getConfiguration().toString().equals(configuration.toString())) {
                this.load();
            }

        } else {
            super.save(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(super.getConfiguration()));
        }
    }
}
