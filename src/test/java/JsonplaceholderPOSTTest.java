import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderPOSTTest {

    @Test
    public void jsonplaceholderCreateNewUser(){

        String jsonBody = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"grzegorz gajownik\",\n" +
                "    \"username\": \"ggrek\",\n" +
                "    \"email\": \"gg@gmail.com\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Kulas Light\",\n" +
                "        \"suite\": \"Apt. 556\",\n" +
                "        \"city\": \"Gwenborough\",\n" +
                "        \"zipcode\": \"92998-3874\",\n" +
                "        \"geo\": {\n" +
                "            \"lat\": \"-37.3159\",\n" +
                "            \"lng\": \"81.1496\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
                "    \"website\": \"hildegard.org\",\n" +
                "    \"company\": {\n" +
                "        \"name\": \"Romaguera-Crona\",\n" +
                "        \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "        \"bs\": \"harness real-time e-markets\"\n" +
                "    }\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/users/")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("grzegorz gajownik",json.get("name"));
        assertEquals("ggrek",json.get("username"));
        assertEquals("gg@gmail.com",json.get("email"));


    }
}