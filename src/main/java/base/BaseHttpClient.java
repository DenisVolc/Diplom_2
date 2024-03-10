package base;

import constants.EndPoints;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class BaseHttpClient {
    private static RequestSpecification baseRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(EndPoints.URL)
                .addHeader("Content-Type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }



    protected Response doGetRequest(String path){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .get(path)
                .thenReturn();
    }
    protected Response doGetRequest(String path, String token){
        return RestAssured.given()
                .header("Authorization", token)
                .spec(baseRequestSpec())
                .when()
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .body(body)
                .when()
                .post(path)
                .thenReturn();
    }
    protected Response doPostRequestWithAuth(String path, Object body,String token){
        return RestAssured.given()
                .header("Authorization", token)
                .spec(baseRequestSpec())
                .body(body)
                .when()
                .post(path)
                .thenReturn();
    }
    protected Response doPutRequest(String path, String param, String data){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .when()
                .queryParam(param,data)
                .put(path)
                .thenReturn();
    }
    protected Response doDeleteRequest(String path,String token){
        return RestAssured.given()
                .header("Authorization", token)
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(path)
                .thenReturn();
    }
    protected Response doDeleteRequest(String path,Object body){
        return RestAssured.given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(body)
                .delete(path)
                .thenReturn();
    }
    protected Response doPatchRequest(String path,Object body,String token){
        return RestAssured.given()
                .header("Authorization", token)
                .spec(BaseHttpClient.baseRequestSpec())
                .body(body)
                .patch(path)
                .thenReturn();
    }
    public static String getRandomIndex(){
        return String.valueOf((int)(Math.random()*1000000));
    }


}
