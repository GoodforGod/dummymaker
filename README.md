# DummyMaker   :hotsprings:

![](https://travis-ci.org/GoodforGod/dummymaker.svg?branch=master)
[![Maintainability](https://api.codeclimate.com/v1/badges/c180e591ba7558c3add2/maintainability)](https://codeclimate.com/github/GoodforGod/dummymaker/maintainability)
[![codecov](https://codecov.io/gh/GoodforGod/dummymaker/branch/master/graph/badge.svg)](https://codecov.io/gh/GoodforGod/dummymaker)

Library can produce Dummy objects (POJOs) using special *factories*, *populate* their fields with values by special *Gen* annotations and *generators*, *export* them in **CSV/JSON/XML/SQL** formats.

Also it is possible extend library functionality by creating your own *Gen* annotations, *IGenerator* generators, *IGenerateFactories* factories to populate Dummy object fields *in your way*.

*Step by step guide how to produce and export your first Dummy:*
1) Create Dummy object (POJO). 
2) *Annotate* Dummy object fields with special *Gen* annotations.
3) Use *Factory* to populate/produce Dummy Object[s].
4) Export Dummy Objects by using special *Exporter*.

![](https://media.giphy.com/media/1msHfmVdtuwkXww4ZC/giphy.gif)

## Dependency :rocket:
**Maven**
```xml
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>dummymaker</artifactId>
    <version>1.1.0</version>
</dependency>
```

**Gradle**
```groovy
dependencies {
    compile 'com.github.goodforgod:dummymaker:1.1.0'
}
```

## Content
- [Overall](#overall)
- [Factories](#factories)
- [Generators](#generators)
- [Exporters](#exporters)
  - [Basic Exporters Parameters](#basic-exporters-parameters)
  - [CsvExporter Parameters](#csvexporter-parameters)
  - [XmlExporter Parameters](#xmlexporter-parameters)
  - [SqlExporter Parameters](#sqlexporter-parameters)
- [Annotations](#annotations)
  - [Basic Gen Annotations](#basic-gen-annotations)  
  - [Collection Annotations](#collection-annotations)  
  - [Time Annotation](#time-annotation)  
  - [Special Annotations](#special-annotations)  
- [Customization](#customization)
  - [IGenerator](#igenerator)
  - [Gen Annotation](#gen-annotation)
  - [IGenerateFactory](#igeneratefactory)
- [Getting Started with examples](#getting-started-with-examples)
  - [Annotations](#annotations-examples)
  - [Factories](#factories-examples)
  - [Exporters](#exporters-examples)
- [Export File Structures](#export-file-structures)
  - [Dummy Object Class](#dummy-object-class)
  - [CSV](#csv)
  - [JSON](#json)
  - [XML](#xml)
  - [SQL](#sql)  
- [Version History](#version-history)

## Overall

How all is linked together:

Dummy object fields should be marked with special *Gen* annotations.

Each *Gen* annotation have special hidden *IGenerator* responsible for value generation.

When *GenPopulateFactory* is used, it scans for such annotations and use hidden generators to generate values for Dummy object fields.

Or special *IGenerateFactory* is used to build complex value (or value with *annotation attributes involved*) for specific *Gen* annotations like *GenList*.

Exporters use scanners to verify what fields to export and format values in chosen format.

## Factories

Factories to populate/produce Dummy Objects.
Factory is responsible for field population.

* ***GenProduceFactory*** - allow you to produce new Dummies with populated fields.

* ***GenPopulateFactory*** - allow you to populate fields of already created Dummies.

* ***IGenerateFactory*** - special factory interface used to build complex generate factories to build Dummy object field values, used by GenPopulateFactory.

You can create your own *IGenerateFactory* implementations as well for complex *Gen* annotations.

## Generators

*IGenerator* generators are the producers of random values of specific type.
Used by *GenPopulateFactory* to generate values for Dummy object fields.

Are part of *Gen* annotations cause indicate what generator each annotation is using.

## Exporters

*IExporter* exporters allow you to export Dummy objects to the shown format via *file* or as a *string*.

### **Basic Exporters Parameters**

Constructor parameters available for all exporters.

* *withPath* - set path for export file, default directory is app home.
* *withCase* - naming case applied to all export fields (excluding *GenRenameExport*), default value is *DEFAULT*. All cases presets are in **Cases** enum and inherit **ICase** interface.

	**Cases**:
	* *DEFAULT* - name as is.
	* *LOW_CASE* - name in low case (like *DummyList - dummylist*)
	* *UPPER_CASE* - name in upper case (like *DummyList - DUMMYLIST*)
	* *CAMEL_CASE* - name as is, but first letter is low case (like *DummyList - dummyList*)
	* *PASCAL_CASE* - name as is, but first letter is upper case (like *DummyList - dummyList*)
	* *SNAKE_CASE* - name in low case, with *_* symbol before each capital letter (like *DummyList - dummy_list*)
	* *UPPER_SNAKE_CASE* - name in upper case, with *_* symbol before each capital letter (like *DummyList - DUMMY_LIST*)
    * *KEBAB_CASE* - name in low case, with *-* symbol before each capital letter (like *DummyList - dummy_list*)
    * *UPPER_KEBAB_CASE* - name in upper case, with *-* symbol before each capital letter (like *DummyList - DUMMY_LIST*)

### **CsvExporter Parameters**
* *withWrap* - if true will wrap String values with commas like 'this', default *False*.
* *withHeader* - if true will generate CSV header, default *False*.
* *withSeparator* - set CSV format separator, default is '**,**' comma.

### **XmlExporter Parameters**
* *withName* - class custom export name value. (class ending is not used in this case).

### **SqlExporter Parameters**
* *withTypes* - map with *key* as a class, and sql data type as string as map *value*.

*DataTypeMap* is used to extend your data types to export in sql format.

## Annotations

It is easily for you to create custom *Gen* annotations and *IGenerator* generators.

### **Basic Gen Annotations**

*Gen* annotations allow you to mark Dummy fields and tell *GenPopulateFactory* to fill this fields with randomly generated values.

This annotations hide inside itself specified *IGenerator* class which is responsible for value generation.

Generate annotations start with *Gen* prefix (like *GenInteger, GenEmail, GenId, etc*).

### **Collection Annotations**

Collection annotations like: **GenList, GenSet, GenMap** used to populate fields with such types.
*GenList* - produce *ArrayList* collection.
*GenSet* - produce *HashSet* collection.
*GenMap* - produce *HashMap* collection.

Collection annotations are **NOT SUPPORTED** by any *exporter* in mean time.

Annotations support special attributes like:
* *min* - minimum entities generated amount.
* *max* - maximum entities generated amount.
* *fixed* - fixed number entities generated amount.
* *generator* - *IGenerator* generator class to build values using it.

This attributes are used by *GenMap* annotation only (instead of *generator* attribute):
* *key* - *IGenerator* generator class to build map *keys* using it.
* *value* - *IGenerator* generator class to build map *values* using it.

### **Time Annotation**

**GenTime** annotation is used to create time/dateTime/timestamps for field.
Automatically identify field time *type* and generate value for it. 

**Supported time fields types**
* *LocalDate*
* *LocalTime*
* *LocalDateTime*
* *Date (java.util.Date)*
* *Timestamp (java.sql.Timestamp)*

Annotations support special attributes like:
* *from* - minimum generated time (*01.01.1970* is default) in long UTC format.
* *to* - maximum generated time (*01.01.3000* is default) in long UTC format.

### **Special Annotations**

* ***GenForceExport*** allow to *force* export object field, even if it is not generated by *Gen*Annotation.

* ***GenIgnoreExport*** allow to *ignore* object's field during export.

* ***GenRenameExport*** allow to rename Dummy export field name or Class Name (Annotate constructor to rename class export name).

* ***GenEnumerate*** annotation with option (*from*) to numerate populated/produced Dummies fields (Works on *Integer/Long/String* field types).

* ***GenEmbedded*** annotation should mark complex object fields which also contain *Gen* annotations inside (There is no recursion, only one step down in hierarchy).

Embedded fields are **NOT SUPPORTED** by any *exporter* in mean time.

## *Getting Started Examples*

### **Annotations Examples**

####  *Field annotate example*

Make sure that *Gen* annotation *generate type* is **same** or is **castable** to field type, or *field type* is **string** so in this case any object can be casted to *string*.

![](https://media.giphy.com/media/1FT9ZdjTrfzVe/giphy.gif)

#### *Force and Ignore annotations*

In this case, field city will be export despite it isn't marked with *Gen* annotation, value will be "Saint-Petersburg".
And field *id* will **NOT** be export if *ignore* annotation will have *true* (*default*) value.

![](https://media.giphy.com/media/3oKIP9McvYYBRw4S2I/giphy.gif)

#### *Enumerate and Rename field example*

*GenEnumerate* annotation will enumerate Dummy field starting from 10 in this case (*from 0 is default*).
It means if we want to produce 10 Dummy Objects, they will have *id* from 10 to 19.

*GenRenameExport* annotation will change *field* or *class* export name.

![](https://media.giphy.com/media/FsKNHPlKtSEpO/giphy.gif)

#### *Class name Rename example*

*GenRenameExport* annotation will change **class** *export* name.in this case.

![](https://media.giphy.com/media/7iuQXqNdcnSLu/giphy.gif)

#### *Gen Time annotation example*

You can generate time/date values using this annotation.

Read more in *time annotation* section.

![](https://media.giphy.com/media/MuCzQ6BfY1Y1HrggsP/giphy.gif)

#### *Collection annotation example*

You can populate collections such as *Set, List, Map* with any generic type you want, if such *IGenerator* is available (or you can create your own generator).

Read more in *collection annotation* section.

![](https://media.giphy.com/media/8FrjAE955A2vTmxgal/giphy.gif)

#### *Collection parameters*

![](https://media.giphy.com/media/1n4JPUg1rxwemngMhV/giphy.gif)

#### *Embedded fields example*

You can populate complex object fields using *GenEmbedded* annotation.

![](https://media.giphy.com/media/uTOSaDeSbAJq4soFt2/giphy.gif)

### **Factories Examples**

*GenPopulateFactory/GenProvideFactory* this factories allow you to populate/produce Dummy objects.

#### *Produce 1 or more Dummy objects demonstration*
![](https://media.giphy.com/media/QmJ3rXQntaRYcgeLPM/giphy.gif)

#### *Populate 1 or more Dummy objects demonstration*

*GenPopulateFactory* will be useful in case, you already have complex objects and you want just to populate some of their fields.

![](https://media.giphy.com/media/1ffn6PiFgTQKaakZ8B/giphy.gif)

### **Exporters Examples**

Exporters allow you to export Dummy objects to shown format as a *file* or *string*.

Available formats:
- [CSV](#csv)
- [JSON](#json)
- [XML](#xml)
- [SQL](#sql)

#### *Export demonstration*

![](https://media.giphy.com/media/9JgcqumizCKFYMt8tm/giphy.gif)

#### *Exporters with parameters*

All *Exporters* parameters you can find in specified section.

![](https://media.giphy.com/media/u47tJEILiglFtyM2Yy/giphy.gif)

#### *Export as a string*

*Export as string* is useful in case you have custom writer or need to send it over network.

![](https://media.giphy.com/media/kS8R51TFsdCw2Agv97/giphy.gif)

## Customization

You can extend basic functionality with your own annotations and generators. All infrastructure will support custom generators, annotations, generate factories with no doubt.

The library works this way:
* You create generators using *IGenerator* interface.
* You create custom *Gen* annotation using *PrimeGen* annotation.
* You mark Dummy object field with such annotation.
* *Magic*.
* Done.

### IGenerator

Is responsible for generating values for fields.

![](https://media.giphy.com/media/FEXNa199DiR2GK1ekF/giphy.gif)

### Gen Annotation

Is created using special *PrimeGen* annotation and custom generator as its value.

Is used to mark Dummy object field with specific generator.

![](https://media.giphy.com/media/7SZufvmQLuosppw7nc/giphy.gif)

### IGenerateFactory

Is used to build complex values for fields, when simple *IGenerator* implementation is insufficient or *Gen* annotation require special parameters. 

![](https://media.giphy.com/media/5bgGzRo5svjQQJDkhU/giphy.gif)

## Export File Structures

Examples of exported Dummy object in each format.

### Dummy Object Class
```java
public class User {

    @GenInteger
    public Integer id;

    @GenName
    public String name;
}
```

### *CSV*

Can be used to import data in ***Cassandra, Mongo, Neo4j, etc...*** 

Csv exporter can generate *header*. (Like in example below)
This option is available to set during instantiating like others.

```csv
name,id
ERASMO,1746885991
HELEN,-625322461
```

### *JSON*

Can be used to import data in ***Mongo, MySQL, etc...***

```json
{
	"User": [
		{
			"name": "GREGORY",
			"id": "-2123372253"
		},
		{
			"name": "HAROLD",
			"id": "-1637387700"
		}
	]
}
```

### *XML*

Can be used to import data in ***MySQL, SQL Server, etc...***

```xml
<UserList>
	<User>
		<name>GREGORY</name>
		<id>-2123372253</id>
	</User>
	<User>
		<name>HAROLD</name>
		<id>-1637387700</id>
	</User>
</UserList>
```

### *SQL*

Don't forget about **Primary Key**!

Each insert query can contains max ***999*** rows (Due to ***1000*** insert row limit in *SQL*).

```sql
CREATE TABLE IF NOT EXISTS user(
	name	VARCHAR,
	id  INT,
	PRIMARY KEY (id)
);

INSERT INTO user (name, id) VALUES 
('GREGORY', -2123372253),
('HAROLD', -1637387700);
```

## Version History

**1.1.0** - Performance and architecture improvements, *IGenerateFactory* introduced, collection *Gen* annotations, time *Gen* annotations, Embedded *Gen* support, architecture improvements in custom user extension support.

**1.0.3** - Lots of tests for all functionality, Added *DataTypeMap* parameter for users in SqlExporter (expandable data type for sql), *NamingStrategy* for exporters, bug fixes.

**1.0.2** - Added special *GenRenameExport* annotation, export as single string, export values order fix, minor fixes and improvements.

**1.0.1** - Added new generator and annotations, special *GenEnumerate* annotation, other minor fixes (Like SQL export).

**1.0.0** - Initial project with core functions.

## License

This project is licensed under the MIT - see the [LICENSE](LICENSE) file for details.
