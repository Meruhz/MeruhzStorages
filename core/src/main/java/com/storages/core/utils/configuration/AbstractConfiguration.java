package com.storages.core.utils.configuration;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class AbstractConfiguration<T> {

    private final @NotNull File folder, file;
    private final @NotNull String name;

    protected @Nullable T configuration;

    protected AbstractConfiguration(@NotNull File folder, @NotNull String name) {
        if(!folder.isDirectory() && !folder.mkdirs()) {
            throw new RuntimeException("Folder '" + folder.getAbsolutePath() + "' could not be created");
        }
        
        this.folder = folder;
        this.name = name.replace(" ", "_");
        this.file = new File(this.getFolder(), this.getName());
    }

    public final @NotNull File getFolder() {
        return this.folder;
    }

    public final @NotNull File getFile() {
        return this.file;
    }

    public final @NotNull String getName() {
        return this.name;
    }

    public @NotNull T getConfiguration() {
        return Optional.ofNullable(this.configuration).orElseThrow(() -> new NullPointerException("Configuration '" + this.getName() + "' is not initialized"));
    }

    public void setConfiguration(@NotNull T configuration, boolean autoLoad) {
        this.configuration = configuration;

        if(autoLoad) {
            this.load();
        }
    }

    protected void create() {
        if(!this.getFile().getParentFile().exists()) {
            try {

                if(!this.getFile().getParentFile().createNewFile()) {
                    throw new IOException("An error occurred while creating file: " + this.getFile().getParentFile());
                }

            } catch (IOException e) {
                throw new RuntimeException("An error occurred while initializing configuration: " + this.getName(), e);
            }
        }
    }

    @MustBeInvokedByOverriders
    public void load() {
        this.create();
    }

    protected void save(@NotNull String format) {
        try (@NotNull FileOutputStream fileOutputStream = new FileOutputStream(this.getFile())) {
            @NotNull BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

            bufferedWriter.write(format);
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while saving configuration file: " + this.getName(), e);
        }
    }

    public static @NotNull InputStream getFromResources(@NotNull String file) {
        file = "/" + file;

        @Nullable InputStream inputStream = AbstractConfiguration.class.getResourceAsStream(file);

        if(inputStream == null) {
            throw new NullPointerException("Could not find resources file: " + file);
        }

        return inputStream;

    }

    public static @NotNull String getFileContent(@NotNull File file) throws IOException {
        @NotNull FileInputStream fileInputStream = new FileInputStream(file);
        @NotNull BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        @NotNull StringBuilder content = new StringBuilder();
        @NotNull String line;

        while((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }

        bufferedReader.close();
        return content.toString();
    }
}
