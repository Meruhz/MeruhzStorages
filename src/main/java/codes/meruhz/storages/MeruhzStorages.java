package codes.meruhz.storages;

import codes.meruhz.storages.core.developers.StorageApi;
import codes.meruhz.storages.core.developers.providers.StorageApiProvider;
import org.jetbrains.annotations.NotNull;

public final class MeruhzStorages {

    private static final @NotNull MeruhzStorages INSTANCE = new MeruhzStorages();

    private @NotNull StorageApi storageApi;

    public MeruhzStorages() {
        this.storageApi = new StorageApiProvider();
    }

    public @NotNull StorageApi getStorageApi() {
        return this.storageApi;
    }

    public void setStorageApi(@NotNull StorageApi storageApi) {
        this.storageApi = storageApi;
    }

    public static @NotNull MeruhzStorages getInstance() {
        return MeruhzStorages.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("***********************************");
        System.out.println("*                                 *");
        System.out.println("*   Welcome to MeruhzStorages :)  *");
        System.out.println("*                                 *");
        System.out.println("***********************************");
        System.out.println();
        System.out.println("Thank you for choosing MeruhzStorages!");
        System.out.println("You are now using the latest version");

        // public class LibraryWelcomeMessage {
        //
        //    public static void displayWelcomeMessage() {
        //        System.out.println("********************************************");
        //        System.out.println("*                                          *");
        //        System.out.println("*      Welcome to (Library Name)           *");
        //        System.out.println("*                                          *");
        //        System.out.println("********************************************");
        //        System.out.println();
        //        System.out.println("Thank you for choosing (Library Name)!");
        //        System.out.println("You are now using the latest version of our Java library.");
        //        System.out.println("Developed by: Meruhz");
        //        System.out.println();
        //        System.out.println("Explore our library's features:");
        //        System.out.println("- (Highlight key library feature 1)");
        //        System.out.println("- (Highlight key library feature 2)");
        //        System.out.println("- (Highlight key library feature 3)");
        //        System.out.println();
        //        System.out.println("Stay connected with the developer and community on GitHub:");
        //        System.out.println("GitHub: [Link to the repository]");
        //        System.out.println();
        //        System.out.println("If you have any questions or feedback, please don't hesitate to reach out.");
        //        System.out.println("We're here to ensure you have a great experience with (Library Name)!");
        //    }
        //
        //    public static void main(String[] args) {
        //        // This is where you would call the displayWelcomeMessage() method when your library is initialized.
        //        displayWelcomeMessage();
        //    }
        //}
    }
}
