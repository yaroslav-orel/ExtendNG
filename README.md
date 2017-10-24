# testng-helpers

This is a utility which provides ability to define methods which would run before and/or after each test in a specific group. 

# Example

```
@Test
public class FeedingTest{

  @Test(groups = "cats")
  public void catCanBeFed(){
    Cat cat = new Cat();
    owner.feed(cat);
    Assert.assertTrue(cat.isFed());
  }

  @Test(groups = "dogs")
  public void beagelCanBeFed(){
    Dog beagel = new Beagel();
    owner.feed(beagel);
    Assert.assertTrue(beagel.isFed());
  }

  @Test(gropus = "dogs")
  public void boxerCanBeFed(){
    Dog boxer = new Boxer();
    owner.feed(boxer);
    Assert.assertTrue(boxer.isFed());
  }

}
```

Suppose we know that dogs like to eat a lot and owner could bring with him only limited amount of food. So we need to ensure owner has enough food in his hands to properly feed each dog.
Using bare TestNG we couldn't check owner's food before each test in "dogs" group but with testng-helper this is as simple as this:
```
@BeforeMethodInGroup("dogs")
public void assureHasEnoughFood(){
  if(!owner.hasFood()){
    owner.FetchFood();
  }
```
``` @AfterMethodInGroup ``` works the same.
