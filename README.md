# DummyMaker

Allow you to create/populate dummy POJOs and export them.

## Install
Get via **Maven** Dependency.
```
Give the example
```

Get via **Gradle** Dependency.
```
Give the example
```

## Methods

### **Factories**

* *IPrimeFactory* - provides (**produce** & **populate** methods)

* *IProduceFactory* - provides (**produce** methods)

* *IPopulateFactory* - provides (**populate** methods)

### **Exporter**

* *IPrimeExporter* - **CSV/XML/JSON/SQL** export methods.

* *IExporter* - export methods.

## *Getting Started with examples*

### ***Annotations***

Let user declare fields with Annotations to generate values for that field for dummy object for populate/produce.

User can user ***GenForceExport*** or ***GenIgnoreExport*** to force/ignore export object's field.

#### 
![](https://media.giphy.com/media/xUA7aPwZO871rrTkT6/giphy.gif)

### ***IPrimeFactory/IProvideFactory***

Let user populate dummy object(s) or produce dummy object(s).

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

Examples of exported dummies of each type.

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
```
0.03967295259868164,0.30227242830001944
0.8408601054584944,0.7401367963737594
0.3415522015863942,0.8390876826848631
```

### *JSON*
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

## Author

**Kurako Anton** (*@GoodforGod*)

## License

This project is licensed under the Apache License - see the [LICENCE](LICENCE) file for details.
