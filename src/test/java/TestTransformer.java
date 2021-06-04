
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class TestTransformer implements IAnnotationTransformer {

    static Logger log = LogManager.getLogger();

    public static Set<String> tests = new HashSet<String>();

    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
            Method testMethod) {

        String l_fullName = null;

        if (testMethod != null) {
            l_fullName = testMethod.getDeclaringClass().getTypeName() + "." + testMethod.getName();

        } else if (testClass != null) {

            l_fullName = testClass.getTypeName();
        } else {
            log.warn("No external group definitions could be set for the given test");
        }
        tests.add(l_fullName);

        log.info(l_fullName);
    }

}
