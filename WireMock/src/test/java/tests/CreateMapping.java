package tests;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;


public class CreateMapping {
	
	// start wiremock Server
	//create a stub
	//test that through RA
	
	public void createStub() {

		/* stubFor(get("/my/resource")
			        .withHeader("Content-Type", containing("xml"))
			        .willReturn(ok()
			            .withHeader("Content-Type", "text/html")
			            .withBody("<response>SUCCESS</response>")));*/
		
		
		stubFor(get(urlEqualTo("/api/services"))
        .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "text/html")
            .withBody("This is sample response ")));
		
		


	}
	
	@Test
	public void verifyStub() {
		
		createStub();
		
		RestAssured.baseURI="http://localhost:8080";
		
		RequestSpecification httprequest=RestAssured.given();
		                                             
		
		Response response=httprequest.request(Method.GET,"/api/services");
		String responseBody=response.getBody().asString();
		System.out.println("responseBody"+responseBody);
		         //  .log().all();
		           //.When()
		
		String contentType=response.header("Content-Type");
		Assert.assertEquals(contentType, "text/html");
		          
		
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		Assert.assertEquals(responseBody.contains("This is sample response "), true);
		
		
		            
	
		
		
		
	}

}
