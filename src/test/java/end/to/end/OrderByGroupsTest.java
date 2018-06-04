package end.to.end;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import testclasses.orderbygroups.OrderByGroupsSortedTest;
import testclasses.orderbygroups.OrderByGroupsSpecialCasesTest;
import util.InvokedMethodNameListener;
import util.TestUtils;

public class OrderByGroupsTest {

    @Test
    public void orderByGroups(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), testclasses.orderbygroups.OrderByGroupsTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("test1()", "wayToName()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("coveredByTests()", "thisOne()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("originalName()", "treatThisRight()");
        softly.assertAll();
    }

    @Test
    public void orderByGroupsSortedGroups(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), OrderByGroupsSortedTest.class);

        Assertions.assertThat(listener.getInvokedMethodNames()).containsExactly(
                "test1()", "wayToName()",
                "coveredByTests()", "treatThisRight()",
                "originalName()", "thisOne()"
        );
    }

    @Test
    public void orderByGroupsSpecialCases(){
        val listener = TestUtils.run(new InvokedMethodNameListener(), OrderByGroupsSpecialCasesTest.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("withMultipleGroups()", "actuallyWithOneGroup()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("anotherTestWithoutGroups()", "zeroGroups()");
        softly.assertThat(listener.getInvokedMethodNames()).containsSequence("betThisWorks()", "orderMeMan()");
        softly.assertAll();
    }
}
