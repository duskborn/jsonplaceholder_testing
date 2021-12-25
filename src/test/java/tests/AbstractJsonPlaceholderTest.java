package tests;

import io.restassured.response.Response;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AbstractJsonPlaceholderTest extends AbstractTestNGSpringContextTests {
    String endpointsPath = "src/main/resources/endpoints.txt";

    protected String getEndpoint() {
        String endpoint = null;
        try {
            endpoint = new String(Files.readAllBytes(Paths.get(endpointsPath)), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            Assert.fail("Ошибка: " + e.getMessage());
            logger.error("Ошибка: " + e);
        }
        return endpoint;
    }

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
}
