package tests.jsonplaceholder_api.posts;

import io.restassured.response.Response;
import models.Post;
import tests.AbstractJsonPlaceholderTest;

import static io.restassured.RestAssured.given;

public class AbstractPostsTest extends AbstractJsonPlaceholderTest {

    protected Response getPosts() {
        return given()
                .baseUri(getEndpoint())
                .when()
                .get("/posts");
    }

    protected Response getPost(int id) {
        return given()
                .baseUri(getEndpoint())
                .when()
                .get("/posts/" + id);
    }

    protected Post[] getPostsList() {
        return getPosts().getBody().as(Post[].class);
    }

    protected Post getPostModel(int id) {
        return getPost(id).getBody().as(Post.class);
    }
}
