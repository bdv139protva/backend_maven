package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CuisineTest extends AbstractTest {

    @Test
    void getItalianTunaPastaTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Italian Tuna Pasta")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200)
                .equals("Italian");

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Italian Tuna Pasta")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisines[0]"), equalTo("Mediterranean"));
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void getCakeBalls() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Cake Balls")
                .formParam("ingredientList", "1 stick unsalted butter")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200)
                .equals("Mediterranean");

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Cake Balls")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisines[2]"), equalTo("Italian"));
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void getThaiPastaSaladTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Thai Pasta Salad")
                .formParam("ingredientList", "2 cups broccoli")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200)
                .equals("Italian");

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Thai Pasta Salad")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Asian"));
    }

    @Test
    void getAppleCrumbleTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Apple Crumble")
                .formParam("ingredientList", "1 tsp cinnamon")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200)
                .equals("European");


        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Apple Crumble")
                .formParam("ingredientList", "1 tsp cinnamon")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisines[1]"), equalTo("European"));
    }

    @Test
    void getGingerPuffsTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Ginger Puffs")
                .formParam("ingredientList", "2 eggs")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .statusCode(200)
                .equals("European");

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Ginger Puffs")
                .formParam("ingredientList", "2 eggs")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisines[2]"), equalTo("Italian"));
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }
}
