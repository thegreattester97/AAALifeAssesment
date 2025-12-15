package com.api.tests;

import com.api.utils.DataLoader;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BookingAPITests {

    private static int bookingId;
    static { baseURI = "https://restful-booker.herokuapp.com"; }

   
    // Data Providers
    @DataProvider(name = "createData")
    public Object[][] createData() throws Exception {
    	// Return data from path 
        return DataLoader.filterJson("src/test/resources/testdata/getBookingData.json", "create_");
    }

    @DataProvider(name = "getData")
    public Object[][] getData() throws Exception {
    	// Return data from path 
        return DataLoader.filterJson("src/test/resources/testdata/getBookingData.json", "get_");
    }

    // API-004 – Create valid booking
    @Test(dataProvider = "createData", priority = 4,
          description = "API-004: POST /booking – Positive – Create a booking with full valid body")
    public void API_004_CreateBooking_Valid(JSONObject testData) {
        if (!testData.getString("type").equals("create_valid")) return;

        String token = (AuthAPITests.token != null) ? AuthAPITests.token : "dummyToken";
        Response res = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .body(testData.toString())
        .when()
                .post("/booking")
        .then()
                .log().all()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/createBookingSchema.json"))
                .extract().response();

        bookingId = res.path("bookingid");
        System.out.println("API-004: Booking created. ID = " + bookingId);
    }


    // API-005 – Missing required field
    @Test(dataProvider = "createData", priority = 5,
          description = "API-005: POST /booking – Negative – Missing required entry")
    public void API_005_CreateBooking_MissingField(JSONObject testData) {
        if (!testData.getString("type").equals("create_missing_firstname")) return;

        given()
                .header("Content-Type", "application/json")
                .body(testData.toString())
        .when()
                .post("/booking")
        .then()
                .log().all()
                .statusCode(anyOf(is(400), is(500)));
    }

    // API-006 – Boundary long firstname
 
    @Test(dataProvider = "createData", priority = 6,
          description = "API-006: POST /booking – Boundary – Long firstname string")
    public void API_006_CreateBooking_LongName(JSONObject testData) {
        if (!testData.getString("type").equals("create_long_firstname")) return;

        given()
                .header("Content-Type", "application/json")
                .body(testData.toString())
        .when()
                .post("/booking")
        .then()
                .log().all()
                .statusCode(anyOf(is(200), is(400)));
    }

    // API-007 – Get valid booking
  
    @Test(dataProvider = "getData", priority = 7,
          description = "API-007: GET /booking/{id} – Positive – Retrieve a valid booking")
    public void API_007_GetBooking_Valid(JSONObject testData) {
        if (!testData.getString("type").equals("get_valid")) return;
        if (bookingId <= 0) { System.out.println("Skipped – no booking created."); return; }

        given()
                .header("Accept", "application/json")
        .when()
                .get("/booking/" + bookingId)
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/getBookingSchema.json"));
    }

    // API-008 – Invalid booking ID
    @Test(dataProvider = "getData", priority = 8,
          description = "API-008: GET /booking/{id} – Negative – Invalid booking ID")
    public void API_008_GetBooking_Invalid(JSONObject testData) {
        if (!testData.getString("type").equals("get_invalid")) return;

        int id = testData.getInt("bookingid");
        given()
                .header("Accept", "application/json")
        .when()
                .get("/booking/" + id)
        .then()
                .log().all()
                .statusCode(anyOf(is(404), is(400)));
    }

    // API-009 – Non-numeric booking ID
    @Test(dataProvider = "getData", priority = 9,
          description = "API-009: GET /booking/{id} – Boundary – Non-numeric ID")
    public void API_009_GetBooking_NonNumeric(JSONObject testData) {
        if (!testData.getString("type").equals("get_non_numeric")) return;

        String id = testData.getString("bookingid");
        given()
                .header("Accept", "application/json")
        .when()
                .get("/booking/" + id)
        .then()
                .log().all()
                .statusCode(anyOf(is(400), is(404)));
    }
}