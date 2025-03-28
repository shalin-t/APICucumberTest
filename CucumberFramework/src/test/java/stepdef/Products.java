package stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class Products {

    private static final String BASE_URL = "https://fakestoreapi.com/";
    private Response response;
    private RequestSpecification request;
    private int cartId;

    @Given("I set up the API request")
    public void setupRequest() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given().contentType(ContentType.JSON);
    }

    @When("I send a GET request to retrieve products")
    public void hitURL() {
        response = request.get("products");
    }

    @Then("I receive the response code as {int}")
    public void receiveResponse(int expectedResponseCode) {
        Assert.assertEquals(expectedResponseCode, response.getStatusCode());
    }

    @When("I send a POST request to add a cart for user {int} with products")
    public void addCart(int userId) {

        Map<String, Object> cartPayload = new HashMap<>();
        cartPayload.put("userId", userId);

        List<Map<String, Object>> products = new ArrayList<>();
        Map<String, Object> product = new HashMap<>();
        product.put("productId", 1);
        product.put("quantity", 2);
        products.add(product);

        cartPayload.put("products", products);

        String jsonPayload = new Gson().toJson(cartPayload);

        response = request.body(jsonPayload).post("carts");
        cartId = response.jsonPath().getInt("id");
    }
    @Then("The cart is successfully created with status {int}")
    public void verifyCartCreated(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertTrue(response.jsonPath().getInt("id") > 0);
    }


    @When("I send a GET request for cart by id {int}")
    public void getCartById(int id) {
    	cartId = id;
        response = request.get("carts/" + cartId);
    }

    @Then("I receive the cart details with status {int}")
    public void verifyGetCart(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertEquals(cartId, response.jsonPath().getInt("id"));
    }


    @When("I send a DELETE request for the created cart")
    public void deleteCartById() {
        response = request.delete("carts/" + cartId);
    }

    @Then("The cart is successfully deleted with status {int}")
    public void verifyCartDeleted(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}

