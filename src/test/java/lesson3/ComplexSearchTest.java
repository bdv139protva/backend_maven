package lesson3;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;


public class ComplexSearchTest extends AbstractTest {

    @Test
    void getRecipePositiveTest() {
        given()
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information?" +
                        "includeNutrition=false&apiKey=142ed7070ee84b5db3b10d7412157e54")
                                .then()
                                .statusCode(200);

        given()
                .when()
                .get(getBaseUrl() + "recipes/716429/information?" +
                        "includeNutrition=false&apiKey=" + getApiKey())
                .then()
                .statusCode(200);
    }

    @Test
    void getComplexSearchTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), equalTo(5224));
        assertThat(response.get("results[1].title"), equalTo("Homemade Garlic and Basil French Fries"));
    }

    @Test
    void getPastaTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(39));
        assertThat(response.get("results[4].title"), equalTo("Pasta With Roasted Vegetables & Greek Olives"));
    }

    @Test
    void getBiscuitsTest() {
        given()
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?" +
                        "query=biscuits&apiKey=142ed7070ee84b5db3b10d7412157e54")
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "biscuits")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), equalTo(16));
        assertThat(response.get("results[1].title"), equalTo("Ice Cream Biscuits"));
    }

    @Test
    void getCakeTest() {
        given()
                .when()
                .request(Method.GET, getBaseUrl() + "recipes/complexSearch?" +
                        "query={query}&cuisine={cuisine}&diet={diet}&apiKey={apiKey}", "cake", "european", "vegeterian", getApiKey())
                .then()
                .statusCode(200);

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "cake")
                .queryParam("cuisine", "european")
                .queryParam("diet", "vegeterian")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("results[0].title"), equalTo("Almost Heaven Cake"));
        assertThat(response.get("results[1].title"), equalTo("German Rhubarb Cake with Meringue"));
    }

    @Test
    void getCarrotsAndTomatoesIngredientsTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("ingredients", "carrots,tomatoes")
                .queryParam("number", 10)
                .queryParam("limitLicense", true)
                .queryParam("ranking", 1)
                .queryParam("ignorePantry", false)
                .when()
                .get(getBaseUrl() + "recipes/findByIngredients")
                .then()
                .statusCode(200)
                .equals("carrots");
    }
    @Test
    void getCarrotsAndTomatoesIngredientsAssertTest() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("ingredients", "carrots,tomatoes")
                .queryParam("number", 10)
                .queryParam("limitLicense", true)
                .queryParam("ranking", 1)
                .queryParam("ignorePantry", false)
                .when()
                .get(getBaseUrl() + "recipes/findByIngredients")
                .body()
                .jsonPath();
        assertThat(response.get("[0].usedIngredients[0].name"), equalTo("carrots"));
        assertThat(response.get("[0].usedIngredients[1].original"), equalTo("1 container grape tomatoes"));

    }

    @Test
    void getRecipeWithBodyChecksAfterRequestPositiveTest() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .body()
                .jsonPath();

        assertThat(response.get("vegetarian"), is(false));
        assertThat(response.get("vegan"), is(false));
        assertThat(response.get("license"), equalTo("CC BY-SA 3.0"));
        assertThat(response.get("pricePerServing"), equalTo(163.15F));
        assertThat(response.get("extendedIngredients[0].aisle"), equalTo("Milk, Eggs, Other Dairy"));
    }
}
