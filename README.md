# ExtendNG

[ ![Download](https://api.bintray.com/packages/yaroslav-orel/ExtendNG/ExtendNG/images/download.svg?version=1.0) ](https://bintray.com/yaroslav-orel/ExtendNG/ExtendNG/1.0/link)
[![Build Status](https://travis-ci.org/yaroslav-orel/ExtendNG.svg?branch=master)](https://travis-ci.org/yaroslav-orel/ExtendNG)
[![Sonarqube quality gate](https://sonarqube.com/api/badges/gate?key=com.github.yaroslav-orel:ExtendNG)](https://sonarqube.com/dashboard/index?id=com.github.yaroslav-orel:ExtendNG)

Library which provides set of convenient listeners to reduce boilerplate TestNG code.  

# Listeners
1. ```MethodInGroupsListener``` - allows to declare methods with ```@BeforeMethodInGroups``` and ```@AfterMethodInGroups``` which are launched before/after each test in a specific group
2. ```OrderByGroupsListener``` - order test execution based on their groups (tests from one group are grouped together)
3. ```OrderByDeclarationListener``` - executes tests in a class in order of their declaration (no more ```dependsOnMethods``` and ```alwaysRun=true```)
4. ```FastFailListener``` - if one test in class fails, all subsequent tests are skipped. Makes sense in classes with ```OrderByDeclarationListener```

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
e.g. ```@BeforeMethodInGroups(priority = 1)```. Methods with lower number are executed first just like in TestNG

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
If you need to establish the dependency between groups you have to create method in your test class
 which is annotated with ```@GroupOrder``` and returns ```String[]``` populated with names of groups in your custom order
 ```
@GroupOrder
public String[] groupOrder(){
    return new String[]{"first", "second", "third"};
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

