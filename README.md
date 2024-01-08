# Language Storage

### _Overview_

The Language Storage is a Java library designed to provide a flexible and extensible framework for managing multilingual message storage in Java applications. It allows developers to store and manage messages in different languages, supporting both string and rich text (BungeeCord's BaseComponent) content.

### _Installation_

To use the Language Storage Library in your Java project, follow these steps:

> Step 1: Clone the Language Storage repository from GitHub to your local machine:

```comment
git clone https://github.com/Meruhz/LanguageStorage.git
```

> Step 2: Add the Language Storage as a dependency in your project. You can do this:

Option 1: Navigate to the root directory of the cloned repository and build the library using a build tool like Maven or Gradle:

```comment
// for Maven
mvn clean install
```

```comment
// for Gradle
./gradlew clean build
```

Then, add the library as a dependency in your project. If you are using Maven, add the following dependency to your pom.xml file:

```html
<!-- LanguageStorage Main -->
<dependency>
    <groupId>codes.meruhz.langstor</groupId>
    <artifactId>main</artifactId>
    <version>2.0-SNAPSHOT</version> <!-- Use the appropriate version -->
    <scope>compile</scope>
</dependency>

<!-- LanguageStorage Implementation -->
<dependency>
    <groupId>codes.meruhz.langstor</groupId>
    <artifactId>{implementation.name}}</artifactId>
    <version>2.0-SNAPSHOT</version> <!-- Use the appropriate version -->
    <scope>compile</scope>
</dependency>
```

If you are using Gradle, add the following dependency to your build.gradle file:

```groovy
implementation 'codes.meruhz.langstor:main:2.0-SNAPSHOT' // Use the appropriate version
implementation 'codes.meruhz.langstor:{implementation.name}:2.0-SNAPSHOT' // Use the appropriate version
```

If you want to use the Language Storage API to create your implementation, you can change "{implementation.name}" to "api".

Option 2: Download the JAR file from the Releases page on GitHub, then include it in your project's classpath.

> Step 3: In your Java code, initialize the Language Storage:

```java
import codes.meruhz.langstor.api.main.LanguageApi;
import codes.meruhz.langstor.api.providers.AbstractLanguageApi;
import codes.meruhz.langstor.api.utils.model.JsonElementConfiguration;
import codes.meruhz.langstor.md5.chat.ComponentLanguageApi;
import codes.meruhz.langstor.md5.chat.ComponentUtils;
import codes.meruhz.langstor.md5.chat.LanguageStorage;
import codes.meruhz.langstor.string.StringLanguageApi;
import codes.meruhz.langstor.string.StringUtils;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MyApp {

    public static void main(String[] args) {
        // Create a language API and message utils
        LanguageApi languageApi = new ComponentLanguageApi();
        ComponentUtils componentUtils = new ComponentUtils();

        // Initialize LanguageStorage with the language API and message utils
        LanguageStorage languageStorage = new LanguageStorage(languageApi, componentUtils);

        // Set the storage folder and start the language storage
        languageStorage.load().join();

        // Your application logic here

        // Stop the language storage when the application exits
        languageStorage.unload().join();
    }
}
// You can change the final storage directory using static method setStorageFolder(File) from LanguageStorage class.
```

> Step 4: Use the Language Storage to create a manage multilingual messages in your application. Follow the usage instructions provided in the code documentation.

### Conclusion

The Language Storage simplifies the process of managing multilingual messages in Java applications. Developers can easily create and organize message storages, work with different types of content, and efficiently serialize/deserialize data. The modular design allows for easy extension to support additional content types and storage implementations.

### Contact

- ✉️  Email: [meruhz.contato@gmail.com]()
- 🎮  Discord: [@meruhz (Meruhz#6933)]()