# Java Object Memory Measurer
Библиотека для измерения фактического размера Java-объектов в памяти. Будет полезна при разработке новых структур данных.

## Сборка
Необходимо наличие JDK >= 8

```shell
./mvnw clean install
```

## Использование
Подключите библиотеку к проекту при помощи maven:
```xml
<dependency>
    <groupId>com.github.lexakimov</groupId>
    <artifactId>object-memory-measurer</artifactId>
    <version>0.1.0</version>
</dependency>
```

Создайте экземпляр класса `ObjectGraphTraverser` и передайте в него исследуемый объект:
```java
var obj = new HashMap<String, StringBuilder>();

var traverser = new ObjectGraphTraverser();
traverser.traverseFor(obj);
traverser.get()
```

При запуске приложения нужно указать параметр VM:
```shell
-javaagent:path/to/object-memory-measurer-0.1.0.jar
```

Для версий Java >= 9 может потребоваться указание дополнительный параметров;
```shell
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/java.util=ALL-UNNAMED
```

Готовый пример кода есть в классе `com.github.lexakimov.omm.Example`. Его можно запустить из консоли следующей командой:
```shell
java -jar -javaagent:target/object-memory-measurer.jar target/object-memory-measurer.jar
```
