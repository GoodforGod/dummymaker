# DummyMaker

[![Java minimum version library is compatible](https://img.shields.io/badge/Java-1.8%2B-blue?logo=openjdk)](https://openjdk.org/projects/jdk8/)
[![GitHub Action](https://github.com/goodforgod/dummymaker/workflows/Java%20CI/badge.svg)](https://github.com/GoodforGod/dummymaker/actions?query=workflow%3A%22Java+CI%22)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=alert_status)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=coverage)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=ncloc)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)

DummyMaker is a library that generates random Java Classes / Records for you.

Library can even do simple export in [*CSV/JSON/XML/SQL*](#export) formats.

## Dependency :rocket:

**Gradle**
```groovy
implementation "com.github.goodforgod:dummymaker:4.0.0"
```

**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>dummymaker</artifactId>
    <version>4.0.0</version>
</dependency>
```

## Content
- [GenFactory Examples](#factory-example)
  - [Methods](#factory-methods)
  - [Configuration](#gen-auto-annotation)
  - [GenRule](#gen-auto-annotation)
    - [Class](#auto-rule)
    - [Global](#global-rule)
- [GenAnnotation Examples](#annotation-examples)
  - [Standard](#array-annotations)
  - [Special](#special-annotations)
- [Export](#export)
  - [JSON](#export)
  - [CSV](#export)
  - [SQL](#export)
  - [XML](#export)
  - [Annotations](#export)
  - [Writer](#export)
- [Customization Examples](#customization)
  - [Simple Generator](#igenerator)
  - [Parameterized Generator](#icomplexgenerator)
  - [Custom Annotation](#gen-custom-annotation)
  - [Custom Annotation Factory](#gen-complex-annotation)
- [Previous Versions](#previous-versions)

## Factory Examples

*GenFactory* provides different methods for random class generation.

Given such Java Class as a model:
```java
class User {

  public String name;
  public String surname;
}
```

Example on how to generate **single** random Java Class with random field values:
```java
GenFactory factory = GenFactory.build();
User user = factory.build(User.class);
```

Example on how to generate **standard** Java Class (can be used with most of the standard Java Classes):
```java
GenFactory factory = GenFactory.build();
Integer integer = factory.build(Integer.class);
```

Example how to generate **list** of Java Classes:
```java
GenFactory factory = GenFactory.build();
List<User> users = factory.build(User.class, 1000);
```

Example how to generate **stream** of Java Classes:
```java
GenFactory factory = GenFactory.build();
factory.stream(User.class, 1000)
        .forEach(System.out::println);
```

In case have complex constructor, or it is required to generate on instantiated class, you can provide **Supplier** for factory:
```java
GenFactory factory = GenFactory.build();
User user = factory.build(() -> new User());
```

### Configuration

*GenFactory* Builder provides different methods to configure random class generation and generated data specifications.

Example how to configure *GenFactory*, check its *Builder* for more settings:
```java
class Account {
    
  public String type = "simple";
  public String name;
}

GenFactory factory = GenFactory.builder()
        .overrideDefaultValues(false)
        .applyCase(NamingCases.UPPER_CASE)
        .build();

Account account = factory.build(Account.class);
```

Available configurations:
- `Case` - apply naming case for *String* values like *SnakeCase, CamelCase, UpperCase, etc.*
- `Localisation` - choose language for generated *String* values (available *Russian & English*)
- `Seed` - set seed to use when selecting random generator for fields for constant results across executions
- `AutoByDefault` - generate field values for all classes by default (true by default)
- `DepthByDefault` - set maximum depth when generating random embedded classes
- `IgnoreExceptions` - ignore exceptions while generating field values (false by default)
- `OverrideDefaultValues` - generate random values for fields when field have Not Null value (true by default)

### GenRules

*GenFactory* allows to provide rules for configuring class generation for specific fields.

Rules can be used to configure specific generators for specific fields or exclude fields.

*GenRule* examples will be based on this class:
```java
class Account {

  public Integer number;
  public String type;
  public String name;
}
```

#### Class

Class specific Rule is applied **only** for specific Class.

Example how to add specific *Generator* for fields by their names:
```java
GenFactory factory = GenFactory.builder()
        .addRule(GenRule.ofClass(Account.class)
                .registerFieldNames(() -> new NameGenerator(), "type", "name"))
        .build();

Account account = factory.build(Account.class);
```

#### Global

Global Rule is applied for all Classes.

Example how to add specific *Generator* for all types:
```java
GenFactory factory = GenFactory.builder()
        .addRule(GenRule.ofGlobal()
                .registerFieldType(() -> new NameGenerator(), String.class))
        .build();

Account account = factory.build(Account.class);
```

## GenAnnotation Examples

Library provides lots of annotations to mark out class for random value generation.

This is an alternative to [GenRules](#genrules) mechanism for random value generation configuring.

Check [*io.dummymaker.annotation*](https://github.com/GoodforGod/dummymaker/tree/master/src/main/java/io/dummymaker/annotation) package for all of them.

### Standard

Using annotations like *GenEmail*, *GenId*, *GenName*, etc., it allows mark out which *Generator* to use for which field.

```java
class Account {

  @GenId
  private String id;
  @GenName
  private String name;
  @GenInt(from = 1, to = 10)
  private long number;
  @GenAddress
  private String address;
  @GenEmail
  private String email;
}

GenFactory factory = GenFactory.build();
Account account = factory.build(Account.class);
```

### Special

Library provides annotations that allow to provide special behavior when generating random values of typically used for complex values generation:
- GenSequence - generates sequence number value (when each individual field value should be incremented by 1).
- GenArray - generates random array value.
- GenArray2D - generates random double array value.
- GenEnum - generates random Enum value (should be annotated for Enum fields)
- GenList - generates random List value.
- GenSet - generates random Set value.
- GenMap - generates random Map value.
- GenTime - generates random Time value java.time.* package and old Java Data API.
- GenUnixTime - generates random UnixTime in millis.

Configuration annotations:
- GenIgnore - indicates that field will be excluded from random value generation.
- GenAuto - indicates that class should be Auto generated (generators swill be automatically selected if not marked out)
- GenDepth - configures maximum Depth when generating embedded class values.

```java
@GenAuto
@GenDepth(2)
class Account {

  @GenSequence
  private Long id;
  @GenTime
  private String timestamp;
  @GenUnixTime
  private long unixTime;
  @GenIgnore
  private String ignored;
  @GenList
  private List<String> emails;
  @GenSet(generator = TypeGenerator.class)
  private Set<String> types;
  @GenMap
  private Map<Integer, String> numbers;
  
  private Account boss;
}

GenFactory factory = GenFactory.build();
Account account = factory.build(Account.class);
```

## Export

Library provides simple *Exporter* classes to export objects in *CSV*, *JSON*, *XML* and even *SQL* formats.

Each *Exporter* provides different methods to export single Java class, list of classes or stream of classes.

*Exporter* have different configurations, like *naming* case for field names, also custom Writer can be supplied for file writing.

Examples will be based on this class:
```java
class Account {

  public Integer number;
  public String type;
  public String name;
}
```

### Annotations

Library provides special export annotations:
- GenExportIgnore - allow to *ignore* object's field during export.
- GenExportRename - allow to rename Dummy export Field Name.

### JSON

*JsonExporter* can export Java classes in *JSON* format.

Example:
```java
GenFactory factory = GenFactory.build();
List<Account> accounts = factory.build(Account.class, 2);

Exporter exporter = JsonExporter.build();
String json = exporter.exportAsString(accounts);
```

Result:
```json
[
  {"number":1657591124,"type":"invalid","name":"Palmer"},
  {"number":1243742023,"type":"rejected","name":"Cindy"}
]
```

### CSV

*CsvExporter* can export Java classes in *CSV* format.

Example:
```java
GenFactory factory = GenFactory.build();
List<Account> accounts = factory.build(Account.class, 2);

Exporter exporter = CsvExporter.build();
String csv = exporter.exportAsString(accounts);
```

Result:
```csv
number,type,name
1032136236,rejected,Mike
338683126,failed,Jesus
```

### XML

*XmlExporter* can export Java classes in *XML* format.

Example:
```java
GenFactory factory = GenFactory.build();
List<Account> accounts = factory.build(Account.class, 2);

Exporter exporter = XmlExporter.build();
String xml = exporter.exportAsString(accounts);
```

Result:
```xml
<AccountList>
    <Account>
        <number>189092582</number>
        <type>invalid</type>
        <name>Antonio</name>
    </Account>
    <Account>
        <number>58523878</number>
        <type>failed</type>
        <name>Carlos</name>
    </Account>
</AccountList>
```

### SQL

*SqlExporter* can export Java classes in *SQL* format.

This is useful when you need to insert millions rows into SQL database, such insert query is one of the fastest ways.

Example:
```java
GenFactory factory = GenFactory.build();
List<Account> accounts = factory.build(Account.class, 2);

Exporter exporter = SqlExporter.build();
String sql = exporter.exportAsString(accounts);
```

Result:
```sql
CREATE TABLE IF NOT EXISTS account(
	number	INT,
	type	VARCHAR,
	name	VARCHAR,
	PRIMARY KEY (number)
);

INSERT INTO account(number, type, name) VALUES
(1682341022, 'failed', 'Megan'),
(2052081057, 'invalid', 'Tina');
```

### Writer

When exporting to file, you can provide your own *Writer* implementation.

Example:
```java
GenFactory factory = GenFactory.build();
List<Account> accounts = factory.build(Account.class, 2);

Exporter exporter = JsonExporter.builder().withWriter(name -> new SimpleFileWriter(false, name)).build();
exporter.exportAsFile(accounts);
```

## Customization Examples

Library allows easily create custom *Generator* or annotations.

### Simple Generator

Simple *Generator* that produce some random value.

*Generator* can specify pattern that will be used choose where to apply such *Generator* based on field name.

```java
public class IntegerMyGenerator implements Generator<Integer> {

    private final Pattern pattern = Pattern.compile("age|grade|group", CASE_INSENSITIVE);

    @Override
    public Pattern pattern() {
        return pattern;
    }

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
```

### Parameterized Generator

*Generator* sometimes require more context to use for random value generation, than it is possible to implement *ParameterizedGenerator*.

```java
public final class MyParameterizedGenerator implements ParameterizedGenerator<Integer> {

    @Override
    public Object get(@NotNull GenParameters parameters) {
        return parameters.namingCase.apply("myValue");
    }

    @Override
    public Object get() {
        return "myValue";
    }
}
```

### Custom Annotation

You can apply custom *Generator* not only using [GenRules](#genrules) but also using *@GenCustom* annotation.

*@GenCustom* annotation require *Generator* 

```java
class Account {

  @GenCustom(IntegerMyGenerator.class)
  public String type;
}
```

### Custom Annotation Factory







```java
public class IntegerSmallGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("age|grade|group", CASE_INSENSITIVE);

    @Override
    public Pattern pattern() {
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









## Factory example Old

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
    final GenFactory factory = GenFactory.build();

    User user = factory.build(User.class);

    // All users have unique data in their fields
    List<User> users = factory.build(User.class, 1000);
    
    return users;
}
```

### Factory Methods

Factory not only instantiate classes but also provides other
contracts to manipulate with objects like using supplier.

```java
final GenFactory factory = GenFactory.build();

Set<User> users = factory.stream(() -> new User(), 1)
                            .collect(Collectors.toSet());

User user = new User();
User filled = factory.fill(user);
```

There are more other contracts available just check *GenFactory*.

Also, factory can export huge amount of data in one attempt when data cannot be proceeded inmemory via *export* contract.

In such case export that is provided *should* append file with each export execution. 
Default *IWriter* doesn't do that by default, so such option should be activated.

```java
final GenFactory factory = GenFactory.build();
final JsonExporter exporter = new JsonExporter(fileName -> new FileWriter(fileName, false)); // do not delete file before write

factory.export(User.class, 100_000_000_000L, exporter);
```

### Gen Auto Annotation

*@GenAuto* annotation provides *depth* field that is required for embedded
fields such as *relatives* in this example.

For such class, each *User* will have field *relatives* filled with data
up to level *3* in depth.
Because each User will have other users as its data and such data can be represented
as a tree. So leaf that has its depth of *3* from root will be the last who have
field *relatives* filled with data.

Maximum depth allowed is *20* (Check @GenAuto.MAX parameter).

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
and fields you want to fill or ignore with factory *GenRules* configuration.

### Manual Rule

In case you want to fill only specific field to be filled with data
you can configure such behavior via *GenRule* for specific class.

```java
GenRule rule = GenRule.of(User.class)
                .add(NameGenerator.class, "name")
                .add(ShortGenerator.class, int.class);

GenFactory factory = new GenFactory(rule);
User user = factory.build(User.class);
```

In this case only *name* and *number* fields will be filled with data, 
as they are the only one specified per *GenRule* configuration.

### Auto Rule

In case you want factory automatically fill fields based on their types
and names, you can setup *auto* (same as *GenAuto* annotation).

Second argument specifies depth of complex objects generation, [check this section for more info](#gen-auto-annotation).

```java
GenRule rule = GenRule.auto(User.class, 2)
                .add(NameGenerator.class, "name")
                .add(ShortGenerator.class, int.class);

GenFactory factory = new GenFactory(rule);
User user = factory.build(User.class);
```

In this case fields *name* and *number* will be filled as previously but
also all other fields will be automatically filled.

```java
GenRule rule = GenRule.auto(User.class, 2);

GenFactory factory = new GenFactory(rule);
User user = factory.build(User.class);
```

This will have same affect as previous , due to fields *name* and *number*
been automatically filled.

### Global Rule

In case you have a lot of common fields with same values in different classes
you can setup *global*  for all classes that will be filled.

```java
GenRule rule = GenRule.global(2)
                .add(NameGenerator.class, "name")
                .add(ShortGenerator.class, int.class);

GenFactory factory = new GenFactory(rule);
User user = factory.build(User.class);
```

In this case all fields with name *name* in all classes will be
filled using *NameGenerator* and same for *int* fields with *ShortGenerator*.

### Lambda Rule

You can add anonymous generator or lambda generator as run for your *GenRules* configuration.

```java
final IGenerator<String> generator = () -> ThreadLocalRandom.current().nextBoolean()
        ? "Bob"
        : "Bill";

GenRule rule = GenRule.auto(User.class, 1)
                .add(generator, "name");

GenFactory factory = new GenFactory(rule);
User user = factory.build(User.class);
```

## Annotation Examples

There are a lot of generators available such as *IntegerGenerator, LongGenerator, CityGenerator, etc.*

All generators can be found in package *io.dummymaker.generator*.

As well as all annotations such as *GenInteger, GenLong, GenCity, etc.* can be found in package *io.dummymaker.annotation.simple*.

### Array Annotations

Responsible for filling array data, can be applied as annotation or
[*GenRule*](#manual-rule). Will be used automatically via [*@GenAuto*](#gen-auto-annotation) or auto [*GenRule*](#auto-rule).


```java
public class User {

    @GenArray
    private int[] numbers;
    
    @GenArray
    private int[][] numberArrays;
}
```


### Collection Annotations

Responsible for filling collection data, can be applied as annotation or
[*GenRule*](#manual-rule). Will be used automatically via [*@GenAuto*](#gen-auto-annotation) or auto [*GenRule*](#auto-rule).


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

Responsible for filling time data, can be applied as annotation or
[*GenRule*](#manual-rule). Will be used automatically via [*@GenAuto*](#gen-auto-annotation) or auto [*GenRule*](#auto-rule).

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


#### Date Time Formatter

You cas specify formatter to export dates \ times.

```java
public class User {

    @GenTime(formatter = "HH:mm")
    private LocalTime localTime;
    
    @GenTime(formatter = "yyyy-MM HH:mm")
    private LocalDateTime dateTime;
}
```

#### Date Time As Unix Time

You can export dates \ times as unix time format.

```java
public class User {

    @GenTime(exportAsUnixTime = true)
    private Timestamp timestamp;
    
    @GenTime(exportAsUnixTime = true)
    private LocalDateTime dateTime;
}
```

### Embedded Annotation

Responsible for filling complex data, can be applied as annotation or
[*GenRule*](#manual-rule). Will be used automatically via [*@GenAuto*](#gen-auto-annotation) or auto [*GenRule*](#auto-rule).

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

* ***GenCustom*** used to mark [field with custom generators](#gen-custom-annotation).

## Export

Information about different exporters can be [found here](/README-EXPORT.md).

## Customization

You can extend basic functionality with your own annotations and generators. All infrastructure will support custom generators, annotations, generate factories with no doubt.

You need to:
* Create generator via *IGenerator* or *IComplexGenerator* interface.
* Annotated class field with **GenCustom** (or your annotation) with your generator.
* *Magic*.
* Done.

### IGenerator

Simple generator that produce specific value.

Generator can have pattern to register it for *@GenAuto* discovery.

```java
public class IntegerSmallGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("age|grade|group", CASE_INSENSITIVE);

    @Override
    public Pattern pattern() {
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

## Previous Versions

Documentation for **versions 3.X.X** in [this document](/README-VERSION-3.X.md).

Documentation for **versions 1.X.X** in [this document](/README-VERSION-1.X.md).

## License

This project licensed under the MIT - see the [LICENSE](LICENSE) file for details.
