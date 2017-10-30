package orderbydeclaration;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DoNotOrderWithoutListener {

    String orderOfExecution = "";

    @Test
    public void test1(){
        orderOfExecution += "test1 ";
        Assert.assertEquals(orderOfExecution, "aNiceOne boyOhBoy test1 ");

    }

    @Test
    public void aNiceOne() {
        orderOfExecution += "aNiceOne ";
        Assert.assertEquals(orderOfExecution, "aNiceOne ");
    }

    @Test
    public void boyOhBoy()  {
        orderOfExecution += "boyOhBoy ";
        Assert.assertEquals(orderOfExecution, "aNiceOne boyOhBoy ");

    }
}
