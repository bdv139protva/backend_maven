package lesson4;


import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpoonacularTest extends AbstractTest {

    //Complex Search Test
    @Test
    void getComplexSearchTest() {
        given()
                .spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        JsonPath response = given()
                .spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), equalTo(5224));
        assertThat(response.get("results[1].title"), equalTo("Homemade Garlic and Basil French Fries"));
    }

    @Test
    void getPastaTest() {
        given()
                .spec(getRequestSpecification())
                .queryParam("query", "pasta")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        JsonPath response = given()
                .spec(getRequestSpecification())
                .queryParam("query", "pasta")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(39));
        assertThat(response.get("results[4].title"), equalTo("Pasta With Roasted Vegetables & Greek Olives"));
    }

    @Test
    void getBiscuitsTest() {
        given()
                .spec(getRequestSpecification())
                .queryParam("query", "biscuits")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        JsonPath response = given()
                .spec(getRequestSpecification())
                .queryParam("query", "biscuits")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), equalTo(16));
        assertThat(response.get("results[1].title"), equalTo("Ice Cream Biscuits"));
    }

    @Test
    void getCakeTest() {
        given()
                .spec(getRequestSpecification())
                .queryParam("query", "cake")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        JsonPath response = given()
                .spec(getRequestSpecification())
                .queryParam("query", "cake")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .jsonPath();
        assertThat(response.get("results[0].title"), equalTo("Almost Heaven Cake"));
        assertThat(response.get("results[1].title"), equalTo("German Rhubarb Cake with Meringue"));
    }

    @Test
    void getCarrotsAndTomatoesIngredientsTest() {
        given()
                .spec(getRequestSpecification())
                .queryParam("ingredients", "carrots,tomatoes")
                .queryParam("number", 10)
                .queryParam("limitLicense", true)
                .queryParam("ranking", 1)
                .queryParam("ignorePantry", false)
                .when()
                .get(getBaseUrl() + "recipes/findByIngredients")
                .then()
                .spec(responseSpecification);
    }
    @Test
    void getCarrotsAndTomatoesIngredientsAssertTest() {
        JsonPath response = given()
                .spec(getRequestSpecification())
                .queryParam("ingredients", "carrots,tomatoes")
                .queryParam("number", 10)
                .queryParam("limitLicense", true)
                .queryParam("ranking", 1)
                .queryParam("ignorePantry", false)
                .when()
                .get(getBaseUrl() + "recipes/findByIngredients")
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .jsonPath();
        assertThat(response.get("[0].usedIngredients[0].name"), equalTo("carrots"));
        assertThat(response.get("[0].usedIngredients[1].original"), equalTo("1 container grape tomatoes"));

    }

    //Cuisine Tests
    @Test
    void getItalianTunaPastaTest() {

        given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Italian Tuna Pasta")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .spec(responseSpecification);

        Response response = given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Italian Tuna Pasta")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getCuisine(), containsString("Mediterranean"));
        assertThat(response.getCuisines(), equalTo(new ArrayList<>(Arrays.asList("Mediterranean", "European", "Italian"))));
    }

    @Test
    void getCakeBalls() {
        given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Cake Balls")
                .formParam("ingredientList", "1 stick unsalted butter")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .spec(responseSpecification);

        Response response = given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Cake Balls")
                .formParam("ingredientList", "1 stick unsalted butter")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getCuisine(), containsString("Mediterranean"));
        assertThat(response.getCuisines(), equalTo(new ArrayList<>(Arrays.asList("Mediterranean", "European", "Italian"))));
    }

    @Test
    void getThaiPastaSaladTest() {
        given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Thai Pasta Salad")
                .formParam("ingredientList", "2 cups broccoli")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .spec(responseSpecification);

        Response response = given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Thai Pasta Salad")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getCuisine(), containsString("Asian"));
        assertThat(response.getCuisines(), equalTo(new ArrayList<>(Arrays.asList("Asian", "Thai"))));
    }

    @Test
    void getAppleCrumbleTest() {

        given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Apple Crumble")
                .formParam("ingredientList", "1 tsp cinnamon")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .spec(responseSpecification);

        Response response = given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Apple Crumble")
                .formParam("ingredientList", "1 tsp cinnamon")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getCuisine(), containsString("Mediterranean"));
        assertThat(response.getCuisines(), equalTo(new ArrayList<>(Arrays.asList("Mediterranean", "European", "Italian"))));
    }

    @Test
    void getGingerPuffsTest() {

        given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Ginger Puffs")
                .formParam("ingredientList", "2 eggs")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .spec(responseSpecification);

        Response response = given()
                .spec(requestSpecification)
                .when()
                .formParam("title", "Ginger Puffs")
                .formParam("ingredientList", "2 eggs")
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getCuisine(), containsString("Mediterranean"));
        assertThat(response.getCuisines(), equalTo(new ArrayList<>(Arrays.asList("Mediterranean", "European", "Italian"))));
    }

    @Test
    void test(){
        given().spec(requestSpecification)
                .when()
                .formParam("title","Burger")
                .formParam("language", "en")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .statusCode(200);
    }
}
