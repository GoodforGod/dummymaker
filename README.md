# DummyMaker   :hotsprings:

![](https://travis-ci.org/GoodforGod/dummymaker.svg?branch=master)
[![codecov](https://codecov.io/gh/GoodforGod/dummymaker/branch/master/graph/badge.svg)](https://codecov.io/gh/GoodforGod/dummymaker)

Library allow to produce Dummy objects (POJOs) via special *Factories* and populate their fields with values via special *Gen* annotations.
And also *export* them in **CSV/JSON/XML/SQL** formats.

Also it is possible to create your own *Gen* annotations and *IGenerator* generators to populate Dummy object fields in your way.

*Step by step guide:*
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

## Contents
- [Overall](#overall)
- [Factories](#factories)
- [Generators](#generators)
- [Export](#export)
  - [Basic Exporters Parameters](#basic-exporters-parameters)
  - [CsvExporter Specific Parameters](#csvExporter-specific-parameters)
  - [XmlExporter Specific Parameters](#xmlExporter-specific-parameters)
  - [SqlExporter Specific Parameters](#sqlExporter-specific-parameters)
- [Annotations](#annotations)
  - [Basic Gen Annotations](#basic-gen-annotations)  
  - [Collection Annotations](#collection-annotations)  
  - [Time Annotation](#time-annotation)  
  - [Special Annotations](#special-annotations)  
- [Getting Started with examples](#getting-started-with-examples)
  - [Annotations](#annotations)
  - [Factories](#factories)
  - [Exporters](#exporters)
- [Export File Structures](#export-file-structures)
  - [Dummy Object Class](#dummy-object-class)
  - [CSV](#csv)
  - [JSON](#json)
  - [XML](#xml)
  - [SQL](#sql)  
- [Version History](#version-history)

## Overall

Scheme how all is linked together:

Dummy object fields should be marked with special *Gen* annotations.

Each *Gen* annotation have special hidden *IGenerator* responsible for value generation.

When *GenPopulateFactory* is used, it scans for such annotations and use hidden generators to generate values for Dummy object fields.

Or special *IGenerateFactory* is used to build complex value (or value with *annotation attributes involved*) for specific *Gen* annotations like *GenList*.

Exporters use scanners to verify what fields to export and format values in chosen format.

## Factories

Factories to populate/produce Dummy Objects.

* ***GenProduceFactory*** - allow you to produce new Dummies with populated fields.

* ***GenPopulateFactory*** - allow you to populate fields of already created Dummies.

* ***IGenerateFactory*** - special factory interface used to build complex generate factories to build Dummy object field values, used by GenPopulateFactory.

You can create your own *IGenerateFactory* implementations as well for complex *Gen* annotations.

## Generators

*IGenerator* generators are the producers of random values of specific type.
Used by *GenPopulateFactory* to generate values for Dummy object fields.

Are part of *Gen* annotations cause indicate what generator each annotation is using.

## Export

Exporters allow you to export Dummy objects to the shown format via *file* or as a *string*.

### **Basic Exporters Parameters**

Constructor parameters available for all exporters.

* *withPath* - set path for export file, default directory where app is started.
* *withStrategy* - naming strategy applied to all origin fields (fields which are not *@GenRenameExport*), default value is *DEFAULT*. All strategies presets are in **Strategies** enum and inherit **IStrategy** interface.

	**Strategies**
	* *DEFAULT* - origin name, as is.
	* *UPPER_CASE* - name in upper case, like *DummyList - DUMMYLIST*
	* *LOW_CASE* - name in low case, like *DummyList - dummylist*
	* *UNDERSCORED_LOW_CASE* - name in upper case, with *_* symbol before each capital letter, like *DummyList - dummy_list*
	* *UNDERSCORED_UPPER_CASE* - name in low case, with *_* symbol before each capital letter, like *DummyList - dummy_list*
	* *INITIAL_LOW_CASE* - origin name, but first letter is low case, like *DummyList - dummyList*

### **CsvExporter Specific Parameters**
* *withWrap* - if true will wrap String values with commas like 'this', default *False*.
* *withHeader* - if true will generate CSV header, default *False*.
* *withSeparator* - set CSV format separator, default is '**,**' comma.

### **XmlExporter Specific Parameters**
* *withEnding* - export xml list name value (example: if class is Dummy, default list name will be DummyList).
* *withFullname* - full class export name. (class ending is not used in this case).

### **SqlExporter Specific Parameters**
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

Collection annotations are *not supported* by any exporter in mean time.

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
* *from* - minimum time generated time (*01.01.1970* is default) in long UTC format.
* *to* - maximum entities generated time (*01.01.3000* is default) in long UTC format.

### **Special Annotations**

* ***GenForceExport*** allow to *force* export object field, even if it is not generated by *Gen*Annotation.

* ***GenIgnoreExport*** allow to *ignore* object's field during export.

* ***GenRenameExport*** allow to rename Dummy export field name or Class Name (Annotate constructor to rename class export name).

* ***GenEnumerate*** annotation with option (*from*) to numerate populated/produced Dummies fields (Works on *Integer/Long/String* field types).

## *Getting Started Examples*

### **Annotations**

####  *POJO gen annotate example*

![](https://media.giphy.com/media/1FT9ZdjTrfzVe/giphy.gif)

#### *Force and Ignore annotations*

In this case, field city will be export despite it isn't marked with *Gen* annotation, value will be "Saint-Petersburg".
And field *id* will **NOT** be export if *ignore* annotation will have *true* (*default*) value.

![](https://media.giphy.com/media/3oKIP9McvYYBRw4S2I/giphy.gif)

#### *Enumerate and Rename field example*

*GenEnumerate* annotation will enumerate Dummy field starting from 10 in this case (*from 0 is default*).
It means if we want to produce 10 Dummy Objects, they will have *id* from 10 to 19.

*GenRenameExport* annotation will change *field* export name or *class* name.

![](https://media.giphy.com/media/FsKNHPlKtSEpO/giphy.gif)

#### *Class name Rename example*

*GenRenameExport* annotation will change *class* export name.in this case.

![](https://media.giphy.com/media/7iuQXqNdcnSLu/giphy.gif)

#### *Gen Time annotation example*

![](https://media.giphy.com/media/MuCzQ6BfY1Y1HrggsP/giphy.gif)

#### *Collection annotation example*

![](https://media.giphy.com/media/8FrjAE955A2vTmxgal/giphy.gif)

#### *Collection parameters*

![](https://media.giphy.com/media/1n4JPUg1rxwemngMhV/giphy.gif)


### **Factories**

*GenPopulateFactory/GenProvideFactory* this factories allow you to populate/produce Dummy objects.

#### *Produce 1 or more Dummy objects demonstration*
![](https://media.giphy.com/media/QmJ3rXQntaRYcgeLPM/giphy.gif)

#### *Populate 1 or more Dummy objects demonstration*

*GenPopulateFactory* will be useful in case, you already have complex objects and you want just to populate some of their fields.

![](https://media.giphy.com/media/1ffn6PiFgTQKaakZ8B/giphy.gif)

### **Exporters**

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

**1.1.0** - Performance and architecture improvements, *IGenerateFactory* introduced, collection *Gen* annotations, time *Gen* annotations, architecture improvements in custom user extension support.

**1.0.3** - Lots of tests for all functionality, Added *DataTypeMap* parameter for users in SqlExporter (expandable data type for sql), *NamingStrategy* for exporters, bug fixes.

**1.0.2** - Added special *GenRenameExport* annotation, export as single string, export values order fix, minor fixes and improvements.

**1.0.1** - Added new generator and annotations, special *GenEnumerate* annotation, other minor fixes (Like SQL export).

**1.0.0** - Initial project with core functions.

## License

This project is licensed under the Apache License - see the [LICENCE](LICENCE) file for details.
