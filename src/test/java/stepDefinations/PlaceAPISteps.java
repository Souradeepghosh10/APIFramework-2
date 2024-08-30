package stepDefinations;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class PlaceAPISteps extends Utils
{
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data =new TestDataBuild();
    static String place_id;

    @Given("^API Add Place Payload with \"([^\"]*)\"  \"([^\"]*)\" \"([^\"]*)\"$")
    public void add_Place_Payload_with(String name, String language, String address) throws Throwable {
        res=given().spec(requestSpecification())
                .body(data.addPlacePayLoad(name,language,address));

    }

    @When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void user_calls_with_http_request(String resource, String method) throws Throwable {
        APIResources resourceAPI=APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
            response =res.when().post(resourceAPI.getResource());
        else if(method.equalsIgnoreCase("GET"))
            response =res.when().get(resourceAPI.getResource());

    }

    @Then("^the API call got success with status code (\\d+)$")
    public void the_API_call_got_success_with_status_code(int arg1) throws Throwable {
        assertEquals(response.getStatusCode(),200);
    }

    @Then("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void in_response_body_is(String keyValue, String Expectedvalue) throws Throwable {
        assertEquals(getJsonPath(response,keyValue),Expectedvalue);

    }

    @Then("^verify place_Id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws Throwable {
        place_id=getJsonPath(response,"place_id");
        res=given().spec(requestSpecification()).queryParam("place_id",place_id);
        user_calls_with_http_request(resource,"GET");
        String actualName=getJsonPath(response,"name");
        assertEquals(actualName,expectedName);

    }
}
