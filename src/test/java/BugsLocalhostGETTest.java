import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BugsLocalhostGETTest {

    @Test
    public void LocalhostGETAllbugs() {
        Response response = given()
                .when()
                .get(Globalsettings.URL + '/' + Globalsettings.BUGS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> names = json.getList("id");
        //WIELKOSC LISTY
        assertEquals(18, names.size());
    }

    @Test
    public void LocalhostFirstPathVariablesGETCheckValue_example1() {
        Response response = given()
                .when()
                .pathParam("bugsID", 1)
                .get(Globalsettings.URL + "/" + Globalsettings.BUGS + "/{bugsID}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("title1", json.getString("title"));
        assertEquals("open", json.getString("status"));


    }

    @Test
    public void LocalhostFirstPathVariablesGETCheckValue_exaple2() {
        Response response = given()
                .when()
                .pathParam("bugsID", 1)
                .get(Globalsettings.URL + "/" + Globalsettings.BUGS + "/{bugsID}")
                .then()
                .body("title", equalTo("title1"))
                .body("status", equalTo("open"))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

    }

    @Test
    public void LocalhostFirstPathVariablesGETNotEmpty_example1() {
        Response response = given()
                .when()
                .pathParam("bugsID", 1)
                .get(Globalsettings.URL + "/" + Globalsettings.BUGS + "/{bugsID}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        //czy wartośc title nie ma pustych wartosci
        Assertions.assertNotNull(json.get("title"));
        //czy wartośc status nie ma pustych wartosci
        Assertions.assertNotNull(json.get("status"));
        //czy wartośc description nie ma pustych wartosci
        Assertions.assertNotNull(json.get("description"));

    }

    @Test
    public void LocalhostFirstPathVariablesGETNotEmpty_example2() {
        Response response = given()
                .when()
                .pathParam("bugsID", 1)
                .get(Globalsettings.URL + "/" + Globalsettings.BUGS + "/{bugsID}")
                .then()
                .body("title", notNullValue())
                .body("status", notNullValue())
                .body("description", notNullValue())
                .body("title", not(equalTo("")))
                .body("status", not(equalTo("")))
                .body("description", not(equalTo("")))
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


    }

    @Test
    public void LocalhostAllresponseGETnotEmpty() {
        Response response = given()
                .when()

                .get(Globalsettings.URL + '/' + Globalsettings.BUGS)

                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        //isNotEmpty()
        JsonPath json = response.jsonPath();
        List<String> status = json.getList("status");
        IntStream
                .range(0, status.size())
                .forEach(i -> assertNotEquals("", status.get(i)));

        List<String> title = json.getList("title");
        IntStream
                .range(0, title.size())
                .forEach(i -> assertNotEquals("", title.get(i)));

        List<String> description = json.getList("description");
        IntStream
                .range(0, description.size())
                .forEach(i -> assertNotEquals("", description.get(i)));

        List<String> emploeeId = json.getList("emploeeId");
        IntStream
                .range(0, emploeeId.size())
                .forEach(i -> assertNotEquals("", emploeeId.get(i)));


    }

    @Test
    public void LocalhostAllGETreturnStatusOpen() {
        Response response = given()
                .when()
                .get(Globalsettings.URL + '/' + Globalsettings.BUGS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> status = json.getList("status");
        IntStream
                .range(0, status.size())
                .forEach(i -> assertEquals("open", status.get(i)));

    }

    @Test
    public void LocalhostAllGETReturnNewIssues() {
        Response response = given()
                .when()
                .get(Globalsettings.URL + '/' + Globalsettings.BUGS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> title = json.getList("title");
        title.stream()
                .filter(name -> name.startsWith("new"))
                .forEach(name -> System.out.println(name));


    }

    @Test
    public void LocalhostAllGETReturnDescriptionStatusCode404() {
        Response response = given()
                .when()
                .get(Globalsettings.URL + '/' + Globalsettings.BUGS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> description = json.getList("description");
        description
                .stream()
                .filter(name -> name.matches(".*404.*"))
                .forEach(name -> System.out.println(name));


    }

    @Test
    public void LocalhostQueryParamsGETReturnTwoEmployer() {
        Response response = given()
                .when()
                .queryParam("emploeeId", "5")
                .get(Globalsettings.URL + '/' + Globalsettings.BUGS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        List<String> names = json.getList("id");
        //WIELKOSC LISTY
        assertEquals(2, names.size());


    }


}
