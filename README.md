# MeruhzStorages

**Hello there! Welcome to MeruhzStorages, the tool that will facilitate the creation of message storages :)**

## INSTALLATION

**To get started using MeruhzStorages, follow these simple steps:**

1. 🔗 **Download the latest JAR file from the release section of this repository;**
2. 📦 **Add the downloaded JAR file to your project. It's easy! Simply put the JAR file to your project source and execute this on Maven Goal:**

```mvn install:install-file -Dfile=MeruhzStorages.jar -DgroupId=com.storages -DartifactId=main -Dversion=1.0-SNAPSHOT -Dpackaging=jar clean```

  **Then add this on your pom.xml:**
  
```html
<dependency>
    <groupId>com.storages</groupId>
    <artifactId>main</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

3. 📚 **After reloading Maven projects, you can import the library in your Java code :)**

## HOW TO USE IT

**Take a look at how easy it is to create message storages with this library:**

```json
{
  "name": "Storage test",
  "default locale": "EN_US",
  "messages": {
    "legacy.message": {
      "content": {
        "EN_US": "Contribute with this project :)",
        "PT_BR": "Contribua com este projeto :)"
      }
    },
    "base_component.message": {
      "content": {
        "EN_US": "{\"text\":\"You can use BungeeCord BaseComponents too :)\"}",
        "PT_BR": "{\"text\":\"Você também pode usar o BaseComponent do BungeeCord :)\"}"
      }
    }
  }
}
```

**OBS: This JSON file have to be in your project's resources folder**

**Once this is done, you can get this message storage with method**

```java
@NotNull BaseComponentStorage storage = BaseComponentStorages.storages().getBaseComponentStorageApi().getSerializer().deserialize(JsonConfiguration.getFromResources("resources_file_name.json"));
```

**But if you want to create a message storage in a more complicated way, you can do this:**

```java
@NotNull Locale enUsLocale = new Locale("en", "US");
@NotNull Locale ptBrLocale = new Locale("pt", "BR");
// More locales if you want

@NotNull BaseComponentStorage storage = BaseComponentStorages.storages().getBaseComponentStorageApi().createStorage("Storage test", enUsLocale);

@NotNull BaseComponentMessage message = BaseComponentStorages.storages().getBaseComponentStorageApi().createMessage(storage, "message.test");
message.addContent(enUsLocale, new BaseComponent[] { new TextComponent("Here is the message content") });
message.addContent(ptBrLocale, new BaseComponent[] { new TextComponent("Aqui é o conteúdo da mensagem") });

storage.getMessages().add(message);

storage.save() // You can use this when want to save the storage on the folder
```

**OBS: The storages wil be saved at folder "storages" on your project source**

## DEVELOPERS

**If you want to create a custom storage without using BaseComponent but another type of text, you can do this in two ways:**

1. **Implements the base interface:**

```java
public class CustomStorage implements Storage<String> {
    
    // Implements all methods and all main logic
}
```

```java
public class CustomMessage implements Message<String> {
    
    // Implements all methods and all main logic
}
```

2. **Extend the abstract class that implements this interface:**

```java
public class CustomStorage extends StorageProvider<String> {
    
    // Override only essentials methods
}
```

```java
public class CustomMessage extends MessageProvider<String> {
    
    // Override only essentials methods
}
```

**OBS: It is recommended that you override the main API class after creating a custom storage or message, but only if you deem it necessary.**

## CONTRIBUTIONS

**We would love to have your help in making this library even better. Feel free to submit pull requests, report issues, or suggest improvements. Your contribution is highly valuable!**

## LICENSE

**This library is under the MIT License. For all the details, refer to the [LICENSE](LICENSE) file.**

## CONTACT

**If you have any questions or need assistance, don't hesitate to get in touch with me! You can do this on:**

✉️ **Email: [meruhz.contato@gmail.com]()**

👤 **Discord: [@meruhz]()**