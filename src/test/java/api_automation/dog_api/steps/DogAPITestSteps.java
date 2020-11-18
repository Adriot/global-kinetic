package api_automation.dog_api.steps;

import com.mongodb.util.JSON;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DogAPITestSteps {
    @Given("^Dog API Endpoints$")
    public void dogAPIEndpoints() {
        RestAssured.baseURI = "https://dog.ceo";
        RestAssured.basePath = "/dog-api";
    }

    @Then("^Verify that a successful message is returned when a user searches for random breeds$")
    public void verifySuccessfulMessageRandomBreedsUserSearches() {
        Response randomBreeds = given().get(RestAssured.baseURI + "/api/breeds/image/random");
        randomBreeds.then().statusCode(200).body("status", equalTo("success"));
    }

    @Then("^Verify that bulldog is on the list of all breeds$")
    public void verifyThatBulldogIsOnTheListOfAllBreeds() throws Throwable {
        Response randomBreeds = given().get(RestAssured.baseURI + "/api/breeds/list/all");
        randomBreeds.then().statusCode(200).body("message.bulldog", notNullValue());
    }

    @Then("^Retrieve all sub-breeds for (.+) and their respective images$")
    public void retrieve_AllSubBreedsForBulldogsAndTheirRespectiveImages(String subBreed) {
        Response subBreedList = given().get(RestAssured.baseURI + "/api/breed/" + subBreed + "/list");
        subBreedList.then().statusCode(200);
        subBreedList.prettyPrint();
        ResponseBody body = subBreedList.body();
        HashMap parse = (HashMap) JSON.parse(body.asString());
        List breedList = (List) parse.get("message");
        for (Object breed : breedList) {
            Response breedImages = given().get(RestAssured.baseURI + "/api/breed/" + subBreed + "/" + breed + "/images");
            breedImages.then().statusCode(200);
            breedImages.prettyPrint();
        }
    }
}
