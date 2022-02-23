package tests;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class WireMockRuleExample1 {

	

	// we should start server
	// create stub
	//test the stub using rest assured
	// close the server
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule();
	
	@Before
	public void setup() {
		
		wireMockRule.start();

		createStub();

	}

	
	public void createStub() {
		
		
		
		stubFor(get("/v3/players")
				.willReturn(aResponse().withStatus(200)
						.withBodyFile("employee.json")
						.withHeader("Content-Type", "application/json")));

		
		
	}
	
	@Test
	public void verifyStub() {
		

		RestAssured.baseURI="http://localhost:8080";



		RequestSpecification httprequest=RestAssured.given();


		Response response=httprequest.request(Method.GET,"/v3/players");
		String responseBody=response.getBody().asString();
		System.out.println("responseBody"+responseBody);
		//  .log().all();
		//.When()

		String contentType=response.header("Content-Type");
		Assert.assertEquals(contentType, "application/json");


		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		//	Assert.assertEquals(responseBody.contains("This is sample response "), true);

		
		            
	
		
		
		
	}
	
	@After
	public void tearDown() {
		wireMockRule.stop();
		
		
	}

}
