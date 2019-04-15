package test.java.rest.com;

import static org.hamcrest.Matchers.hasItems;

import io.restassured.RestAssured;
import io.restassured.http.Method;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import com.google.gson.Gson;

import java.util.List;
import java.util.Arrays;


public class DemoTest {


    @Test
    public void GetPost_Typicode() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/posts/1");

        DemoHelper.checkStatusCode(response, 200);
        DemoHelper.checkHeader(response, "application/json; charset=utf-8", "gzip", "public, max-age=14400");

        String responseBody = response.getBody().asString();

        SimplePost simplePost = DemoHelper.deserliasation(responseBody);

        Assertions.assertEquals("1", simplePost.getId());
        Assertions.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", simplePost.getTitle());
        Assertions.assertEquals("quia et suscipit\n" +
                "suscipit recusandae consequuntur expedita et cum\n" +
                "reprehenderit molestiae ut ut quas totam\n" +
                "nostrum rerum est autem sunt rem eveniet architecto", simplePost.getBody());

    }

    @Test
    public void GetPost_Typicode_NonExistence() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/posts/102");

        DemoHelper.checkStatusCode(response, 404);

    }
    @Test
    public void GetComment_Post_One_Typicode() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/posts/1/comments");
        DemoHelper.checkStatusCode(response, 200);
        DemoHelper.checkHeader(response, "application/json; charset=utf-8", "gzip", "public, max-age=14400");

        String responseBody = response.getBody().asString();

        List<SimplePost> simplePostList = Arrays.asList(new Gson().fromJson(responseBody, SimplePost[].class));
        Assertions.assertEquals("2", simplePostList.get(1).getId()); // will fail as ID should be only 1 as we are accessing comments for id=1

    }

    @Test
    public void GetComment_Filter_Start_Ten_End_Ten_Typicode() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/comments?_start=10&_limit=10");
        DemoHelper.checkStatusCode(response, 200);
        DemoHelper.checkHeader(response, "application/json; charset=utf-8", "gzip", "public, max-age=14400");

        String responseBody = response.getBody().asString();

        List<SimplePost> simplePostList = Arrays.asList(new Gson().fromJson(responseBody, SimplePost[].class));
        Assertions.assertEquals(10, simplePostList.size()); // will fail as ID should be only 1 as we are accessing comments for id=1

    }
    @Test
    public void PostPost_Typicode() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("test", "kemi test");
        json.put("title", "kemi");
        json.put("body", "kemiauth");

        request.body(json.toJSONString());
        Response response = request.post("https://jsonplaceholder.typicode.com/posts");
        DemoHelper.checkStatusCode(response, 201);
        String responseBody = response.getBody().asString();

        SimplePost simplePost = DemoHelper.deserliasation(responseBody);

        Assertions.assertEquals("101", simplePost.getId());
        Assertions.assertEquals("kemi", simplePost.getTitle());
        Assertions.assertEquals("kemiauth", simplePost.getBody());

    }

    @Test
    public void PostPost_Typicode_with_No_Content_Type() {

        RequestSpecification request = RestAssured.given();
        //request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("test", "kemi test");
        json.put("title", "kemi");
        json.put("body", "kemiauth");

        request.body(json.toJSONString());
        Response response = request.post("https://jsonplaceholder.typicode.com/posts");
        DemoHelper.checkStatusCode(response, 201);
        String responseBody = response.getBody().asString();

        SimplePost simplePost = DemoHelper.deserliasation(responseBody);

        Assertions.assertEquals("101", simplePost.getId());
        Assertions.assertEquals("kemi", simplePost.getTitle()); // Will fail
        Assertions.assertEquals("kemiauth", simplePost.getBody()); // Will fail

    }

    @Test
    public void DeletePost_Typicode() {

        RequestSpecification request = RestAssured.given();

        Response response = request.delete("https://jsonplaceholder.typicode.com/posts/1");
        DemoHelper.checkStatusCode(response, 200);

        String responseBody = response.getBody().asString();

        SimplePost simplePost = DemoHelper.deserliasation(responseBody);

        Assertions.assertEquals(null, simplePost.getId());
    }

    @Test
    public void PutPost_Typicode() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("test", "kemi test");
        json.put("title", "kemi put");
        json.put("body", "kemibody put");

        request.body(json.toJSONString());
        Response response = request.put("https://jsonplaceholder.typicode.com/posts/1");
        DemoHelper.checkStatusCode(response, 200);
        String responseBody = response.getBody().asString();
        SimplePost simplePost = DemoHelper.deserliasation(responseBody);

        Assertions.assertEquals("1", simplePost.getId());
        Assertions.assertEquals("kemi put", simplePost.getTitle());
        Assertions.assertEquals("kemibody put", simplePost.getBody());
    }

    @Test
    public void PutPost_Typicode_NonExistence() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("test", "kemi test");
        json.put("title", "kemi put");
        json.put("body", "kemibody put");

        request.body(json.toJSONString());
        Response response = request.put("https://jsonplaceholder.typicode.com/posts/103");
        DemoHelper.checkStatusCode(response, 404); // Will fail as it returns 200

    }

    @Test
    public void PatchPost_Typicode() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("title", "kemi patch");

        request.body(json.toJSONString());
        Response response = request.patch("https://jsonplaceholder.typicode.com/posts/2");
        DemoHelper.checkStatusCode(response, 200);
        String responseBody = response.getBody().asString();
        SimplePost simplePost = DemoHelper.deserliasation(responseBody);

        Assertions.assertEquals("2", simplePost.getId());
        Assertions.assertEquals("kemi patch", simplePost.getTitle());

    }

    @Test
    public void PatchPost_Typicode_Pass_Same_UserID() {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("userId", "2");

        request.body(json.toJSONString());
        Response response = request.patch("https://jsonplaceholder.typicode.com/posts/2");
        DemoHelper.checkStatusCode(response, 304); // Will fail should return not modified


    }

    @Test
    public void GetPost_Search_Start_End_URL_Typicode() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "posts?_start=0&_end=2");

        DemoHelper.checkStatusCode(response, 200);
        DemoHelper.checkHeader(response, "application/json; charset=utf-8", "gzip", "public, max-age=14400");

        String responseBody = response.getBody().asString();

        List<SimplePost> simplePostList = Arrays.asList(new Gson().fromJson(responseBody, SimplePost[].class));

        Assertions.assertEquals(2, simplePostList.size());


    }

    @Test
    public void GetPost_Search_Startzero_Endhundered_URL_Typicode() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "posts?_start=0&_end=100");

        DemoHelper.checkStatusCode(response, 200);
        DemoHelper.checkHeader(response, "application/json; charset=utf-8", "gzip", "public, max-age=14400");

        String responseBody = response.getBody().asString();

        List<SimplePost> simplePostList = Arrays.asList(new Gson().fromJson(responseBody, SimplePost[].class));
        Assertions.assertEquals(100, simplePostList.size());


    }
}