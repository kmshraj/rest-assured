package test.java.rest.com;
import static org.hamcrest.Matchers.hasItems;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.net.SocketFlow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;


public class DemoHelper {
    public static void checkStatusCode (Response response, int code) {
        Assertions.assertEquals(response.getStatusCode(),code, "Correct status code returned");
    }

    public static SimplePost deserliasation(String json){
        Gson gson = new Gson();
        SimplePost simplePost = gson.fromJson(json,SimplePost.class);
        return simplePost;
    }


    public static void checkHeader (Response response, String contentType, String contentEncoding, String cacheControl) {
        Assertions.assertEquals(response.header("Content-Type"), contentType);
        Assertions.assertEquals(response.header("Content-Encoding"), contentEncoding);
        Assertions.assertEquals(response.header("Cache-Control"), cacheControl);

    }





}
