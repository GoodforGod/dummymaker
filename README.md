# DummyMaker  :hotsprings:

[![Maintainability](https://api.codeclimate.com/v1/badges/c180e591ba7558c3add2/maintainability)](https://codeclimate.com/github/GoodforGod/dummymaker/maintainability)
[![codecov](https://codecov.io/gh/GoodforGod/dummymaker/branch/master/graph/badge.svg)](https://codecov.io/gh/GoodforGod/dummymaker)
[![travis](https://travis-ci.org/GoodforGod/dummymaker.svg?branch=master)](https://travis-ci.org/GoodforGod/dummymaker)

Library can generate *Data Objects* filled *random data* for your tests, 
database setup, Big Data setups or other workloads. 
Library is very flexible at tuning.

It can even do little bit of export in *CSV/JSON/XML/SQL formats*.

Documentation for **versions earlier than 2.0.0** present in [this document](/README-VERSION-1.X.md).

## Dependency :rocket:
**Gradle**
```groovy
dependencies {
    compile 'com.github.goodforgod:dummymaker:2.0.0'
}
```

**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>dummymaker</artifactId>
    <version>2.0.0</version>
</dependency>
```

## Content
- [Factory Example](#factory-example)
    - [Methods](#factory-methods)
    - [Gen Auto](#gen-auto-annotation)
- [Factory Configuration (GenRules)](#factory-configuration)
    - [Manual rules](#manual-rules)
    - [Auto rules](#auto-rules)
    - [Global rules](#global-rules)
- [Annotation Examples](#annotation-examples)
  - [Array Annotations](#array-annotations)  
  - [Collection Annotations](#collection-annotations)  
  - [Time Annotation](#time-annotation)  
  - [Special Annotations](#special-annotations)  
- [Export](#export)
- [Customization](#customization)
  - [Simple Generator](#igenerator)
  - [Complex Generator](#icomplexgenerator)
  - [Gen Custom Annotation](#gen-custom-annotation)
  - [Gen Complex Annotation](#gen-complex-annotation)
- [Version History](#version-history)

## Factory example

Example how to produce *1001* Users in no time.

All user data is automatically generated and proper generators 
are selected due to *@GenAuto* annotation.

Class that is instantiated by factory via *build* method **must** 
have zero argument constructor (*Can be private*).

```java
@GenAuto
public class User {
 
    /**
     * All fields will have unique data after being processed by factory
     */
    private int number;
    private String name;
    private String surname;
    private List<User> relatives;
}

public static List<User> getUsers() {
    final GenFactory factory = new GenFactory();

    User user = factory.build(User.class);

    // All users have unique data in their fields
    List<User> users = factory.build(User.class, 1000);
    
    return users;
}
```

### Factory Methods

Factory can not only instantiate classes but also provides other
contracts to manipulate with objects like using provided supplier.

```java
final GenFactory factory = new GenFactory();

Set<User> users = factory.stream(() -> new User(), 1)
                            .collect(Collectors.toSet());

User user = new User();
User filled = factory.fill(user);
```

There are more other contracts available just check *GenFactory*.

### Gen Auto annotation

*@GenAuto* annotation provides *depth* field that is required for embedded
fields such as *relatives* in this example.

For such class, each *User* will have field *relatives* filled with data
up to level *3* in depth.
Because each User will have other users as its data and such data can be represented
as a tree. So leaf that has its depth of *3* from root will be the last who have
field *relatives* filled with data.

Maximum depth allowed is *20*.

```java
@GenAuto(depth = 3)
public class User {

    private int number;
    private String name;
    private String surname;
    private List<User> relatives;
}
```

## Factory configuration

In case you have no intention or opportunity to annotate class 
even with *@GenAuto* annotation, you can configure all generators
and fields you want to ignore with factory configuration.

### Manual rules

In case you want to fill only specific field to be filled with data
you can configure such behavior via *rules*.

```java
GenRules rules = GenRules.of(
        GenRule.of(User.class)
                .add(NameGenerator.class, "name")
                .add(ShortGenerator.class, int.class)
);

GenFactory factory = new GenFactory(rules);
User user = factory.build(User.class);
```

In this case only *name* and *number* fields will be filled with data, 
as they are the only ones suited to factory *rules*.

### Auto rules

In case you want factory automatically fill fields based on their types
and names, you can setup *auto* rules (same as *GenAuto* annotation).

```java
GenRules rules = GenRules.of(
        GenRule.auto(User.class, 2)
                .add(NameGenerator.class, "name")
                .add(ShortGenerator.class, int.class)
);

GenFactory factory = new GenFactory(rules);
User user = factory.build(User.class);
```

In this case fields *name* and *number* will be filled as previously but
also all other fields will be automatically filled.

```java
GenRules rules = GenRules.of(GenRule.auto(User.class, 2));

GenFactory factory = new GenFactory(rules);
User user = factory.build(User.class);
```

This will have same affect as previous rules, due to fields *name* and *number*
been automatically filled.

### Global rules

In case you have a lot of common fields with same values in different classes
you can setup *global* rules for all classes that will be filled.

```java
GenRules rules = GenRules.of(
            GenRule.global(2)
                .add(NameGenerator.class, "name")
                .add(ShortGenerator.class, int.class)
);

GenFactory factory = new GenFactory(rules);
User user = factory.build(User.class);
```

In this case all fields with name *name* in all classes will be
filled using *NameGenerator* and same for *int* fields with *ShortGenerator*.

## Annotation Examples

### Array Annotations

Responsible for filling arrays data, could be applied annotation, 
*GenRule* or automatically applied via *@GenAuto* or auto *GenRule*.


```java
public class User {

    @GenArray
    private int[] numbers;
    
    @GenArray
    private int[][] numberArrays;
}
```


### Collection Annotations

Responsible for filling collections data, like list, set or map. Could be applied annotation, 
*GenRule* or automatically applied via *@GenAuto* or auto *GenRule*.


```java
public class User {

    @GenList
    private List<Integer> numberList;
    
    @GenSet
    private Set<Integer> numberSet;
    
    @GenMap
    private Map<Integer, String> numberMap;
}
```


### Time Annotation

Responsible for filling time data, could be applied via annotation, 
*GenRule* or automatically applied via *@GenAuto* or auto *GenRule*.

**GenTime** annotation is used to create time/dateTime/timestamps for field.
Automatically identify field time *type* and generate value for it. 

Supported time fields types:
* *LocalDate*
* *LocalTime*
* *LocalDateTime*
* *Date (java.util.Date)*
* *Timestamp (java.sql.Timestamp)*

```java
public class User {

    @GenTime
    private Timestamp timestamp;
    
    @GenTime
    private LocalDateTime dateTime;
}
```

### Embedded Annotation

Responsible for filling complex data, could be applied annotation, 
*GenRule* or automatically applied via *@GenAuto* or auto *GenRule*.

```java
public class User {

    @GenEmbedded
    private User parent;
}
```

### Special Annotations

* ***GenExportForce*** allow to *force* export object field, even if it is not generated by *Gen*Annotation.

* ***GenExportIgnore*** allow to *ignore* object's field during export.

* ***GenExportRename*** allow to rename Dummy export field name or Class Name (Annotate constructor to rename class export name).

* ***GenIgnore*** field will not be filled with value when generated.

* ***GenEnum*** generates Enum field value.

* ***GenSequence*** annotation with option (*from*) used to numerate Dummies fields as sequence (Works on *Integer/Long/String* field types).

* ***GenCustom*** used to mark [field with custom generators](#gen-annotation).

## Export

Information about different exporters can be [found here](/README-EXPORT.md).

## Customization

You can extend basic functionality with your own annotations and generators. All infrastructure will support custom generators, annotations, generate factories with no doubt.

You need to:
* Create generator via *IGenerator* or *IComplexGenerator* interface.
* Annotated class field with **GenCustom** and your generator.
* *Magic*.
* Done.

### IGenerator

Simple generator that produce specific value.

Generator can have pattern to register it for *@GenAuto* discovery.

```java
public class IntegerSmallGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("age|grade|group", CASE_INSENSITIVE);

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
```

### IComplexGenerator

Is used to build complex values for fields, 
when simple *IGenerator* implementation is insufficient,
*Gen* annotation require special parameters or when you need to access field. 

```java
public class CustomComplexGenerator implements IComplexGenerator {

    @Override
    public Object generate(Annotation annotation, Field field, IGenStorage storage, int depth) {
        final int from = GenInteger.class.equals(annotation.annotationType()) 
                ? ((GenInteger) annotation).from() 
                : 0;
        
        final int to = GenInteger.class.equals(annotation.annotationType()) 
                ? ((GenInteger) annotation).to() 
                : Integer.MAX_VALUE;

        return ThreadLocalRandom.current().nextInt(from, to);
    }

    @Override
    public Object generate() {
        return new IntegerGenerator().generate();
    }
}
```

### Gen Custom Annotation

For custom *IGenerator*s or *IComplexGenerator*s that don't require special annotation
fields just use **@GenCustom** annotation that is provided by library to mark fields with your
custom generators.

```java
public class User {

    @GenCustom(CustomComplexGenerator.class)
    private String number;
}
```


### Gen Complex Annotation

Is required when you need properties for your *IComplexGenerator*.

```java
@ComplexGen(CustomComplexGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenInteger {

    int from() default Integer.MIN_VALUE;

    int to() default Integer.MAX_VALUE;
}
```

In case you want custom annotation for simple generator you can do it as well, 
just use *@PrimeGen* instead of *@ComplexGen* to mark your annotation.


## Version History

**2.0.0** - Redesign factory and contracts, improved complex and other generators, GenRules added for configuration without annotations, @Ignore annotation, @GenEnum enum generation, minor improvements and more.

**1.1.3** - Embedded objects in arrays, collections full support, collections\arrays embedded depth support, Json & SQL exporters arrays & collections support.

**1.1.2** - Full primitive support, single or two dimension array support, jsonExporter collection, map, array support.

**1.1.1** - Factory performance improvements (about 40%), *GenAuto* annotation, *IComplexGenerator* the successor of *IGenerateFactory*, primitive support, Embedded multi level depth support, bug fixes.

**1.1.0** - Performance and architecture improvements, *IGenerateFactory* introduced, collection *Gen* annotations, time *Gen* annotations, Embedded *Gen* support, architecture improvements in custom user extension support.

**1.0.3** - Lots of tests for all functionality, Added *DataTypeMap* parameter for users in SqlExporter (expandable data type for sql), *NamingStrategy* for exporters, bug fixes.

**1.0.2** - Added special *GenRenameExport* annotation, export as single string, export values order fix, minor fixes and improvements.

**1.0.1** - Added new generator and annotations, special *GenEnumerate* annotation, other minor fixes (Like SQL export).

**1.0.0** - Initial project with core functions.

## License

This project is licensed under the MIT - see the [LICENSE](LICENSE) file for details.
