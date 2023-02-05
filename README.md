# Java Object Memory Measurer

Библиотека для измерения фактического размера Java-объектов в памяти. Будет полезна при разработке новых структур
данных.

## Сборка

Необходимо наличие JDK 8

```shell
./mvnw clean install -Pbuild-jar
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

Передайте в статический метод `ObjectMemoryMeasurer#analyze()` исследуемый объект:

```java
var objectToAnalyze = new HashMap<String, StringBuilder>();
ObjectMemoryMeasurer.analyze(objectToAnalyze).getFootprint().printOut();
```

При запуске приложения нужно указать параметр VM:

```shell
-javaagent:path/to/object-memory-measurer-0.1.0.jar
```

Для версий Java ≥ 9 требуется указание дополнительных параметров:

```shell
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/java.util=ALL-UNNAMED
```

Готовые примеры кода есть в пакете `com.github.lexakimov.omm.example`. Один из примеров можно запустить из консоли
следующей командой:

```shell
java -jar -javaagent:target/object-memory-measurer.jar target/object-memory-measurer.jar
```

## Пример вывода в консоль:
```
[java object memory meter]: memory footprint of java.util.HashMap instance:
[java object memory meter]:      Count       Size Type                                                        
[java object memory meter]:          1       16 B java.lang.Boolean                                           
[java object memory meter]:          1       24 B java.lang.Long                                              
[java object memory meter]:          1       16 B java.lang.Object                                            
[java object memory meter]:          7      336 B java.lang.String                                            
[java object memory meter]:          2      464 B java.util.HashMap                                           
[java object memory meter]:          8      256 B java.util.HashMap$Node                                      
[java object memory meter]:          1       80 B java.util.HashMap$Node[]                                    
[java object memory meter]:          1      272 B java.util.LinkedHashMap                                     
[java object memory meter]:          1      216 B java.util.TreeMap                                           
[java object memory meter]: ----------------------------------------------------------------------------------
[java object memory meter]:         23    1,6 KiB Total                                                       
```
