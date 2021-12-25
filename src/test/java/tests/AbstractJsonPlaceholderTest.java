package tests;

import io.restassured.response.Response;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class AbstractJsonPlaceholderTest extends AbstractJUnit4SpringContextTests {
    String endpointsPath = "src/main/resources/endpoints.txt";

    protected String getEndpoint() {
        String endpoint = null;
        try {
            endpoint = new String(Files.readAllBytes(Paths.get(endpointsPath)), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            String errorText = "Ошибка при получении endpoint: " + e.getMessage();
            fail(errorText);
            logger.error(errorText);
        }
        return endpoint;
    }

    protected void throwAssertionError(String expected, String actual) {
        String errorText = "Ошибка - ожидалось " + expected + ", но было " + actual;
        fail(errorText);
        logger.error(errorText);
    }

    protected void throwStatusAssertionError(Integer expectedStatus, Integer actualStatus) {
        String errorText = "Ошибка - ожидался статус " + expectedStatus + ", но был получен " + actualStatus;
        fail(errorText);
        logger.error(errorText);
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
}
