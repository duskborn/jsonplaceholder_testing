package tests.jsonplaceholder_api.posts.positive;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.PostModel;
import org.junit.jupiter.api.*;
import tests.jsonplaceholder_api.posts.AbstractPostsTest;

@Epic("Тестирование постов JSON Placeholder")
@Story("Добавление нового поста")
@Severity(value = SeverityLevel.BLOCKER)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositiveAddNewPostTest extends AbstractPostsTest {
    private static TestData testData;
    private static Response response;
    private static PostModel outputNewPost = null;


    @BeforeAll
    public static void setup() {
        testData = new TestData();
    }


    @DisplayName("Позитивное создание обычного поста и проверка ответа")
    @Order(1)
    @Test()
    public void positiveAddNewPostTestStep1() {
        response = postPost(testData.newPost);
        outputNewPost = response.getBody().as(PostModel.class);
        testData.newPost.id = outputNewPost.id;
        compareTwoModels(testData.newPost, outputNewPost);
    }

    @DisplayName("Проверка статуса")
    @Order(2)
    @Test()
    public void positiveAddNewPostTestStep2() {
        checkStatusCode(response, 201);
    }


    private static class TestData {
        PostModel newPost = new PostModel();

        {
            newPost.id = null;
            newPost.userId = 1;
            newPost.title = "foo";
            newPost.body = "bar";
        }
    }
}
