package patika.n11.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;

//import static io.restassured.RestAssured.given;


import static net.serenitybdd.rest.SerenityRest.given;
@SerenityTest
public class N11AdminGetTest {
    @BeforeAll
    public static void init(){
        baseURI = "https://api.thedogapi.com/v1/";
    }
    @Test
    public void getAllN11(){

        //https://api.thedogapi.com/v1/favourites?sub_id=your-user-1234
        given().headers("x-api-key","8adf71fc-c27b-40ef-8662-19ab891129e3")
                .accept(ContentType.JSON)
                .when()
                .get("favourites")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }
    @Test
    public void getOneUserVotes(){
        //https://api.thedogapi.com/v1/votes?sub_id=your-user-1234
        given()
                .headers("x-api-key","8adf71fc-c27b-40ef-8662-19ab891129e3")
                .accept(ContentType.JSON)
                .pathParam("sub_id","your-user-1234")
                .when()
                .get("votes?sub_id={sub_id}")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @DisplayName("GET request with Serenity Assertion way")
    @Test
    public void getOneUserVotesAssertion(){

        given()
                .headers("x-api-key","8adf71fc-c27b-40ef-8662-19ab891129e3")
                .and()
                .pathParam("sub_id","your-user-1234")
                .when()
                .get("votes?sub_id={sub_id}");


        //Serenity way of assertion

        Ensure.that("Status code is 200", validatableResponse -> validatableResponse.statusCode(200) );

        Ensure.that("Content-type is JSON",vRes -> vRes.contentType(ContentType.JSON));

        Ensure.that("sub_id is your-user-1234", vRes -> vRes.body("sub_id is",is("your-user-1234")));


    }

}
