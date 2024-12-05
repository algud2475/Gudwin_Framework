package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/*
* В случае с JUnit нужно аннотировать раннер @RunWith(Cucumber.class), при TestNG - расширять AbstractTestNGCucumberTests
* plugin - подключаем listener
* glue - указываем путь (общий или конкретный) до steps и hooks (Before, After)
* features - путь к сценариям
* tags - аннотации, которыми пометили сценарии для запуска
*
* опции возможно передать через мавен или, например, в argLine surefire-plugin
* -Dcucumber.options="--plugin io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
 */
@CucumberOptions(
        plugin = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        glue = {"steps"},
        features="src/test/resources",
        tags = "@smoke"
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

}
