package qtriptest.APITests;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.UUID;
import io.restassured.RestAssured;



public class testCase_API_01 {
    
    @Test(groups = {"API Tests"})
    public static void TestCase01() {
        RestAssured.baseURI="https://content-qtripdynamic-qa-backend.azurewebsites.net";

        RestAssured.basePath="/api/v1/register";
        JSONObject json1=new JSONObject();
        String email="apitestuser"+UUID.randomUUID().toString()+"@gmail.com";
        String password=UUID.randomUUID().toString();
        json1.put("email", email);
        json1.put("password", password);
        json1.put("confirmpassword", password);
        RequestSpecification http1=RestAssured.given();
        Response response1=http1.contentType("application/json").body(json1.toString()).when().post();
        Assert.assertEquals(response1.getStatusCode(), 201);

        RestAssured.basePath="/api/v1/login";
        JSONObject json2=new JSONObject();
        json2.put("email", email);
        json2.put("password", password);
        RequestSpecification http2=RestAssured.given();
        Response response2 = http2.contentType("application/json").body(json2.toString()).when().post();
        Assert.assertEquals(response2.getStatusCode(), 201);
    }

}
