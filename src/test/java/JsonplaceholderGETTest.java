import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonplaceholderGETTest {

    //Given - konfiguracja
    //When - wysłanie requestu
    //Then - asercje

    @Test
    public void JsonplaceholderReadAllUsers(){
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        //System.out.println(response.asString());

        Assertions.assertEquals(200,response.statusCode());

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        names.stream()
                //.filter(name -> name.startsWith("L"))
                //.forEach(System.out.println(name));
                .forEach(System.out::println);

        Assertions.assertEquals(10,names.size());
    }

    @Test
    public void jsonplaceholderReadOneUser(){
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        Assertions.assertEquals(200,response.statusCode());
        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham",json.get("name"));
        Assertions.assertEquals("Bret",json.get("username"));
        Assertions.assertEquals("Sincere@april.biz",json.get("email"));
    }

    //Path variables
    @Test
    public void jsonplaceholderReadOneUserWithPathvariables() {

        Response response = given()
                .pathParam("userID", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userID}");

        Assertions.assertEquals(200,response.statusCode());
        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham",json.get("name"));
        Assertions.assertEquals("Bret",json.get("username"));
        Assertions.assertEquals("Sincere@april.biz",json.get("email"));
    }
    //Query variables
    @Test
    public void jsonplaceholderReadOneUserWithQueryParams() {

        Response response = given()
                .queryParam("username","Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals(200,response.statusCode());
        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham",json.getList("name").get(0));
        Assertions.assertEquals("Bret",json.getList("username").get(0));
        Assertions.assertEquals("Sincere@april.biz",json.getList("email").get(0));
        Assertions.assertEquals("Kulas Light",json.getList("address.street").get(0));
    }


}
