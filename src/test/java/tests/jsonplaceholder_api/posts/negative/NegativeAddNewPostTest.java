package tests.jsonplaceholder_api.posts.negative;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import tests.jsonplaceholder_api.posts.AbstractPostsTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * В данном кейсе в отличие от остальных описано гипотетически верное, а не реальное поведение системы.
 * На данны момент сайт https://jsonplaceholder.typicode.com создает записи  почти при любом теле POST-запроса,
 * кроме шагов 7 и 8 (переполнение заголовка и тела запроса, где выдаются верные коды ошибок), что теоритически
 * является неправильным поведением системы. Таким образом шаги с этими проверками будут падать с ошибкой статуса.
 */
@Epic("Тестирование постов JSON Placeholder")
@Story("Добавление нового поста. Негативные проверки")
@Severity(value = SeverityLevel.CRITICAL)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NegativeAddNewPostTest extends AbstractPostsTest {
    private static Response response;
    private static TestData testData;


    private void checkRecordNotCreated() {
        if (response.getBody().asString().contains("\"id\": 101")) throwError("Новая запись неожиданно была создана");
    }


    @BeforeAll
    public static void setup() {
        testData = new TestData();
    }


    @DisplayName("Негативное создание поста с невалидным содержанием 1 - пустое тело запроса. Проверка статуса ответа")
    @Order(1)
    @Test()
    public void negativeAddNewPostTestStep1_1() {
        response = postPost("");
        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 1 - пустое тело запроса. Проверка тела ответа")
    @Order(2)
    @Test()
    public void negativeAddNewPostTestStep1_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 2  - неверный параметр в теле запроса. Проверка статуса ответа")
    @Order(3)
    @Test()
    public void negativeAddNewPostTestStep2_1() {
        response = postPost("{\"not_rly_id\":null}");
        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 2  - неверный параметр в теле запроса. Проверка тела ответа")
    @Order(4)
    @Test()
    public void negativeAddNewPostTestStep2_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 3  - неверный тип значения параметра в теле запроса. Проверка статуса ответа")
    @Order(5)
    @Test()
    public void negativeAddNewPostTestStep3_1() {
        response = postPost("{\"userId\":\"userId\"}");
        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 3  - неверный тип значения параметра в теле запроса. Проверка тела ответа")
    @Order(6)
    @Test()
    public void negativeAddNewPostTestStep3_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 4  - не хватает параметра в теле запроса. Проверка статуса ответа")
    @Order(7)
    @Test()
    public void negativeAddNewPostTestStep4_1() {
        response = postPost("{\"userId\": 1, \"title\": \"title\"}");
        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 4  - не хватает параметра в теле запроса. Проверка тела ответа")
    @Order(8)
    @Test()
    public void negativeAddNewPostTestStep4_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 5  - неверное значение параметра в теле запроса. Проверка статуса ответа")
    @Order(9)
    @Test()
    public void negativeAddNewPostTestStep5_1() {
        response = postPost("{\"userId\": 999, \"title\": \"title\", \"body\": \"body\"}");
        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 5  - неверное значение параметра в теле запроса. Проверка тела ответа")
    @Order(10)
    @Test()
    public void negativeAddNewPostTestStep5_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 6  - неверный заголовок. Проверка статуса ответа")
    @Order(11)
    @Test()
    public void negativeAddNewPostTestStep6_1() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(getEndpoint())
                .addHeader("Content-Type", "application/json")
                .addHeader("Wrong-Header-123", "Wrong-Header-123")
                .build();
        response = postPost("{\"userId\": 1, \"title\": \"title\", \"body\": \"body\"}", requestSpecification, responseSpecification);
        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 6  - неверный заголовок. Проверка тела ответа")
    @Order(12)
    @Test()
    public void negativeAddNewPostTestStep6_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 7 - переполнение заголовка. Проверка статуса ответа")
    @Order(13)
    @Test()
    public void negativeAddNewPostTestStep7_1() {
        ResponseSpecification negativeResponseSpecification = new ResponseSpecBuilder()
                .expectHeader("Content-Type", "text/html")
                .build();
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(getEndpoint())
                .addHeader("Content-Type", "application/json")
                .addHeader("Big-Header", testData.bigText)
                .build();
        response = postPost("{\"userId\": 1, \"title\": \"title\", \"body\": \"body\"}", requestSpecification, negativeResponseSpecification);

        checkStatusCode(response, 400);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 7 - переполнение заголовка. Проверка тела ответа")
    @Order(14)
    @Test()
    public void negativeAddNewPostTestStep7_2() {
        checkRecordNotCreated();
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 8 - переполнение тела запроса. Проверка статуса ответа")
    @Order(15)
    @Test()
    public void negativeAddNewPostTestStep8_1() {
        ResponseSpecification negativeResponseSpecification = new ResponseSpecBuilder()
                .expectHeader("Content-Type", "text/html; charset=utf-8")
                .build();
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(getEndpoint())
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .build();
        response = postPost(testData.bigText, requestSpecification, negativeResponseSpecification);
        checkStatusCode(response, 503);
    }

    @DisplayName("Негативное создание поста с невалидным содержанием 8 - переполнение тела запроса. Проверка тела ответа")
    @Order(16)
    @Test()
    public void negativeAddNewPostTestStep8_2() {
        checkRecordNotCreated();
    }


    private static class TestData {
        String bigText = null;

        {
            try {
                bigText = new String(Files.readAllBytes(Paths.get("src/test/resources/bigtext.txt")), StandardCharsets.US_ASCII);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
