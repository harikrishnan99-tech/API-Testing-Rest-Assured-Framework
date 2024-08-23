import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

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
		
		String getResponse =  given().queryParam("access_token",accessToken)
		.when().get("/oauthapi/getCourseDetails")
		.then().extract().response().asString();
								
		System.out.println("Response with access token "+accessToken+" = "+getResponse+"");


	}

}
