package testclasses.orderbydeclaration;

import org.testng.annotations.Test;

public class DoNotOrderWithoutListener {

    @Test
    public void test1(){ }

    @Test
    public void aNiceOne() { }

    @Test
    public void boyOhBoy()  { }
}
