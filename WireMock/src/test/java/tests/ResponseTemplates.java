package tests;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ResponseTemplates {
	
	// Filter the response if the user not intersted for the few fields kind of quer parameters
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(new WireMockConfiguration()
			.port(8080).extensions(new ResponseTemplateTransformer(false)));
	
	@Before
	public void setup() {
		
		wireMockRule.start();

		createStub();

	}
	
	
	public void createStub() {

		
		
		stubFor(post(urlEqualTo("/v1/player"))
        .willReturn(aResponse().withStatus(201)
            .withHeader("Content-Type", "application/json")
           // .withBody("{{jsonPath request.body '$.name'}}")
            //.withBody("{{request.port}}")
            .withBody("{{request.path}}")
            .withStatusMessage("Created")
            .withTransformers("response-template")));
		
		


	}
	
	@Test
	public void teststub() {
		
		RestAssured.baseURI="http://localhost:8080";
		Response response=RestAssured.given()
		           .log()
		           .all()
		           .when()
		           .body("{\r\n"
		           		+ "\"name\":\"Tester\",\r\n"
		           		+ "\"Dept\":\"Automation\",\r\n"
		           		+ "\"Country\":\"India\",\r\n"
		           		+ "\"Rank\":1\r\n"
		           		+ "}")
		           .post("/v1/player");
		int statusCode=response.statusCode();
		System.out.println(statusCode);
		
		response.prettyPrint();
		           
		
		
		
		
		
	}
	
	@After
	public void tearDown() {
		wireMockRule.stop();
		
		
	}

	
	

}
