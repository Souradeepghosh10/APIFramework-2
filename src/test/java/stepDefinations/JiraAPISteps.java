package stepDefinations;

import Utility.Filepaths;
import Utility.TestContext;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class JiraAPISteps extends Utils
{
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data = new TestDataBuild();
    TestContext testContext;


    @Given("^Add Payload with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void add_Payload_with_and(String name, String defectDesc) throws Throwable {
        res = given().spec(requestSpecificationJira()).body(data.addDefectPayLoad(name, defectDesc));

    }

    @When("^user calls Jira Api \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void user_calls_Jira_Api_with_http_request(String resource, String method) throws Throwable {
        APIResources resourceAPI=APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        resspec =new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
            response =res.when().post(resourceAPI.getResource());
        else if(method.equalsIgnoreCase("GET"))
            response =res.when().get(resourceAPI.getResource());

    }

    @Then("^the Jira API call got success with status code (\\d+)$")
    public void the_Jira_API_call_got_success_with_status_code(int statusCode) throws Throwable {
        assertEquals(response.getStatusCode(),statusCode);


    }





    @And("^add image to the same ticket with the end point \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void addImageToTheSameTicketWithTheEndPointWithHttpRequest(String resource, String method) throws Throwable {
       String jiraDefectID= getJsonPath(response,"id");
        APIResources resourceAPI=APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        RestAssured.baseURI = "https://souradeepghosh10.atlassian.net/";
       res= given().pathParam("key", jiraDefectID)
               .header("X-Atlassian-Token","no-check")
               .header("Authorization",getGlobalValue("token"))
               .multiPart("file",new File(Filepaths.imageFile)).log().all();

       if (method.equalsIgnoreCase("POST"))
       {
           res.post(resourceAPI.getResource()).then().log().all().assertThat().statusCode(200);
       }



    }
}

