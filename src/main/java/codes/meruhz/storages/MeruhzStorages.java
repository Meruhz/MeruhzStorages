package codes.meruhz.storages;

import codes.meruhz.storages.core.Core;
import codes.meruhz.storages.core.developers.providers.StorageApiProvider;
import org.jetbrains.annotations.NotNull;

public final class MeruhzStorages {

    private static final @NotNull MeruhzStorages INSTANCE = new MeruhzStorages();

    private @NotNull Core core;

    public MeruhzStorages() {
        this.core = new StorageApiProvider.ApiCore();
    }

    public @NotNull Core getCore() {
        return this.core;
    }

    public void setCore(@NotNull Core core) {
        this.core = core;
    }

    public static @NotNull MeruhzStorages getInstance() {
        return MeruhzStorages.INSTANCE;
    }

    public static void main(String[] args) {
        MeruhzStorages.getInstance().getCore().load();

        System.out.println("***********************************");
        System.out.println("*                                 *");
        System.out.println("*   Welcome to MeruhzStorages :)  *");
        System.out.println("*                                 *");
        System.out.println("***********************************");
        System.out.println();
        System.out.println("Thank you for choosing MeruhzStorages!");
        System.out.println("You are now using version: 1.0-SNAPSHOT");
        System.out.println("Developer: Meruhz @meruhz");
        System.out.println();
        System.out.println("Explore our library features:");
        System.out.println(" • Message storage creation;");
        System.out.println(" • BaseComponent API integrated;");
        System.out.println(" • Storages serialized as JSON;");
        System.out.println(" • Custom API support;");
        System.out.println(" • Fully API.");
        System.out.println();
        System.out.println("Stay connected with the developer and updates on GitHub:");
        System.out.println("Developer Github: https://github.com/Meruhz/");
        System.out.println("Library Github: https://github.com/Meruhz/MeruhzStorages/");
        System.out.println();
        System.out.println("If you have any questions, feedback or suggestions, please don't hesitate to reach out :)");
        System.out.println("We're here to ensure you have a great experience with MeruhzStorages!");
    }
}
