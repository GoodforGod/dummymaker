# DummyMaker

[![Minimum required Java version](https://img.shields.io/badge/Java-1.8%2B-blue?logo=openjdk)](https://openjdk.org/projects/jdk8/)
[![GitHub Action](https://github.com/goodforgod/dummymaker/workflows/Java%20CI/badge.svg)](https://github.com/GoodforGod/dummymaker/actions?query=workflow%3A%22Java+CI%22)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=alert_status)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=coverage)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_dummymaker&metric=ncloc)](https://sonarcloud.io/dashboard?id=GoodforGod_dummymaker)

DummyMaker is a library that generates random Java Classes / Records / Sealed Interfaces / Sealed Classes for you.

Library can even do simple export in [*CSV/JSON/XML/SQL*](#export) formats.

Library have no dependencies.

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
- [GenFactory Examples](#factory-examples)
  - [Configuration](#configuration)
  - [GenRule](#genrules)
    - [Class](#class)
    - [Global](#global)
- [GenAnnotation Examples](#genannotation-examples)
  - [Standard](#standard)
  - [Special](#special)
- [Generators](#generators)
- [Export](#export)
  - [Annotations](#annotations)
  - [JSON](#json)
  - [CSV](#csv)
  - [SQL](#sql)
  - [XML](#xml)
  - [Writer](#writer)
- [Customization Examples](#customization-examples)
  - [Simple Generator](#simple-generator)
  - [Parameterized Generator](#parameterized-generator)
  - [Custom Annotation](#custom-annotation)
  - [Custom Annotation Factory](#custom-annotation-factory)
- [Previous Versions](#previous-versions)

## When to use?

There are plenty of use cases where *DummyMaker* is useful:
- Generating a random data to populate database
- Generating a random data to for tests
- Generating a high number of data to load-test a web service
- Generating a high number of data to text file (CSV, XML, JSON, SQL, etc)
- etc..

Simple example on how you would create class with random data using constructor *manually*:
```java
Street street = new Street(12, 1, "Revolution Sq");
Address address = new Address(street, "123456", "Saint-Petersburg");
Person person = new Person("Foo", "Bar", "foo.bar@ya.ru", address);
```

And if classes do not provide constructors with parameters:
```java
Street street = new Street();
street.setNumber(12);
street.setType(1);
street.setName("Revolution Sq");

Address address = new Address();
address.setStreet(street);
address.setZipCode("123456");
address.setCity("Saint-Petersburg");

Person person = new Person();
person.setFirstName("Foo");
person.setLastName("Bar");
person.setEmail("foo.bar@ya.ru");
person.setAddress(address);
```

Using *DummyMaker* library, it is easy to generate *Person* class with all inner classes with random data for all fields:
```java
GenFactory factory = GenFactory.build();
Person person = factory.build(Person.class);
```

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

Example on how to generate specific random value for any field:
```java
List<String> values = Arrays.asList("1", "5", "9");

GenFactory factory = GenFactory.builder()
        .addRule(GenRule.ofClass(Account.class)
                .generateForNames("type", () -> CollectionUtils.random(values)))
        .build();

Account account = factory.build(Account.class);
```

#### Class

Class specific Rule is applied **only** for specific Class.

Example how to add specific *Generator* for fields by their names:
```java
GenFactory factory = GenFactory.builder()
        .addRule(GenRule.ofClass(Account.class)
                .generateForNames("type", "name", () -> new NameGenerator()))
        .build();

Account account = factory.build(Account.class);
```

#### Global

Global Rule is applied for all Classes.

Example how to add specific *Generator* for all types:
```java
GenFactory factory = GenFactory.builder()
        .addRule(GenRule.ofGlobal()
                .generateForTypes(String.class, () -> new NameGenerator()))
        .build();

Account account = factory.build(Account.class);
```

## GenAnnotation Examples

Library provides lots of annotations to mark out class for random value generation.

This is an alternative to [GenRules](#genrules) mechanism for random value generation configuring.

Check [*io.dummymaker.annotation*](https://github.com/GoodforGod/dummymaker/tree/master/src/main/java/io/dummymaker/annotation) package for all of them.

### Standard

Using annotations like `@GenEmail`, `@GenId`, `@GenName`, etc., it allows mark out which *Generator* to use for which field.

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
- `@GenSequence` - generates sequence number value (when each individual field value should be incremented by 1).
- `@GenArray` - generates random array value.
- `@GenArray2D` - generates random double array value.
- `@GenEnum` - generates random Enum value (should be annotated for Enum fields)
- `@GenList` - generates random List value.
- `@GenSet` - generates random Set value.
- `@GenMap` - generates random Map value.
- `@GenTime` - generates random Time value java.time.* package and old Java Data API.
- `@GenUnixTime` - generates random UnixTime in millis.

Configuration annotations:
- `@GenIgnore` - indicates that field will be excluded from random value generation.
- `@GenAuto` - indicates that class should be Auto generated (generators swill be automatically selected if not marked out)
- `@GenDepth` - configures maximum Depth when generating embedded class values.

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

## Generators

Library provides lots of generators:

<details>
<summary>String Generators</summary>

```java
AddressFullGenerator.java
AddressGenerator.java
BtcAddressGenerator.java
BtcTxHashGenerator.java
CadastralGenerator.java
CategoryGenerator.java
CityGenerator.java
CompanyGenerator.java
CountryGenerator.java
CurrencyGenerator.java
DescriptionGenerator.java
DistrictGenerator.java
DocumentGenerator.java
EmailGenerator.java
EthAddressGenerator.java
EthTxHashGenerator.java
ExtensionGenerator.java
FileGenerator.java
FormatGenerator.java
FrequencyGenerator.java
FullnameGenerator.java
GenderGenerator.java
HexDataGenerator.java
HexNumberGenerator.java
HouseGenerator.java
IdBigGenerator.java
IdGenerator.java
IPv4Generator.java
IPv6Generator.java
JobGenerator.java
JsonGenerator.java
LevelGenerator.java
LoginGenerator.java
MccGenerator.java
MerchantGenerator.java
MiddleNameGenerator.java
NameGenerator.java
NounGenerator.java
PasswordGenerator.java
PhoneGenerator.java
PhotoGenerator.java
ProductGenerator.java
RoleGenerator.java
StatusGenerator.java
StreetGenerator.java
StringGenerator.java
StringValuesGenerator.java
SurnameGenerator.java
TagGenerator.java
TypeGenerator.java
VersionGenerator.java
```

</details>

<details>
<summary>Number Generators</summary>

```java
BigDecimalGenerator.java
BigIntegerGenerator.java
ByteGenerator.java
CharacterGenerator.java
CharGenerator.java
DoubleGenerator.java
DoubleSmallGenerator.java
FloatGenerator.java
FloatSmallGenerator.java
IntegerGenerator.java
IntegerSmallGenerator.java
LongGenerator.java
MccGenerator.java
PostalGenerator.java
PriceGenerator.java
SequenceGenerator.java
ShortGenerator.java
UnixTimeGenerator.java
```

</details>

<details>
<summary>Time Generators</summary>

```java
CalendarGenerator.java
DateGenerator.java
DateSqlGenerator.java
DayOfWeekGenerator.java
DurationGenerator.java
InstantGenerator.java
LocalDateGenerator.java
LocalDateTimeGenerator.java
LocalTimeGenerator.java
MonthDayGenerator.java
MonthGenerator.java
OffsetDateTimeGenerator.java
OffsetTimeGenerator.java
PeriodGenerator.java
TimeSqlGenerator.java
TimestampGenerator.java
YearGenerator.java
YearMonthGenerator.java
ZonedDateTimeGenerator.java
ZonedOffsetGenerator.java
```

</details>

<details>
<summary>Other Generators</summary>

```java
BooleanGenerator.java
EmbeddedGenerator.java
NullGenerator.java
ObjectGenerator.java
UriGenerator.java
UrlGenerator.java
UuidGenerator.java
```

</details>

<details>
<summary>Parameterized Generators</summary>

```java
Array2DParameterizedGenerator.java
ArrayParameterizedGenerator.java
EnumParameterizedGenerator.java
ListParameterizedGenerator.java
MapParameterizedGenerator.java
SetParameterizedGenerator.java
TimeParameterizedGenerator.java
```

</details>

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
- `@GenExportIgnore` - allow to *ignore* object's field during export.
- `@GenExportRename` - allow to rename Dummy export Field Name.

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
public final class MyParameterizedGenerator implements ParameterizedGenerator<String> {

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply("myValue");
    }

    @Override
    public String get() {
        return "myValue";
    }
}
```

### Custom Annotation

You can apply custom *Generator* not only using [GenRules](#genrules) but also using `@GenCustom` annotation.

`@GenCustom` annotation require *Generator* 

```java
class Account {

  @GenCustom(IntegerMyGenerator.class)
  public String type;
}
```

### Custom Annotation Factory

*AnnotationGeneratorFactory* is used when you need to instantiate generate with arguments from annotation.

*AnnotationGeneratorFactory* must have zero argument constructor.

`@GenCustomFactory` is used to indicate which factory for which annotation to use.

Given annotation:
```java
@GenCustomFactory(CustomIntegerAnnotationGeneratorFactory.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenIntCustom {

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    int from() default 0;

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    int to() default Integer.MAX_VALUE;
}
```

Respected *AnnotationGeneratorFactory* should look like:
```java
public final class IntegerAnnotationGeneratorFactory implements CustomIntegerAnnotationGeneratorFactory<GenIntCustom> {

    @Override
    public @NotNull Generator<Integer> get(GenIntCustom annotation) {
        return new IntegerGenerator(annotation.from(), annotation.to());
    }
}
```

## Previous Versions

Documentation for **versions 3.X.X** in [this document](/README-VERSION-3.X.md).

Documentation for **versions 1.X.X** in [this document](/README-VERSION-1.X.md).

## License

This project licensed under the MIT - see the [LICENSE](LICENSE) file for details.
