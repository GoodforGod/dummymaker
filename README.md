# DummyMaker :hotsprings:

Library allow you to produce Dummy Objects (POJOs) by using *Factories* and populate their fields with random values by using *Annotations*.
And then *Export* them in **CSV/JSON/XML/SQL** format.

Steps to do:
1) *Annotate* your Dummy Object fields with special *Gen* annotations.
2) Use *Factory* to populate/produce you Dummy Objects.
3) Export your Dummy Objects by using *Exporter*.

## Install :rocket:
Get via **Maven**
```
<dependency>
    <groupId>com.github.goodforgod</groupId>
    <artifactId>dummymaker</artifactId>
    <version>1.0.1</version>
</dependency>
```

Get via **Gradle**
```
dependencies {
    compile 'com.github.goodforgod:dummymaker:1.0.1'
}
```

## Methods

### **Factories**

Allow user populate or produce Dummy Object(s).

* *IProduceFactory* - allow you to produce new Dummies (**produce** methods)

* *IPopulateFactory* - allow you to populate your Dummies fields (**populate** methods)

### **Exporter**

Allow you to export your Dummies.

* *IExporter* - export contracts.

* ***{CSV/XML/JSON/SQL}** Exporter* - IExporter implementations.

## *Getting Started with examples*

### ***Annotations***

Allow your declare Dummy fields with generator annotations.
Annotations will generate values during by using *factories*.

You can use special annotations like ***GenForceExport*** or ***GenIgnoreExport*** to *force/ignore* export object's field.

Also you can use annotations ***GenNumerate*** with option (*from*) to numerate populated/produced Dummies fields.


#### 
![](https://media.giphy.com/media/xUA7aPwZO871rrTkT6/giphy.gif)

### ***IPopulateFactory/IProvideFactory***

Allow user populate or produce Dummy Object(s).

#### *You can produce 1 or more dummies.*

![](https://media.giphy.com/media/r2q6kaeasKRQ4/giphy.gif)

#### *You can manually populate object.*

![](https://media.giphy.com/media/cajTTyUltm9qg/giphy.gif)

#### *You can manually populate list of objects.*

![](https://media.giphy.com/media/10r4FUKdJQuSxW/giphy.gif)

### ***IExporter/IPrimeExporter***

#### 

![](https://media.giphy.com/media/f1jM0efW6WpY4/giphy.gif)

#### *Export **CSV/XML/JSON/SQL** Demonstration*

![](https://media.giphy.com/media/xUA7aPXaWZENNUGXbq/giphy.gif)

#### 
![](https://media.giphy.com/media/g3efqXIblykCs/giphy.gif)

## Export File Structures

Examples of exported Dummies in each format.

### Test Class
```
public class TestCaseClass {

    @GenDouble
    private String name;

    @GenDouble
    private Double value;
    
    /* ... getters and setters ... */
    
}
```

### *CSV*

Can be used to import data in ***Cassandra, Mongo, Neo4j, etc...*** 

```
0.03967295259868164,0.30227242830001944
0.8408601054584944,0.7401367963737594
0.3415522015863942,0.8390876826848631
```

### *JSON*

Can be used to import data in ***Mongo, MySQL, etc...***

```
{
	"TestCaseClass": [
		{
			"str": "0.03967295259868164",
			"aDouble": "0.30227242830001944"
		},
		{
			"str": "0.8408601054584944",
			"aDouble": "0.7401367963737594"
		}
  ]
}
```

### *XML*

Can be used to import data in ***MySQL, SQL Server, etc...***

```
<TestCaseClassList>
	<TestCaseClass>
		<str>0.03967295259868164</str>
		<aDouble>0.30227242830001944</aDouble>
	</TestCaseClass>
	<TestCaseClass>
		<str>0.8408601054584944</str>
		<aDouble>0.7401367963737594</aDouble>
	</TestCaseClass>
</TestCaseClassList>
```

### *SQL*

Don't forget about Primary Key!

Each insert query can contains max ***950*** rows (Due to ***1000*** row limit in SQL).

```
CREATE TABLE IF NOT EXISTS TestCaseClass(
	str	VARCHAR,
	aDouble	DOUBLE
);

INSERT INTO TestCaseClass (str, aDouble) VALUES 
('0.03967295259868164', 0.30227242830001944),
('0.8408601054584944', 0.7401367963737594),
('0.3415522015863942', 0.8390876826848631)
;
```

## Version

Last stable release **1.0.1**.

## Author :octocat:

***Anton Kurako*** (***@GoodforGod***)

## License

This project is licensed under the Apache License - see the [LICENCE](LICENCE) file for details.
