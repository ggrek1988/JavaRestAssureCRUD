import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BugsLocalhostPOSTTest {

    private static Faker faker;
    private String title;
    private String description;
    private Integer emploeeId;
    private String status;


    @BeforeAll//wykona się metoda raz przed testami
    public static void beforeAll(){

        faker = new Faker();



    }
    @BeforeEach//wykonać się przed kazdym  testem
    public  void BeforeEach(){

        title = faker.job().title();
        description = faker.color().name();
        emploeeId = faker.number().numberBetween(1,5);


    }


    @Test
    public void LocalhostPostCreateNewBug(){

        JSONObject user = new JSONObject();
        user.put("title",title);
        user.put("description",description);
        user.put("emploeeId",emploeeId);
        user.put("status","open");

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .post(Global_settings.URL_POSTS+"/"+Global_settings.BUGS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(title,json.get("title"));
        assertEquals(description,json.get("description"));
        assertEquals("open",json.get("status"));


    }
}
