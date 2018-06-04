package util;

import lombok.experimental.UtilityClass;
import org.testng.ITestNGListener;
import org.testng.TestNG;

@UtilityClass
public class TestUtils {

    public static <T extends ITestNGListener> T run(T listener, final Class<?>... testClasses) {
        final TestNG tng = create(testClasses);
        tng.addListener(listener);
        tng.run();

        return listener;
    }

    public static void run(final Class<?>... testClasses){
        final TestNG tng = create(testClasses);
        tng.run();
    }

    private static TestNG create() {
        final TestNG result = new TestNG();
        result.setUseDefaultListeners(false);
        result.setVerbose(0);
        return result;
    }

    private static TestNG create(final Class<?>... testClasses) {
        TestNG result = create();
        result.setTestClasses(testClasses);
        return result;
    }

}
