package qtriptest.APITests;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.List;
public class testCase_API_02 {

    @Test(groups = {"API Tests"})
    public static void TestCase02() {
        RestAssured.baseURI="https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath="/api/v1/cities";

        RequestSpecification http=RestAssured.given().queryParam("q", "beng");
        Response response=http.get();
        Assert.assertEquals(response.getStatusCode(), 200);
        
        List<Object> list=response.body().jsonPath().getList("$");
        Assert.assertEquals(list.size(), 1);

        File schemaFile=new File("src/test/resources/citiesschema.json");
        JsonSchemaValidator matcher=JsonSchemaValidator.matchesJsonSchema(schemaFile);
        response.then().assertThat().body(matcher);
    }

}
