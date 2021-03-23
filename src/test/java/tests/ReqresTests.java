package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static utils.FileUtils.readStringFromFile;

public class ReqresTests {

    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void listAllResourcesTest() {
        given()
        .when()
                .get("api/unknown")
        .then()
                .statusCode(200)
                .log().body()
                .body("support.text",
                        is("To keep ReqRes free, contributions towards server costs are appreciated!"))
                .body("total", notNullValue(), "data.id", hasItems(1,2,3,4,5,6))
                .assertThat().body(matchesJsonSchemaInClasspath("resourcesSchema.json"));
    }

    @Test
    void verifyResourceParamsTest() {
        given()
        .when()
                .get("api/unknown/2")
        .then()
                .statusCode(200)
                .log().body()
                .body("support.text",
                        is("To keep ReqRes free, contributions towards server costs are appreciated!"))
                .body("data.id", equalTo(2));
    }

    @Test
    void createUserTest() throws ParseException {
        String data = readStringFromFile("./src/test/resources/login_data.txt");
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(data);

        given()
                .contentType(ContentType.JSON)
                .body(data)
        .when()
                .post("api/users")
        .then()
                .statusCode(201)
                .log().body()
                .body("id", notNullValue())
                .body("name", is(jsonData.get("name")))
                .body("job", is(jsonData.get("job")));
    }

    @Test
    void updateUserTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"job\": \"zion resident\" }")
        .when()
                .put("api/users/2")
        .then()
                .statusCode(200)
                .log().body()
                .body("job", equalTo("zion resident"));
    }

    @Test
    void deleteUserTest() {
        given()
        .when()
                .delete("api/users/2")
        .then()
                .statusCode(204);
    }
}
