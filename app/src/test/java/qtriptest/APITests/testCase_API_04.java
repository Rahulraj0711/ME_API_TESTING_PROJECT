package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {

    @Test(groups = {"API Tests"})
    public static void TestCase04() {
        RestAssured.baseURI="https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath="/api/v1/register";

        String email="apitestuser"+UUID.randomUUID().toString()+"@gmail.com";
        String password=UUID.randomUUID().toString();

        JSONObject json=new JSONObject();
        json.put("email", email);
        json.put("password", password);
        json.put("confirmpassword", password);

        Response response=RestAssured.given().contentType("application/json").body(json.toString()).when().post();
        
        response=RestAssured.given().contentType("application/json").body(json.toString()).when().post();
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.body().jsonPath().getString("message"), "Email already exists");
    }

}

  

