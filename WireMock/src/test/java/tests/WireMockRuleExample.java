package tests;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

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

public class WireMockRuleExample {

	
	// we should start server
	// create stub
	//test the stub using rest assured
	// close the server
	
	// Reading the json
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule();
	
	@Before
	public void setup() {
		
		wireMockRule.start();

		createStub();

	}

	
	public void createStub() {
		
		
		
		stubFor(get(urlEqualTo("/v1/services"))
        .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "text/html")
            .withBody("{\r\n"
            		+ "\"name\":\"Tester\",\r\n"
            		+ "\"Dept\":\"Automation\",\r\n"
            		+ "\"Country\":\"India\",\r\n"
            		+ "\"Rank\":1\r\n"
            		+ "}")));
		
		
	}
	
	@Test
	public void verifyStub() {
		
		createStub();
		
		RestAssured.baseURI="http://localhost:8080";
		
		RequestSpecification httprequest=RestAssured.given();
		                                             
		
		Response response=httprequest.request(Method.GET,"/v1/services");
		String responseBody=response.getBody().asString();
		System.out.println("responseBody"+responseBody);
		         //  .log().all();
		           //.When()
		
		String contentType=response.header("Content-Type");
		Assert.assertEquals(contentType, "text/html");
		          
		
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		Assert.assertEquals(responseBody.contains("Automation"), true);
		
		
		            
	
		
		
		
	}
	
	@After
	public void tearDown() {
		wireMockRule.stop();
		
		
	}

	
}
