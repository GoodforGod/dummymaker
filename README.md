# DummyMaker  :hotsprings:

[![Maintainability](https://api.codeclimate.com/v1/badges/c180e591ba7558c3add2/maintainability)](https://codeclimate.com/github/GoodforGod/dummymaker/maintainability)
[![codecov](https://codecov.io/gh/GoodforGod/dummymaker/branch/master/graph/badge.svg)](https://codecov.io/gh/GoodforGod/dummymaker)
[![travis](https://travis-ci.org/GoodforGod/dummymaker.svg?branch=master)](https://travis-ci.org/GoodforGod/dummymaker)

Library can generate *Data Objects* filled *random data* for your tests, 
database setup, Big Data setups or other workloads. 
Library is very flexible at tuning.

It can even do little bit of export in *CSV/JSON/XML/SQL formats*.

For versions **earlier than 2.0.0** refer to [this documentation](/README-VERSION-1.X.md).

## Dependency :rocket:
**Gradle**
```groovy
dependencies {
    compile 'com.github.goodforgod:dummymaker:2.0.0-SNAPSHOT'
}
```

**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>dummymaker</artifactId>
    <version>2.0.0-SNAPSHOT</version>
</dependency>
```

## Content
- [Example](#start-example)
- [Annotations](#annotations)
  - [Auto Annotation](#auto-annotation)
  - [Array Annotations](#array-annotations)  
  - [Collection Annotations](#collection-annotations)  
  - [Time Annotation](#time-annotation)  
  - [Special Annotations](#special-annotations)  
- [Getting Started Examples](#getting-started-examples)
  - [Annotations](#annotations-examples)
  - [Factories](#factories-examples)
- [Customization](#customization)
  - [IGenerator](#igenerator)
  - [IComplexGenerator](#icomplexgenerator)
  - [Complex Gen Annotation](#complex-gen-annotation)
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
contracts to manipulate with dummies.

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

## Auto rules

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

## Global rules

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



### **Array Annotations**

**GenArray** annotation allow you to generate an array of any type library provides generators for. 
Arrays in classes annotated with *@GenAuto* will be generated automatically.

**GenArray2D** annotation allow you to generate two dimension array of any type library provides generators for. 
Arrays in classes annotated with *@GenAuto* will be generated automatically.

Arrays exports are supported only by **JsonExporter** in mean time.

### **Collection Annotations**

Collection annotations like: **GenList, GenSet, GenMap** used 
to populate fields with associated types.

Collection exports are supported only by **JsonExporter** in mean time.

### **Time Annotation**

**GenTime** annotation is used to create time/dateTime/timestamps for field.
Automatically identify field time *type* and generate value for it. 

**Supported time fields types**
* *LocalDate*
* *LocalTime*
* *LocalDateTime*
* *Date (java.util.Date)*
* *Timestamp (java.sql.Timestamp)*

### **Embedded Annotation**

*GenEmbedded* annotation used to mark complex object fields 
(like fields which also contain *Gen* annotations inside).

Annotation support **depth** parameter, which indicates maximum depth of the Dummy 
from its root and can have 20 levels as maximum.

Embedded fields are **NOT SUPPORTED** by any *IExporter* in mean time.

### **Special Annotations**

* ***GenExportForce*** allow to *force* export object field, even if it is not generated by *Gen*Annotation.

* ***GenExportIgnore*** allow to *ignore* object's field during export.

* ***GenExportRename*** allow to rename Dummy export field name or Class Name (Annotate constructor to rename class export name).

* ***GenIgnore*** field will not be filled with value when generated.

* ***GenEnum*** generates Enum field value.

* ***GenSequence*** annotation with option (*from*) used to numerate Dummies fields as sequence (Works on *Integer/Long/String* field types).

* ***GenCustom*** used to mark [field with custom generators](#gen-annotation).

## *Getting Started Examples*

### **Annotations Examples**

####  *Field annotate example*

Make sure that *Gen* annotation *generate type* is **same** or is **castable** to field type, or *field type* is **string** so in this case any object can be casted to *string*.

![](https://media.giphy.com/media/1FT9ZdjTrfzVe/giphy.gif)

#### *Auto Gen magic*

Just simple use *GenAuto* on class and library will do all for you.
All fields will be populated automatically if such generators are available.

Check *Auto Annotation* [part for details](#auto-annotation).

![](https://media.giphy.com/media/2fOt0kmS5Zk99CldyU/giphy.gif)

#### *Force and Ignore annotations*

In this case, field city will be export despite it isn't marked with *Gen* annotation, value will be "Saint-Petersburg".
And field *id* will **NOT** be export if *ignore* annotation will have *true* (*default*) value.

![](https://media.giphy.com/media/fGUhbzqFXGO18YDdmM/giphy.gif)

#### *Enumerate and Rename field example*

*GenEnumerate* annotation will enumerate Dummy field starting from 10 in this case (*from 0 is default*).
It means if we want to produce 10 Dummy Objects, they will have *id* from 10 to 19.

*GenRenameExport* annotation will change *field* or *class* export name.

![](https://media.giphy.com/media/1lAKHEbMtZ0ISV1HMp/giphy.gif)

#### *Class name Rename example*

*GenRenameExport* annotation will change **class** *export* name.in this case.

![](https://media.giphy.com/media/cdMPMc4OcXoBb0erSO/giphy.gif)

#### *Gen Time annotation example*

You can generate time/date values using this annotation.

Read more in *time annotation* section.

![](https://media.giphy.com/media/MuCzQ6BfY1Y1HrggsP/giphy.gif)

#### *Arrays annotation example*

You can populate single or two dimension array any generic type you want, if such *IGenerator* is available (or you can create your own generator).

Read more in *array annotation* section.

![](https://media.giphy.com/media/eB5X49oQHkClApV3PA/giphy.gif)

#### *Collection annotation example*

You can populate collections such as *Set, List, Map* with any generic type you want, if such *IGenerator* is available (or you can create your own generator).

Read more in *collection annotation* section.

![](https://media.giphy.com/media/8FrjAE955A2vTmxgal/giphy.gif)

#### *Collection parameters*

*List* will have from 50 to 100 elements and values will be generated by *NameGenerator*
*Set* will always have a fixed 13 elements (or less if hash codes will be the same for different objects)

![](https://media.giphy.com/media/3b8NNh405Y9ORC8NMa/giphy.gif)

#### *Embedded fields example*

You can populate complex object fields using *GenEmbedded* annotation.

In this case depth of embedded object field will be 5 levels.

And embedded list will have only 1 depth level.

![](https://media.giphy.com/media/kFezIHBLZ961ariUc7/giphy.gif)

### **Factories Examples**

*GenPopulateFactory/GenProvideFactory* this factories allow you to populate/produce Dummy objects.

#### *Produce 1 or more Dummy objects demonstration*
![](https://media.giphy.com/media/QmJ3rXQntaRYcgeLPM/giphy.gif)

#### *Populate 1 or more Dummy objects demonstration*

*GenPopulateFactory* will be useful in case, you already have complex objects and you want just to populate some of their fields.

![](https://media.giphy.com/media/1ffn6PiFgTQKaakZ8B/giphy.gif)

## Customization

You can extend basic functionality with your own annotations and generators. All infrastructure will support custom generators, annotations, generate factories with no doubt.

You need to:
* Create generator using *IGenerator* interface ir *IComplexGenerator*.
* Mark Dummy object field with **GenCustom** annotation and your generator as value.
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


### Complex Gen Annotation

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

### Gen Annotation

For custom *IGenerator*s or *IComplexGenerator*s that don't require special annotation
fields just use **@GenCustom** annotation that is provided by library to mark fields with your
custom generators.

```java
public class User {

    @GenCustom(CustomComplexGenerator.class)
    private String number;
}
```


## Version History

**2.0.0** - Unsigned int and byte, @Ignore annotation, @GenEnum enum generation, minor improvements.

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
