package tests.jsonplaceholder_api.posts;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.PostModel;
import org.json.JSONException;
import org.json.JSONObject;
import tests.AbstractJsonPlaceholderTest;

import static io.restassured.RestAssured.given;

/**
 * Сюда вынесены общие методы для работы с постами
 */
public class AbstractPostsTest extends AbstractJsonPlaceholderTest {

    protected Response getPosts() {
        Response response = given()
                .spec(requestSpecification)
                .baseUri(getEndpoint())
                .when()
                .get("/posts");
        response
                .then()
                .assertThat()
                .spec(responseSpecification);
        return response;
    }

    protected Response getPost(Integer id) {
        return getPost(id.toString());
    }

    protected Response getPost(String id) {
        Response response = given()
                .spec(requestSpecification)
                .baseUri(getEndpoint())
                .when()
                .get("/posts/" + id);
        response
                .then()
                .assertThat()
                .spec(responseSpecification);
        return response;
    }

    protected PostModel[] getPostsList() {
        return getPosts().getBody().as(PostModel[].class);
    }

    protected PostModel getPostModel(Integer id) {
        return getPost(id.toString()).getBody().as(PostModel.class);
    }

    protected PostModel getPostModel(String id) {
        return getPost(id).getBody().as(PostModel.class);
    }

    protected String createPostBody(Integer userId, String title, String body) {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("userId", userId);
            jsonobject.put("title", title);
            jsonobject.put("body", body);
        } catch (JSONException e) {
            throwError("Ошибка при создании тела POST-запроса: " + e.getMessage());
        }
        return jsonobject.toString();
    }

    protected Response postPost(String stringPostBody, RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        Response response = given()
                .spec(requestSpecification)
                .body(stringPostBody)
                .baseUri(getEndpoint())
                .when()
                .post("/posts");
        response
                .then()
                .assertThat()
                .spec(responseSpecification);
        return response;
    }

    protected Response postPost(String stringPostBody) {
        return postPost(stringPostBody, requestSpecification, responseSpecification);
    }

    protected Response postPost(Integer userId, String title, String body) {
        return postPost(createPostBody(userId, title, body));
    }

    protected Response postPost(PostModel postModel) {
        return postPost(postModel.userId, postModel.title, postModel.body);
    }
}
