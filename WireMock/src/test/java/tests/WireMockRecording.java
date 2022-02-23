package tests;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class WireMockRecording {
	public WireMockServer wireMockServer;
	
	// External environment(down stream /up steam) we have limited time access once we create the stub and we can test it
    @Before
	public void setup() {

		wireMockServer = new WireMockServer();
		wireMockServer.start();
	}


	@Test
	public void record() {
		
		// We are recording based on target url

		wireMockServer.startRecording("http://reqres.in/");
		//After recording the above site we will have multiple end points for an example :single user,list users.page not found etc..
		verifytest();
		SnapshotRecordResult snapshotRecordResult= wireMockServer.stopRecording();
		List<StubMapping>stubmappings=snapshotRecordResult.getStubMappings();
		System.out.println(stubmappings);

	}


	public void verifytest() {


		RestAssured.baseURI="http://localhost:8080";
		Response response=RestAssured.given()
				.log()
				.all()
				.when()
				//.get("/api/users");
				.get("/api/users/2/");

		//int statusCode=response.statusCode();
		//System.out.println(statusCode);

		response.prettyPrint();

	}

} 	
