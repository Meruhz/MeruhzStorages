## **MeruhzStorages-String**

MeruhzStorages-String is a version that doesn't rely on any external libraries since it exclusively utilizes native Java libraries, ensuring compatibility in any environment it's integrated into.

### **INSTALLATION**

By following the steps outlined in the main installation section [General.md](), you can utilize MeruhzStorages-String as follows:

`mvn install:install-file -Dfile=MeruhzStorages-String.jar -DgroupId=codes.meruhz.storages -DartifactId=string -Dversion=1.0-SNAPSHOT -Dpackaging=jar`

Then add this to your pom.xml:

```html
<dependency>
    <groupId>codes.meruhz.storages</groupId>
    <artifactId>string</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

### **HOW TO USE IT**

Take a look at how easy it is to create a string message storages with this module:
```json
{
  "name": "Storage test",
  "default locale": "EN_US",
  "messages": {
    "string.message": {
      "content": {
        "EN_US": "Contribute with this project :)",
        "PT_BR": "Contribua com este projeto :)"
      }
    }
  }
}
```

**OBS: This JSON file have to be at your project's resources folder, once this is done, you can get this message storage with method:**
```java
@NotNull StringStorage storage = (StringStorage) StringStorages.getInstance().getStorageSerializer().deserialize(JsonParser.parseReader(new InputStreamReader(AbstractConfiguration.getFromResources("resources_file_name.json"))));
```

**OBS: As default, this storage wil be saved at folder "storages" on your project source directory.**

But if you want to create a string message storage by a more complicated way, you can do this:

```java
@NotNull Locale enUsLocale = new Locale("en", "US"); 
@NotNull Locale ptBrLocale = new Locale("pt", "BR");
 
@NotNull StringStorage storage = new StringStorage("Storage test", enUsLocale);
 
@NotNull StringMessage message = new StringMessage(storage, "message.test"); 
message.addContent(enUsLocale, "Contribute with this project :)"); 
message.addContent(ptBrLocale, "Contribua com este projeto :)");
 
storage.load();
 
// Unload before
storage.unload();
```

This code will return the exact same Storage as mentioned earlier, albeit in a more verbose manner.

OBS: You can use utilitary class LocaleUtils when using native Java Locale.