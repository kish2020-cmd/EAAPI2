import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.PrettyPrinter;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class APITestSuite {

    @Test
    void checkResponseCode(){
        Response response  = RestAssured.get("https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals");
        int statusCode = response.getStatusCode();
        System.out.println("Status Code is: " + statusCode);
        //System.out.println("Response is: " + response.asString());
        System.out.println("Response is: " + response.prettyPrint());
        
        if(statusCode == 200) {
        	if (response.asString().equals("\"\"")) {
        	System.out.println("Response returned is empty");
        	assert false;
        	}
        	else
        	{
        		System.out.println("Valid response code returned:" +statusCode);
        	}
        	
        }
        else if(statusCode == 429) {
        	System.out.println("Too many requests, response code is: "+statusCode);
        	assert false;
        }
        else {
        	System.out.println("Invalid response code: "+statusCode);
        	assert false;
        	
        }       

    }

    @Test
    public void validateJSONSchema(){

        //API end point URL
        RestAssured.baseURI = "https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals";
        
        Response response  = RestAssured.get("https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals");
        System.out.println("Response is: " + response.prettyPrint());
          
        
        //retrieve response
        given()
                .when().get()

                //verify JSON Schema
                .then().assertThat()
                .body(JsonSchemaValidator.
                        matchesJsonSchema(new File("/APITest_EA/src/test/resources/EASchema2.json")));
        		

    }



}
