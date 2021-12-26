package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.BaseModel;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class AbstractJsonPlaceholderTest extends AbstractJUnit4SpringContextTests {
    private String endpointsPath = "src/main/resources/endpoints.txt";
    protected RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(getEndpoint())
            .addHeader("Content-Type", "application/json")
            .build();
    protected ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectHeader("Content-Type", "application/json; charset=utf-8")
            .build();

    protected String getEndpoint() {
        String endpoint = null;
        try {
            endpoint = new String(Files.readAllBytes(Paths.get(endpointsPath)), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            throwError("Ошибка при получении endpoint: " + e.getMessage());
        }
        return endpoint;
    }

    protected void throwError(String errorText) {
        fail(errorText);
        logger.error(errorText);
    }

    protected void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, "Ошибка - ожидалось " + expected + ", но было получено " + actual);
    }

    protected void assertThatObjectsAreEqual(Object expected, Object actual) {
        try {
            Assertions.assertTrue(expected.equals(actual));
        } catch (AssertionError e) {
            throwError("Ошибка при сравнении объектов - ожидалось " + expected + ", но было получено " + actual);
        }
    }

    protected void assertEquals(Object expected, Object actual, String errorText) {
        try {
            Assertions.assertEquals(expected, actual);
        } catch (AssertionError e) {
            throwError(errorText);
        }
    }

    protected void throwStatusAssertionError(Integer expectedStatus, Integer actualStatus) {
        String errorText = "Ошибка - ожидался статус " + expectedStatus + ", но был получен " + actualStatus;
        throwError(errorText);
    }

    protected void checkStatusCode(Response response, Integer expectedStatusCode) {
        try {
            response
                    .then()
                    .assertThat()
                    .statusCode(expectedStatusCode);
        } catch (AssertionError e) {
            throwStatusAssertionError(expectedStatusCode, response.getStatusCode());
        }
    }

    protected void compareTwoModels(BaseModel expectedModel, BaseModel actualModel) {
        try {
            Assertions.assertTrue(actualModel.equals(expectedModel));
        } catch (AssertionError e) {
            throwError("Ошибка при сравнении моделей класса " + actualModel.getClass().getSimpleName()
                    + " - модели не идентичны: "
                    + "\nожидалось " + expectedModel.toString()
                    + "\nно было " + actualModel.toString());
        }
    }
}
