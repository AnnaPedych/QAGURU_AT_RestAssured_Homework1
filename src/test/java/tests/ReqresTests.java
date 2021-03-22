package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReqresTests {

    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void listAllResourcesTest() {

    }

    @Test
    void verifyResourceParamsTest() {

    }

    @Test
    void createUserTest() {

    }

    @Test
    void updateUserTest() {

    }

    @Test
    void deleteUserTest() {

    }
}
