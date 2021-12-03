import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;



public class BugsLocalhostDeleteTest {

    public static Integer sizeGet;

    @BeforeAll//wykona się metoda raz przed testami
    public static void beforeAll(){
        //pobiera wielosc listy
        Response response = given()
                .when()
                .get(Global_settings.URL_POSTS + '/' + Global_settings.BUGS)
                .then()
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> names = json.getList("id");
        //WIELKOSC LISTY
        sizeGet = names.size();
    }


    @Test
    public void JsonplaceholderDELETEUser(){
        Response response = given()
                .when()
                .pathParam("bugsID", sizeGet)
                .delete(Global_settings.URL_POSTS + "/" + Global_settings.BUGS + "/{bugsID}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


    }

}
