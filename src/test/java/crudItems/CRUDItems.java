package crudItems;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

public class CRUDItems {
	@Test
	public void crudProject() {
		JSONObject body = new JSONObject();
		body.put("Content", "Item");

		Response response = given().auth().preemptive().basic("upb@api.com", "51231").body(body.toString()).log().all()
				.when().post("https://todo.ly/api/items.json");

		response.then().statusCode(200).body("Content", equalTo("Item")).log().all();

		int idProject = response.then().extract().path("Id");

		response = given().auth().preemptive().basic("upb@api.com", "51231").log().all().when()
				.get("https://todo.ly/api/items/" + idProject + ".json");

		response.then().statusCode(200).body("Content", equalTo("Item")).log().all();
		body.put("Checked", true);
		response = given().auth().preemptive().basic("upb@api.com", "51231").body(body.toString()).log().all().when()
				.put("https://todo.ly/api/items/" + idProject + ".json");

		response.then().statusCode(200).body("Content", equalTo("Item")).body("Checked", equalTo(true)).log().all();
		response = given().auth().preemptive().basic("upb@api.com", "51231").log().all().when()
				.delete("https://todo.ly/api/items/" + idProject + ".json");

		response.then().statusCode(200).body("Content", equalTo("Item")).log().all();
	}
}
