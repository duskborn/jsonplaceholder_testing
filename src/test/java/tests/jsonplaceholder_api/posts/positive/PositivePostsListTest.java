package tests.jsonplaceholder_api.posts.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import models.Post;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.*;
import tests.jsonplaceholder_api.posts.AbstractPostsTest;

@Epic("Тестирование постов JSON Placeholder")
@Story("Получение списка постов")
@Severity(value = SeverityLevel.BLOCKER)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositivePostsListTest extends AbstractPostsTest {
    private static TestData testData;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        testData = new TestData();
    }

    @DisplayName("Проверка возврата статуса 200 при получении первого поста")
    @Order(1)
    @Test()
    public void positiveApiTestStep1() {
        getPost(1)
                .then()
                .assertThat()
                .statusCode(200);
        // TODO logs
    }

    @DisplayName("Проверка возврата статуса 200 при получении списка постов")
    @Order(2)
    @Test()
    public void positiveApiTestStep2() {
        getPosts()
                .then()
                .assertThat()
                .statusCode(200);
        // TODO logs
    }

    @DisplayName("Проверка количества постов")
    @Order(3)
    @Test()
    public void positiveApiTestStep3() {
        Post[] postList = getPostsList();
        Assertions.assertEquals(100, postList.length);
        // TODO logs
    }

    @DisplayName("Проверка содержимого первого поста")
    @Order(4)
    @Test()
    public void positiveApiTestStep4() throws JsonProcessingException {
        Post post = getPostModel(1);
        Assertions.assertTrue(post.equals(testData.firstPost));
        // TODO logs
    }

    @DisplayName("Проверка содержимого последнего поста")
    @Order(5)
    @Test()
    public void positiveApiTestStep5() {
        Post post = getPostModel(100);
        Assertions.assertTrue(post.equals(testData.lastPost));
        // TODO logs
    }

    private static class TestData {
        Post firstPost = new Post();
        {
            firstPost.id = 1;
            firstPost.userId = 1;
            firstPost.title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
            firstPost.body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum" +
                    "\nreprehenderit molestiae ut ut quas totam" +
                    "\nnostrum rerum est autem sunt rem eveniet architecto";
        }

        Post lastPost = new Post();
        {
            lastPost.id = 100;
            lastPost.userId = 10;
            lastPost.title = "at nam consequatur ea labore ea harum";
            lastPost.body = "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in" +
                    "\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut";
        }

    }
}
