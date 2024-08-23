import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(payload.complexjson());
		
		// Print No of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);
		
		// Print Purchase Amount
		System.out.println(js.get("dashboard.purchaseAmount").toString());
		
		// Print Title of the first course
		String title = js.getString("courses[0].title");
		System.out.println(title);
		
		// Print All course titles and their respective Prices
		for (int i = 0; i < courseCount; i++) {
			String titles = js.get("courses["+i+"].title"); 
			int prices = js.get("courses["+i+"].price"); 
			System.out.println(titles);
			System.out.println(prices);
		}
		
		// Print no of copies sold by RPA Course
		
		for (int i = 0; i < courseCount; i++) {
			if ("courses["+i+"].title" == "RPA") {
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
	}

}
