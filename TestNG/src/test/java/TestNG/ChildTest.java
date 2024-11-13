package TestNG;

import org.testng.annotations.Test;

public class ChildTest extends ParentTest {
    @Test
    public void someTest() {
        System.out.println("Test");
    }
}
