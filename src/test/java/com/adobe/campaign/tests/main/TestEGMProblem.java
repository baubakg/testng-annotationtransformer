package com.adobe.campaign.tests.main;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.adobe.campaign.tests.case1_fails.TestClassA;
import com.adobe.campaign.tests.case1_fails.TestClassB;
import com.adobe.campaign.tests.case2_fails.TestClassA2;
import com.adobe.campaign.tests.case2_fails.TestClassB2;
import com.adobe.campaign.tests.case2_fails.TestClassC2;

public class TestEGMProblem {
    @Test
    public void testClassLevel_multiGrpups() {

        // Rampup
        TestNG myTestNG = createTestNG();
        TestListenerAdapter tla = fetchTestResultsHandler(myTestNG);

        // Define suites
        XmlSuite mySuite = addSuitToTestNGTest(myTestNG, "Automated Suite EGM Problem Testing");

        // Add listeners

        mySuite.addListener(TestTransformer.class.getTypeName());

        // Create an instance of XmlTest and assign a name for it.
        XmlTest myTest = attachTestToSuite(mySuite, "Test Simple Phased EGM Tests");

        myTest.addIncludedGroup("A");

        //Define packages
        List<XmlPackage> l_packages = new ArrayList<XmlPackage>();
        l_packages.add(new XmlPackage("com.adobe.campaign.tests.case1_fails.*"));
        myTest.setXmlPackages(l_packages);

        myTestNG.run();

        final Set<String> tests = TestTransformer.tests;
        assertThat("Our test Class TestClassA should have been accessed", tests.contains(TestClassA.class.getTypeName()));
        assertThat("Our test Class TestClassB should have been accessed", tests.contains(TestClassB.class.getTypeName()));

        int allTestsNr = tla.getFailedTests().size() + tla.getPassedTests().size()
                + tla.getSkippedTests().size();
        assertThat("One test should have been executed", allTestsNr, is(equalTo(2)));

        assertThat("We should have 1 successful methods of class A",
                tla.getPassedTests().stream().filter(m -> m.getInstance().getClass().equals(TestClassA.class))
                        .collect(Collectors.toList()).size(),
                is(equalTo(1)));

        assertThat("We should have 1 successful methods of class B",
                tla.getPassedTests().stream().filter(m -> m.getInstance().getClass().equals(TestClassB.class))
                        .collect(Collectors.toList()).size(),
                is(equalTo(1)));

    }

    
    
    @Test
    public void testTestClassLevel_NoDescription_DifferentGroups() {

        // Rampup
        TestNG myTestNG = createTestNG();
        TestListenerAdapter tla = fetchTestResultsHandler(myTestNG);

        // Define suites
        XmlSuite mySuite = addSuitToTestNGTest(myTestNG, "Automated Suite EGM Problem Testing");

        // Add listeners

        mySuite.addListener(TestTransformer.class.getTypeName());

        // Create an instance of XmlTest and assign a name for it.
        XmlTest myTest = attachTestToSuite(mySuite, "Test Simple Phased EGM Tests");

        myTest.addIncludedGroup("A");

        //Define packages
        List<XmlPackage> l_packages = new ArrayList<XmlPackage>();
        l_packages.add(new XmlPackage("com.adobe.campaign.tests.case2_fails.*"));
        myTest.setXmlPackages(l_packages);

        myTestNG.run();

        final Set<String> tests = TestTransformer.tests;
        assertThat("Our test Class TestClassC2 should have been accessed", tests.contains(TestClassC2.class.getTypeName()));
        assertThat("Our test Class TestClassA2 should have been accessed", tests.contains(TestClassA2.class.getTypeName()));
        assertThat("Our test Class TestClassB2 should have been accessed", tests.contains(TestClassB2.class.getTypeName()));

        int allTestsNr = tla.getFailedTests().size() + tla.getPassedTests().size()
                + tla.getSkippedTests().size();
        assertThat("One test should have been executed", allTestsNr, is(equalTo(2)));

        assertThat("We should have 1 successful methods of class C",
                tla.getPassedTests().stream().filter(m -> m.getInstance().getClass().equals(TestClassC2.class))
                        .collect(Collectors.toList()).size(),
                is(equalTo(1)));

        
        assertThat("We should have 1 successful methods of class A",
                tla.getPassedTests().stream().filter(m -> m.getInstance().getClass().equals(TestClassA2.class))
                        .collect(Collectors.toList()).size(),
                is(equalTo(1)));

        assertThat("We should have 1 successful methods of class B",
                tla.getPassedTests().stream().filter(m -> m.getInstance().getClass().equals(TestClassB2.class))
                        .collect(Collectors.toList()).size(),
                is(equalTo(1)));

    }
    //////////////////Helpers

    /**
     * This method creates a testng test instance with a result listener
     * 
     * @return a TestNG instance
     */
    public static TestNG createTestNG() {
        TestNG myTestNG = new TestNG();
        TestListenerAdapter tla = new TestListenerAdapter();
        myTestNG.addListener((ITestNGListener) tla);
        return myTestNG;
    }

    /**
     * Attached a newly created empty suite attached to the given testng
     * instance
     * 
     * @param in_testNGTestInstance
     * @param in_suiteName
     * @return
     */
    public static XmlSuite addSuitToTestNGTest(TestNG in_testNGTestInstance, String in_suiteName) {
        XmlSuite mySuite = new XmlSuite();
        mySuite.setName(in_suiteName);
        List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
        mySuites.add(mySuite);
        // Set the list of Suites to the testNG object you created earlier.
        in_testNGTestInstance.setXmlSuites(mySuites);
        return mySuite;
    }

    /**
     * Attaches a test to the TestNG suite
     * 
     * @param in_testNGSuite
     * @param in_testName
     * @return
     */
    public static XmlTest attachTestToSuite(XmlSuite in_testNGSuite, String in_testName) {
        XmlTest lr_Test = new XmlTest(in_testNGSuite);

        lr_Test.setName(in_testName);
        return lr_Test;
    }

    /**
     * This method returns the test result listener attached to the testng
     * instance
     * 
     * @param myTestNG
     * 
     * @return a TestListenerAdapter that listens on the test results
     */
    public static TestListenerAdapter fetchTestResultsHandler(TestNG myTestNG) {
        List<ITestListener> testLisenerAdapaters = myTestNG.getTestListeners();

        if (testLisenerAdapaters.size() != 1)
            throw new IllegalStateException("We did not expect to have more than one adapter");

        return (TestListenerAdapter) testLisenerAdapaters.get(0);
    }

}
