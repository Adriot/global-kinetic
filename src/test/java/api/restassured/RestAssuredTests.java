package api.restassured;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredTests {
    @Test
    public void restAssuredTest() {
        Response response = given()
                .when()
                .get("http://ergast.com/api/f1/2017/circuits.json");
        int statusCode = response.statusCode();
        ResponseBody responseBody = response.body();
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody.prettyPrint());
    }
}
