package tests.jsonplaceholder_api.posts.positive;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import tests.jsonplaceholder_api.posts.AbstractPostsTest;

@Epic("Тестирование постов JSON Placeholder")
@Story("Получение списка постов")
@Severity(value = SeverityLevel.BLOCKER)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositivePostsListTest extends AbstractPostsTest {

    @DisplayName("Проверка возврата статуса 200 при получении первого поста")
    @Order(1)
    @Test()
    public void positiveApiTestStep1() {
        getPost(1)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @DisplayName("Проверка возврата статуса 200 при получении списка постов")
    @Order(2)
    @Test()
    public void positiveApiTestStep2() {
        getPosts()
                .then()
                .assertThat()
                .statusCode(200);
    }

    @DisplayName("Проверка количества постов")
    @Order(3)
    @Test()
    public void positiveApiTestStep3() {

    }

    @DisplayName("Проверка содержимого первого поста")
    @Order(4)
    @Test()
    public void positiveApiTestStep4() {

    }

    @DisplayName("Проверка содержимого последнего поста")
    @Order(5)
    @Test()
    public void positiveApiTestStep5() {

    }
}
