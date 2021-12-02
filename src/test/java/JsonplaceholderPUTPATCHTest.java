import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPUTPATCHTest {

    private static Faker faker;
    private String fakeEmail;
    private String fakeName;
    private String fakewebside;
    private String fakeusername;
    private String fakephone;

    @BeforeAll//wykonać się metodzie raz przed testami
    public static void beforeAll(){
        faker = new Faker();
    }
    @BeforeEach//wykonać się przed kazdym naszym testem
    public  void BeforeEach(){

        fakeEmail = faker.internet().emailAddress();
        fakeName = faker.name().name();
        fakeusername = faker.name().username();

        fakephone = faker.phoneNumber().phoneNumber();
        fakewebside = faker.internet().url();
    }

    @Test
    public void jsonplaceholderUpdateNewUserPUT(){
        //obiektowy json//

        Locale locale;



        JSONObject user = new JSONObject();
        user.put("name",fakeName);
        user.put("username",fakeusername);
        user.put("email",fakeEmail);
        user.put("phone",fakephone);
        user.put("website",fakewebside);

        JSONObject geo = new JSONObject();
        geo.put("lat","-37.3159");
        geo.put("lng","81.1496");

        JSONObject address = new JSONObject();
        address.put("street","Kulas Light");
        address.put("suite","Apt. 556");
        address.put("city","Gwenborough");
        address.put("zipcode","92998-3874");
        address.put("geo",geo);
        user.put("address",address);

        JSONObject company = new JSONObject();
        company.put("name","Romaguera-Crona");
        company.put("catchPhrase","Multi-layered client-server neural-net");
        company.put("bs","harness real-time e-markets");
        user.put("company",company);


//            String jsonBody = "{\n" +
//                    "    \"id\": 1,\n" +
//                    "    \"name\": \"grzegorz UBDATEgajownik\",\n" +
//                    "    \"username\": \"ggrek\",\n" +
//                    "    \"email\": \"gg@gmail.com\",\n" +
//                    "    \"address\": {\n" +
//                    "        \"street\": \"Kulas Light\",\n" +
//                    "        \"suite\": \"Apt. 556\",\n" +
//                    "        \"city\": \"Gwenborough\",\n" +
//                    "        \"zipcode\": \"92998-3874\",\n" +
//                    "        \"geo\": {\n" +
//                    "            \"lat\": \"-37.3159\",\n" +
//                    "            \"lng\": \"81.1496\"\n" +
//                    "        }\n" +
//                    "    },\n" +
//                    "    \"phone\": \"1-770-736-8031 x56442\",\n" +
//                    "    \"website\": \"hildegard.org\",\n" +
//                    "    \"company\": {\n" +
//                    "        \"name\": \"Romaguera-Crona\",\n" +
//                    "        \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
//                    "        \"bs\": \"harness real-time e-markets\"\n" +
//                    "    }\n" +
//                    "}";

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeName,json.get("name"));
        assertEquals(fakeusername,json.get("username"));
        assertEquals(fakeEmail,json.get("email"));


    }
    @Test
    public void jsonplaceholderUpdateNewUserPATCH(){


        JSONObject userDetails = new JSONObject();

        userDetails.put("email",fakeEmail);
//            String jsonBody = "{\n" +
//
//                    "    \"email\": \"gg1@gmail.com\"\n" +
//
//                    "}";

        Response response = given()
                .contentType("application/json")
                .body(userDetails.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();


        assertEquals(fakeEmail,json.get("email"));


    }
}
