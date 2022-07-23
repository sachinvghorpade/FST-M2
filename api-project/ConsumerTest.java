package LiveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import static io.restassured.RestAssured.given;


import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String,String> headers=new HashMap<>();
    //API Path
    String userResourcePath="/api/users";

    @Pact(consumer="UserConsumer", provider="UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        headers.put("Content-Type","application/json");

        //set body
        DslPart requestResponseBody=new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        //Create Pact
        return builder.given("request to create user")
                .uponReceiving("request to create user")
                .method("POST")
                .headers(headers)
                .path(userResourcePath)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();

    }
    @Test
    @PactTestFor(providerName = "UserProvider", port="8282")
    public void consumerTest(){
        final String baseURI="http://localhost:8282";
        Map<String,Object> reqBody=new HashMap<>();
        reqBody.put("id",1212);
        reqBody.put("firstName","Sachin");
        reqBody.put("lastName","Ghorpade");
        reqBody.put("email","sachin@test.com");

        //generate Response
        Response response=given().headers(headers).when().body(reqBody).post(baseURI+userResourcePath);

        //Assertions
        response.then().statusCode(201);
    }
}
