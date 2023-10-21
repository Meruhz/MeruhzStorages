# **MeruhzStorages**

Hello there! Welcome to MeruhzStorages, the tool that facilitate the use and creation of message storages :)

### **INSTALLATION**

To get started using MeruhzStorages, follow these simple steps:

**1. 🔗 Download the latest JAR file of the desired module on the release section;**

**2. 📦 Add the downloaded JAR file to your project source and read the docs of the downloaded module source, then, download it on your Maven Goal and add this to your pom.xml:**

```html
<dependency>
    <groupId>codes.meruhz.storages</groupId>
    <artifactId>core</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

**3. 📚 After reloading Maven projects, you can import the library core in you Java code**

### **DEVELOPERS**

If you want to create a fully custom storage as you want, you can do this in two ways:

**1. Implements the base interface:**

```java
public class CustomStorage implements Storage<TextType, LocaleType> {
    
    // Implements all methods and base logic
}
```

```java
public class CustomMessage implements Message<TextType, LocaleType> {
    
    // Implements all methods and base logic
}
```

**2. Extend the abstract class:**

```java
public class CustomStorage extends AbstractStorage<TextType, LocaleType> {
    
    // Override essentials methods
}
```

```java
public class CustomMessage extends AbstractMessage<TextType, LocaleType> {
    
    // Override essentials methods
}
```

**OBS: It is essentially recommended that you override the storage serializer, but only if you deem it necessary.**

## CONTRIBUTIONS

**We would love to have your help in making this library even better. Feel free to submit pull requests, report issues, or suggest improvements. Your contribution is highly valuable!**

## LICENSE

**This library is under the MIT License. For all the details, refer to the [LICENSE](LICENSE) file.**

## CONTACT

**If you have any questions or need assistance, don't hesitate to get in touch with me! You can do this on:**

✉️ **Email: [meruhz.contato@gmail.com]()**

👤 **Discord: [@meruhz]()**