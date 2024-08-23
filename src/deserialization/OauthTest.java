package deserialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.AbstractAuthenticationHandler;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.courseDetails;
import pojo.webAutomation;

public class OauthTest {
	
public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
		
		String serverResponse =  given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("/oauthapi/oauth2/resourceOwner/token")
		.then().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(serverResponse);
		String accessToken = js.getString("access_token");
		System.out.println(accessToken);
	
		courseDetails getResponse =  given().queryParam("access_token",accessToken)
		.when().get("/oauthapi/getCourseDetails")
		.then().extract().response().as(courseDetails.class);
								
		System.out.println(getResponse.getInstructor());
		
		//To fetch course title of course api 2nd object
		System.out.println(getResponse.getCourses().getApi().get(1).getCourseTitle());
		
		//get price of webAutomation course title=cypress
		int courseSize = getResponse.getCourses().getWebAutomation().size();
		for(int i=0;i<getResponse.getCourses().getWebAutomation().size();i++) {
			if (getResponse.getCourses().getWebAutomation().get(i).getCourseTitle().equalsIgnoreCase("Cypress")) {
				String price =  getResponse.getCourses().getWebAutomation().get(i).getPrice();
				System.out.println("Price of course Cypress is "+price+"");
			}
		}
		
		// print all course titles under web automation
		
		List<String>titleList = new ArrayList<String>();
		List<webAutomation> webAutomation = getResponse.getCourses().getWebAutomation(); 
		for (int i=0;i<webAutomation.size();i++) {
			titleList.add(getResponse.getCourses().getWebAutomation().get(i).getCourseTitle());
		}
		System.out.println(titleList);


	}
}
