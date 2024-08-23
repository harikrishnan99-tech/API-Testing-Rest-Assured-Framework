import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import org.codehaus.groovy.runtime.memoize.CommonCache;

import static io.restassured.RestAssured.*;  //For rest-assured methods

import org.testng.Assert;

import files.common;
import files.payload;

public class basics {

	public static void main(String[] args) {
		
//		RestAssured.baseURI = "https://rahulshettyacademy.com";
//		// Given function for input data
//		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")    //log().all() is used for printing data
//		.body(payload.AddPlace())
//		.when().post("/maps/api/place/add/json")
//		.then().log().all().assertThat().statusCode(200);
		
		//Add a place, update place with new address. Get the place id to validate if new address is added successfully
		
		//Add place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String addResponse =given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = common.rawToJson(addResponse);
		String placeId =js1.getString("place_id"); 
		System.out.println(placeId);
		
		//Update Place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String expectedAddress = "70 winter walk, USA";
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId).header("Content-Type","application/json")
		.body("{\n"
				+ "\"place_id\":\""+placeId+"\",\n"
				+ "\"address\":\""+expectedAddress+"\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get updated place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String getResponse =given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId).header("Content-Type","application/json")
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2 = common.rawToJson(getResponse);
		String actualAddress = js2.getString("address");
		
		Assert.assertEquals(expectedAddress, actualAddress);
		System.out.println("Expected Address from response = "+expectedAddress+"\nActual Address from response =  "+actualAddress+"");
	}

}
