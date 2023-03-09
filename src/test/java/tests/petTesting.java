package tests;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Constants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class petTesting {

    private RequestSpecification requestBuilder = new RequestSpecBuilder()
            .setBaseUri(Constants.BASE_URL)
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build();


    @DataProvider(name = "statuses")
    private Object[][] list_of_statuses() {
        String[][] options = {{"available"}, {"pending"}, {"sold"}, {""}, {"invalid_input"}};
        return options;
    }

    @Test(dataProvider = "statuses")
    public void test01_get_pet_by_status(String status) {
        Response response = given()
                .spec(requestBuilder)
                .basePath(Constants.PET_STATUS_URL)
                .param("status", status)
                .when()
                .get();

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void test02_add_new_pet_to_store() {

        JSONObject params = new JSONObject();
        params.put("id", 0);
        params.put("category", "{\"id\":0, \"name\":\"bulldogs\"}");
        params.put("name", "spooty");
        params.put("photoUrls", "[\"www.him.com\"]");
        params.put("tags", "[{\"id\":0,\"name\":\"nothing\"}]");
        params.put("status", "available");

        System.out.println(params.toJSONString());

        Response response = given()
                .spec(requestBuilder)
                .basePath(Constants.PET_URL)
                .body(params.toJSONString())
                .when()
                .post();
        response.then().log().all();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void test03_update_existing_pet() {

        JSONObject params = new JSONObject();
        params.put("id", 0);
        params.put("category", "{\"id\":0, \"name\":\"bulldogs\"}");
        params.put("name", "spooty");
        params.put("photoUrls", "[\"www.him.com\"]");
        params.put("tags", "[{\"id\":0,\"name\":\"nothing\"}]");
        params.put("status", "available");

        System.out.println(params.toJSONString());

        Response response = given()
                .spec(requestBuilder)
                .basePath(Constants.PET_URL)
                .body(params.toJSONString())
                .when()
                .put();
        response.then().log().all();

        assertThat(response.statusCode(), equalTo(200));
    }




}
