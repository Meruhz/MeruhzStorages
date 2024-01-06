package codes.meruhz.langstor.md5.chat.main;

import codes.meruhz.langstor.api.main.LanguageStorage;
import codes.meruhz.langstor.md5.chat.ComponentUtils;

public class ComponentLanguage {

    public ComponentLanguage() {
        LanguageStorage.langstor().setMessageUtils(new ComponentUtils());
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}