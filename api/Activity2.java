package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {
final static String baseURI="https://petstore.swagger.io/v2/user";

@Test(priority = 1)
    public void postUser() throws IOException {
    FileInputStream inputStream=new FileInputStream("src/test/java/Activities/Activity2_input.json");
    Response response=given().contentType(ContentType.JSON).body(inputStream).when().post(baseURI);
    inputStream.close();
    response.getBody().prettyPrint();
    response.then().body("code",equalTo(200));
    response.then().body("message",equalTo("5521"));

    }



    @Test(priority = 2)
    public void getUser() throws IOException {
        File fileOutputStream=new File("src/test/java/Activities/Activity2_output.json");
        Response response=given().contentType(ContentType.JSON).when()
                .pathParam("username","sachin").get(baseURI+"/{username}");
        String resBody=response.asPrettyString();

        response.getBody().prettyPrint();

        response.then().body("id",equalTo(5521));
        response.then().body("username",equalTo("sachin"));

        FileWriter fileWriter=new FileWriter(fileOutputStream);
        fileWriter.write(resBody);
        fileWriter.close();
    }
    @Test(priority = 3)
    public void deleteUser()
    {
        Response response=given().contentType(ContentType.JSON).when()
                .pathParam("username","sachin").delete(baseURI+"/{username}");

        response.getBody().prettyPrint();
        response.then().body("code",equalTo(200));
        response.then().body("message",equalTo("sachin"));
    }



}
