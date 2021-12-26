package tests.jsonplaceholder_api.posts.negative;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import tests.jsonplaceholder_api.posts.AbstractPostsTest;

@Epic("Тестирование постов JSON Placeholder")
@Story("Получение списка постов. Негативные проверки")
@Severity(value = SeverityLevel.CRITICAL)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NegativePostsListTest extends AbstractPostsTest {

    @DisplayName("Проверка возврата статуса 404 при отправке несуществующего номера поста")
    @Order(1)
    @Test()
    public void negativePostsListTestStep1() {
        Response response = getPost(1001);
        checkStatusCode(response, 404);
    }

    @DisplayName("Проверка возврата статуса 404 при отправке символа вместо номера поста")
    @Order(2)
    @Test()
    public void negativePostsListTestStep2() {
        Response response = getPost("A");
        checkStatusCode(response, 404);
    }
}
