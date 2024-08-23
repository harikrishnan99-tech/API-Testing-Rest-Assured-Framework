import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse2 {
	
	// Verify if Sum of all Course prices matches with Purchase Amount
	@Test
	public void coursesSum() {
		JsonPath js = new JsonPath(payload.complexjson());
		int sum = 0;
		int courseCount = js.getInt("courses.size()");
		for (int i = 0; i < courseCount; i++) {
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = price * copies;
			sum = sum + amount;
		}
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, sum);
		System.out.println("Amounts Matched:\nActual Amount="+sum+"\nExpected Amount="+purchaseAmount+"");
	}

}
