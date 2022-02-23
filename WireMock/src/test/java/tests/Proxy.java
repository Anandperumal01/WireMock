package tests;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class Proxy {
	
	
	public WireMockServer wireMockServer;
	
	// proxy--Routing
    @Before
	public void setup() {

		wireMockServer = new WireMockServer(8080);
		wireMockServer.start();
		createStub();
	}

	
	public void createStub() {
		
		stubFor(get(urlEqualTo("/api/users/"))
		        .willReturn(aResponse()
		            .proxiedFrom("https://reqres.in/")));
		
	}
	
	@Test
	public void test() {
		

		RestAssured.baseURI="http://localhost:8080";
		Response response=RestAssured.given()
				.log()
				.all()
				.when()
				.get("/api/users");
			
		//int statusCode=response.statusCode();
		//System.out.println(statusCode);

		response.prettyPrint();
		
	}
	
	@After
	public void tearDown() {
		wireMockServer.stop();
		
		
	}
	
	

}
