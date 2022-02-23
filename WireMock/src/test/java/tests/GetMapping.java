package tests;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class GetMapping {


	@Before
	public void setup() {

		getStub();

	}


	public void getStub() {

		stubFor(get("/v1/players")
				.willReturn(aResponse().withStatus(200)
						.withBodyFile("employee.json")
						.withHeader("Content-Type", "application/json")));

	}

	@Test
	public void test() {




		RestAssured.baseURI="http://localhost:8080";



		RequestSpecification httprequest=RestAssured.given();


		Response response=httprequest.request(Method.GET,"/v1/players");
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

}
