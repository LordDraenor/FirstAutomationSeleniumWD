package autoFramework;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

public class TestAPI {

	@Test
	public void GetDummyApiDetails() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city/";

		RequestSpecification httpRequest = RestAssured.given();
		
		Response response = httpRequest.get("/Hyderabad");

		int statusCode = response.getStatusCode();
		
		String statusLine = response.getStatusLine();
		
		String responseBody = response.getBody().asString();
		
		Headers headers = response.getHeaders();
		
		JsonPath path = response.jsonPath();
		String temperature = path.getString("Temperature");
		
		System.out.println("The full status line is: " +statusLine);
		System.out.println("The status code is: " +statusCode);
		System.out.println("The headers are: \n"+headers.toString());
		System.out.println("The response is: " + responseBody);
		System.out.println("The temperature is: " + temperature);
		
	}

}
