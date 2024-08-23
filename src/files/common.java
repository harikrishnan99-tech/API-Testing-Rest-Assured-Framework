package files;

import io.restassured.path.json.JsonPath;

public class common {
	 //converts raw data to json
	public static JsonPath rawToJson (String response) {
		JsonPath jsonPath = new JsonPath(response);
		return jsonPath;
	}

}
