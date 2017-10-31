# ExtendNG

Library which provides set of convenient listeners to reduce boilerplate TestNG code.  

# Listeners

1. ```MethodInGroupsListener``` - allows to declare methods with ```@BeforeMethodInGroups``` and ```@AfterMethodInGroups``` which are launched before/after each test in a specific group
2. ```OrderByDeclarationListener``` - executes tests in a class in order of their declaration (no more 'dependsOnMethods' and 'alwaysRun=true')
3. ```FastFailListener``` - if one test in class fails, all subsequent tests are skipped. Makes sense in classes with ```OrderByDeclarationListener```
