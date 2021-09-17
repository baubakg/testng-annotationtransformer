package com.adobe.campaign.tests.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.adobe.campaign.tests.case1_fails.TestClassA;

public class TestGroupTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
            Method testMethod) {

        if (testClass != null && testClass.getTypeName().equals(TestClassA.class.getTypeName())) {
            annotation.setGroups(new String[] { "MYGROUP" });
        }

        if (testMethod != null
                && testMethod.getDeclaringClass().getTypeName().equals(TestClassA.class.getTypeName())) {
            annotation.setGroups(new String[] { "MYGROUP" });
        }
    }

}
