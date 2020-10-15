package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static com.jayway.restassured.RestAssured.given;





@SpringBootTest
class DemoApplicationTests {



    @Test
    public void makeSureThatGoogleIsUp() {
        given().when().get("http://www.google.com").then().statusCode(200);
    }
    @Test
    public void internalHelloTest() {
        given().when().get("http://localhost:9091/internal/hello").then().statusCode(200);
    }

}
