import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderGETTwoTest {

    private String BASE_URL = "https://jsonplaceholder.typicode.com";
    private String USERS = "users";

    //Given - konfiguracja
    //When - wysłanie requestu
    //Then - asercje

    //rekomendowane rozwiazanie
    @Test
    public void JsonplaceholderReadAllUsers(){
        Response response = given()
                .when()
                .get(BASE_URL+ "/"+ USERS)
                .then()
                .statusCode(200) //tylko sprawdzamy kod
                .extract()//uzycie extract pozwala na stworzenie valiable response
                .response(); //uzycie extract pozwala na stworzenie valiable response


        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");

        assertEquals(10,names.size());
    }

    //pełne wykorzystanie rest assured
    @Test
    public void jsonplaceholderReadOneUser(){
        given()
                .when()
                .get(BASE_URL+ "/"+ USERS+"/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.street", equalTo("Kulas Light"));



    }

    //Path variables
    @Test
    public void jsonplaceholderReadOneUserWithPathvariables() {

        Response response = given()
                .pathParam("userID", 1)
                .when()
                .get(BASE_URL+ "/"+ USERS+"/{userID}")
                .then()
                .statusCode(200)
                .extract()
                .response();


        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham",json.get("name"));
        assertEquals("Bret",json.get("username"));
        assertEquals("Sincere@april.biz",json.get("email"));
    }
    //Query variables
    @Test
    public void jsonplaceholderReadOneUserWithQueryParams() {

        Response response = given()
                .queryParam("username","Bret")
                .when()
                .get(BASE_URL+ "/"+ USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham",json.getList("name").get(0));
        assertEquals("Bret",json.getList("username").get(0));
        assertEquals("Sincere@april.biz",json.getList("email").get(0));
        assertEquals("Kulas Light",json.getList("address.street").get(0));
    }


}