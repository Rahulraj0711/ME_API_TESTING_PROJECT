package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class testCase_API_03 {

    @Test(groups = {"API Tests"})
    public static void TestCase03() {
        RestAssured.baseURI="https://content-qtripdynamic-qa-backend.azurewebsites.net";
        
        String email="apitestuser"+UUID.randomUUID().toString()+"@gmail.com";
        String password=UUID.randomUUID().toString();
        String userToken=null;
        String userId=null;

        RestAssured.basePath="/api/v1/register";
        JSONObject json1=new JSONObject();
        json1.put("email", email);
        json1.put("password", password);
        json1.put("confirmpassword", password);
        Response response=RestAssured.given().contentType("application/json").body(json1.toString()).when().post();

        if(response.getStatusCode()==201) {
            RestAssured.basePath="/api/v1/login";
            JSONObject json2=new JSONObject();
            json2.put("email", email);
            json2.put("password", password);
            response=RestAssured.given().contentType("application/json").body(json2.toString()).when().post();
            userToken=response.getBody().jsonPath().getString("data.token");
            userId=response.getBody().jsonPath().getString("data.id");
        }

        RestAssured.basePath="/api/v1/reservations/new";
        JSONObject json3=new JSONObject();
        json3.put("userId", userId);
        json3.put("name", "testuser");
        json3.put("date", "2024-21-09");
        json3.put("person", "3");
        json3.put("adventure", "2447910730");
        response=RestAssured.given().header("Authorization", "Bearer "+userToken).contentType("application/json").body(json3.toString()).when().post();
        Assert.assertEquals(response.getStatusCode(), 200);

        RestAssured.basePath="/api/v1/reservations";
        response=RestAssured.given().header("Authorization", "Bearer "+userToken).queryParam("id", userId).get();
        List<HashMap<String, String>> list=response.getBody().jsonPath().getList("$");
        Assert.assertEquals(list.get(0).get("adventure"), "2447910730");
    }
    
}
