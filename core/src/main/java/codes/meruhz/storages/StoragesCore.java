package codes.meruhz.storages;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class StoragesCore {

    private static final @NotNull Logger LOGGER = Logger.getLogger("MeruhzStorages");
    private static final @NotNull File STORAGES = new File(Paths.get(System.getProperty("user.dir")).toAbsolutePath() + File.separator + "storages" + File.separator);

    public static @NotNull Logger getLogger() {
        return StoragesCore.LOGGER;
    }

    public static @NotNull File getStorages() {
        return StoragesCore.STORAGES;
    }

    public static void main(String[] args) {
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