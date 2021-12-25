package tests.jsonplaceholder_api.positive;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.AbstractJsonPlaceholderTest;

import static io.restassured.RestAssured.given;

@Epic("Тестирование ")
@Story("")
@Severity(value = SeverityLevel.BLOCKER)
public class PositiveApiTest extends AbstractJsonPlaceholderTest {
    @Autowired
    Environment environment;

    @BeforeClass
    public void setup() {

    }


    @Test(description = "")
    public void positiveApiTestStep1() {
        getPost(1)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(description = "", dependsOnMethods = "positiveApiTestStep1")
    public void positiveApiTestStep2() {
        getPosts()
                .then()
                .assertThat()
                .statusCode(200);
    }


    @AfterClass(alwaysRun = true)
    public void teardown() {

    }
}
