package tests;

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
            fail("Ошибка: " + e.getMessage());
            logger.error("Ошибка: " + e);
        }
        return endpoint;
    }
}
