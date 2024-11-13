package TestNG;

import org.testng.annotations.BeforeTest;

public abstract class ParentTest {
    @BeforeTest
    public void beforeTest1() {
        System.out.println("Before test");
    }
}
