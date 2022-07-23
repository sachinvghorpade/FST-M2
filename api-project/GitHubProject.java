package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProject {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    final String baseURI="https://api.github.com/user/keys";
    String SSHKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCVPEQb7VBWVgefFKMnTg3w15ciA/TDanlmjJCql97dhqjGIVkOP+/bWOaXGd8AUSn9/7cbO9HcACEK9OPCkOtx2Lfr/TaeNrUn7r1dNtHquvMJfpgJFonWmZz/njkvp0aZa6J0h7S9FkKA98mtUAc2ZRgH4pfa2HWcdbezUPtqUT1TduQFOoimkOhFAP3sddPx8fodqH5owyzDWbDV1gbdm4JwrmupmlkAkofwijXPoTItWRi0yZFq2+Bc7MSzTkt4f/odwWAvrBk2yA8L8IKIbMcWdAvX2Lhk31Motp715EinmeJYnaTlhwC7UBmdFfJk7H6Kc9MdvmCjYZ5iC/IN ";
    String Tokenid="token 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";
    int id;
    @BeforeClass
    public void setup(){
        requestSpecification=new RequestSpecBuilder().
                addHeader("Authorization",Tokenid).
                setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test(priority=1)
    public void addSSHKey() {
        String reqBody = "{" +  "\"title\":\"TestAPIKey\"," + "\"key\":\""+SSHKey+"\"" + "}";

        Response response=given().spec(requestSpecification).body(reqBody).when().post();
        System.out.println(response.prettyPrint());
        id=response.then().extract().path("id");
        response.then().statusCode(201);

    }

    @Test (priority=2)
    public void getSSHKey() {

        Response response = given().spec(requestSpecification).when().
                pathParam("keyid", id) // Set path parameter
                .get(baseURI + "/{keyid}");

        System.out.println(response.getBody().asPrettyString());

        response.then().statusCode(200);
    }

    @Test(priority=3)

    public void deleteSSHKey() {

        Response response =given().spec(requestSpecification).when().
                pathParam("keyid", id) // Set path parameter
                .delete(baseURI + "/{keyid}"); // Send DELETE request

        System.out.println(response.getBody().asPrettyString());
        // Assertion
        response.then().statusCode(204);

    }


}
