package codes.meruhz.langstor.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface Message<T> {

    @NotNull String getId();

    @NotNull MessageStorage<T> getStorage();

    @NotNull Collection<@NotNull MessageContent<T>> getContents();

    default @NotNull MessageContent<T> getContent(@NotNull Locale locale) {
        return this.getContents().stream().filter(messageContent -> messageContent.getLocale().equals(locale)).findFirst().orElseThrow(() -> new NullPointerException("Content with locale '" + locale + "' could not be found for message with id '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'"));
    }

    default @NotNull T getText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return this.getContent(locale).replace(replaces);

        } catch (NullPointerException e) {

            try {
                return this.getContent(this.getStorage().getDefaultLocale()).replace(replaces);

            } catch (NullPointerException ex) {
                throw new NullPointerException("Could not be found text with locales '" + locale + "' and the default '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
            }
        }
    }

    default @NotNull T getText(Object @NotNull [] replaces) {
        try {
            return this.getContent(this.getStorage().getDefaultLocale()).replace(replaces);

        } catch (NullPointerException e) {
            throw new NullPointerException("Could not be found text with default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
        }
    }

    default @NotNull List<@NotNull T> getArrayText(@NotNull Locale locale, Object @NotNull [] replaces) {
        try {
            return this.getContent(locale).replaceArray(replaces);

        } catch (NullPointerException e) {

            try {
                return this.getContent(this.getStorage().getDefaultLocale()).replaceArray(replaces);

            } catch (NullPointerException ex) {
                throw new NullPointerException("Could not be found array text with locales '" + locale + "' and the default '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
            }
        }
    }

    default @NotNull List<@NotNull T> getArrayText(Object @NotNull [] replaces) {
        try {
            return this.getContent(this.getStorage().getDefaultLocale()).replaceArray(replaces);

        } catch (NullPointerException e) {
            throw new NullPointerException("Could not be found array text with default locale '" + this.getStorage().getDefaultLocale() + "' for message '" + this.getId() + "' at storage '" + this.getStorage().getName() + "'");
        }
    }

    default @NotNull CompletableFuture<Void> addContent(@NotNull MessageContent<T> content) {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
           try {

               if(!this.getContents().contains(content)) {
                   this.getContents().add(content);
               }

               completableFuture.complete(null);

           } catch (Exception e) {
               completableFuture.completeExceptionally(e);
           }
        });

        return completableFuture.orTimeout(2, TimeUnit.SECONDS);
    }
}
