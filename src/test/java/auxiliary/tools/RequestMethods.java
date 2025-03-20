package auxiliary.tools;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestMethods {

    public static void changeURL(String newBase){
        RestAssured.baseURI = newBase;
    }

    public static JsonPath rawToJson(String response){
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath;
    }
    //-----------------------GET-------------------------------------------------------------------------------------------------------
    public static String getRequest(String url, Headers headers, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .headers(headers)
                .when()
                .get(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }

    public static String getRequest(String url, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .when()
                .get(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }

    public static String getRequest(String url, Map<String, Object> params, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .params(params)
                .get(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }

    public static String getRequest(String url, Headers headers, Map<String, Object> params, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .headers(headers)
                .params(params)
                .get(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }

    //-----------------------DELETE-------------------------------------------------------------------------------------------------------
    public static String deleteRequest(String url, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .when()
                .delete(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }

    //-----------------------POST-------------------------------------------------------------------------------------------------------
    public static String postRequest(String url, String bodyJson, String user, String pass, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .auth().preemptive().basic(user,pass)
                .body(bodyJson)
                .when()
                .post(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }
    public static String postRequest(String url, String bodyJson, SessionFilter sessionFilter, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .body(bodyJson)
                .filter(sessionFilter)
                .when()
                .post(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }
    public static String postRequest(String url, String bodyJson, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .body(bodyJson)
                .when()
                .post(url)
                .then()
                .assertThat().statusCode(code)
                .extract().response().asString();
    }
    public static String postRequest(String url, Headers headers, String bodyJson, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .headers(headers)
                .body(bodyJson)
                .when()
                .post(url)
                .then()
                .assertThat().statusCode(code)
                //.log().all()
                .extract().response().asString();
    }
    public static String postRequest(String url, Headers headers, String bodyJson, SessionFilter sessionFilter, int code) {
        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .headers(headers)
                .body(bodyJson)
                .filter(sessionFilter)
                .when()
                .post(url)
                .then()
                .assertThat().statusCode(code)
                //.log().all()
                .extract().response().asString();
    }
    public static String postRequest(String url, Map<String, Object> parameters, String bodyJson, int code) {
        return given()
                //.log().all()
                .queryParams(parameters)
                .header("Content-Type", "application/json")
                .body(bodyJson)
                .when()
                .post(url)
                .then()
                .assertThat().statusCode(code)
                //.log().all()
                .extract().response().asString();
    }
    //-----------------------PUT-------------------------------------------------------------------------------------------------------
    public static String putRequest(String url, Map<String, Object> parameters, String bodyJson, int code) {
        return given()
                //.log().all()
                .queryParams(parameters)
                .header("Content-Type", "application/json")
                .body(bodyJson)
                .when()
                .put(url)
                .then()
                .assertThat().statusCode(code)
                //.log().all()
                .extract().response().asString();
    }
}