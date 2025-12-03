import com.example.ApiController;
import com.example.Application;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.server.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void testGetAllUsers() {
        given().baseUri("http://localhost:" + port + "/api").when().get("/users")
                .then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(2))
                .body("[0].name", equalTo("Alice"))
                .body("[1].name", equalTo("Bob"));
    }

    @Test
    void testGetUserById() {
        given().baseUri("http://localhost:" + port + "/api").pathParam("id", 1).when().get("/users/{id}")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Alice"))
                .body("email", equalTo("alice@example.com"));
    }

    @Test
    void testAddUser() {
        String newUserJson = """
            {
                "name": "Charlie",
                "email": "charlie@example.com"
            }
            """;

        given().baseUri("http://localhost:" + port + "/api").contentType(ContentType.JSON).body(newUserJson).when().post("/users")
                .then().statusCode(200)
                .body("name", equalTo("Charlie"))
                .body("email", equalTo("charlie@example.com"));
    }

    @Test
    void testUpdateUser() {
        String newUserJson = """
        {
            "name": "Temp User",
            "email": "temp@example.com"
        }
        """;

        int newId = given().baseUri("http://localhost:" + port + "/api").contentType(ContentType.JSON).body(newUserJson).when().post("/users")
                .then().statusCode(200)
                .extract().path("id");

        String updatedUserJson = """
        {
            "name": "Updated Name",
            "email": "updated@example.com"
        }
        """;

        given().baseUri("http://localhost:" + port + "/api").contentType(ContentType.JSON).body(updatedUserJson).when().put("/users/" + newId)
                .then().statusCode(200)
                .body("name", equalTo("Updated Name"));
    }

    @Test
    void testDeleteUser() {
        String newUserJson = """
            {
                "name": "ToDelete",
                "email": "delete@example.com"
            }
            """;

        var user = given().baseUri("http://localhost:" + port + "/api").contentType(ContentType.JSON).body(newUserJson)
                .when().post("/users").as(ApiController.User.class);

        given().baseUri("http://localhost:" + port + "/api").pathParam("id", user.getId()).when().get("/users/{id}")
                .then().statusCode(200);
    }
}