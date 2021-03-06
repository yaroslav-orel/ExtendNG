# ExtendNG

[ ![Download](https://api.bintray.com/packages/yaroslav-orel/ExtendNG/ExtendNG/images/download.svg) ](https://bintray.com/yaroslav-orel/ExtendNG/ExtendNG/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.yaroslav-orel/ExtendNG/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.yaroslav-orel/ExtendNG)
[![Build Status](https://travis-ci.org/yaroslav-orel/ExtendNG.svg?branch=master)](https://travis-ci.org/yaroslav-orel/ExtendNG)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.github.yaroslav-orel%3AExtendNG&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.yaroslav-orel%3AExtendNG)
[![Open Source Love](https://badges.frapsoft.com/os/mit/mit.svg?v=102)](https://github.com/ellerbrock/open-source-badge/)

Library which provides set of convenient listeners to reduce boilerplate TestNG code.  

# Listeners
1. ```MethodInGroupsListener``` - allows to declare methods with ```@BeforeMethodInGroups``` and ```@AfterMethodInGroups``` which are launched before/after each test in a specific group
2. ```OrderByGroupsListener``` - order test execution based on their groups (tests from one group are grouped together)
3. ```OrderByDeclarationListener``` - executes tests in a class in order of their declaration (no more ```dependsOnMethods``` and ```alwaysRun=true```)
4. ```FastFailListener``` - if one test in class fails, all subsequent tests are skipped. Makes sense in classes with ```OrderByDeclarationListener```

# Usage

### Maven
```
<dependency>
	<groupId>com.github.yaroslav-orel</groupId>
	<artifactId>ExtendNG</artifactId>
	<version>1.1.2</version>
</dependency>
```

### Gradle
```
testCompile 'com.github.yaroslav-orel:ExtendNG:1.1.2'
```

# Examples
### MethodInGroupsListener
```
@Listeners({MethodInGroupsListener.class})
public class MethodInGroupsExample {

    @BeforeMethodInGroups(groups = "target")
    public void before(){
        System.out.println("Before test in target group");
    }

    @AfterMethodInGroups(groups = "target")
    public void after(){
        System.out.println("After test in target group");
    }

    @Test(groups = "target")
    public void target1(){
        System.out.println("target1");
    }

    @Test(groups = "target")
    public void target2(){
        System.out.println("target2");
    }

    @Test
    public void nonTarget(){
        System.out.println("nonTarget");
    }
```
The output
```
Before test in target group
target1
After test in target group
Before test in target group
target2
After test in target group
nonTarget
```
>Note: If you need several @(Before/After)MethodInGroups you can assign them priority 
e.g. ```@BeforeMethodInGroups(priority = 1)```. Methods with lower number are executed first just like in TestNG.

>Note: You can inject the following objects into MethodInGroups methods: ```ITestResult```, ```Method```, ```ITestContext```, ```XmlTest``` 

### OrderByGroupsListener
```
@Listeners(OrderByGroupsListener.class)
public class OrderByGroupsTest {

    @Test(groups = "first")
    public void c(){
        System.out.println("c first");
    }

    @Test(groups = "second")
    public void w(){
        System.out.println("w second");
    }

    @Test(groups = "third")
    public void a(){
        System.out.println("a third");
    }

    @Test(groups = "first")
    public void k(){
        System.out.println("k first");
    }

    @Test(groups = "second")
    public void q(){
        System.out.println("q second");
    }

    @Test(groups = "third")
    public void v(){
        System.out.println("v third");
    }
}
```
The output:
```
c first
k first
a third
v third
q second
w second
```
> Note: By Default groups are not sorted. 
If you need to establish the dependency between groups you have two options:
>1. Add ```@GroupOrder``` annotation to the test class and define order via ```groups``` property;
>2. create method in your test class which is annotated with ```@GroupOrder``` and returns ```List<String>``` populated with names of groups in your custom order just like below
 ```
@GroupOrder
public List<String> groupOrder(){
    return asList("first", "second", "third");
 }
 ```


### OrderByDeclarationListener
```
@Listeners(OrderByDeclarationListener.class)
public class OrderByDeclarationTest {

    @Test
    public void thisIsFirst(){
        System.out.println("thisIsFirst");
    }

    @Test
    public void aVeryCoolTest(){
        System.out.println("aVeryCoolTest");
    }

    @Test
    public void needThisToBeLast(){
        System.out.println("needThisToBeLast");
    }

}
```
The output: 
```
thisIsFirst
aVeryCoolTest
needThisToBeLast
```

### FastFailListener

```
@Listeners(FastFailListener.class)
public class FastFailTest {

    @Test
    public void throwsException(){
        System.out.println("I'm going to fail");
        Assert.fail();
    }

    @Test
    public void doesNotRunAfterException1(){ 
        System.out.println("Not going to be executed");
    }
}
```
The output: 
```
I'm going to fail
```

