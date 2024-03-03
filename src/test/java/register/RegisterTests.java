package register;

import base.BaseHttpClient;
import base.DeleteApi;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.LoginRequestCard;
import json.RegisterRequsetCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTests {
    private PostApi postApi = new PostApi();
    private DeleteApi deleteApi = new DeleteApi();
    private RegisterRequsetCard registerCard;
    private String accessToken;

    @Before
    public void setUp(){
         registerCard = new RegisterRequsetCard(
                "a"+ BaseHttpClient.getRandomIndex() +"@a.com",
                 BaseHttpClient.getRandomIndex(),
                 "Pushok"+BaseHttpClient.getRandomIndex()
        );
    }
    @Test
    @DisplayName("Проверка успешной регистарции пользователя")
    public void registerTest(){
        registerUserAssertThat(200,"user.email",registerCard.getEmail());
    }

    @Test
    @DisplayName("Проверка повторной регистарции пользователя")
    public void duplicateRegisterTest(){
        registerUser();
        registerUserAssertThat(403,"message","User already exists");
    }
    @Test
    @DisplayName("Проверка регистарции без email")
    public void noEmailRegisterTest(){
        registerCard.setEmail("");
        registerUserAssertThat(403,"message","Email, password and name are required fields");
    }
    @Test
    @DisplayName("Проверка регистарции без пароля")
    public void noPasswordRegisterTest(){
        registerCard.setPassword("");
        registerUserAssertThat(403,"message","Email, password and name are required fields");
    }
    @Test
    @DisplayName("Проверка регистарции без имени")
    public void noNameRegisterTest(){
        registerCard.setName("");
        registerUserAssertThat(403,"message","Email, password and name are required fields");
    }

    //--------------------------------------------------------------
    @Step("Регистрация пользователя и проверка ответа")
    public void registerUserAssertThat(int statusCode,String bodyParm, String equalTo){
        Response response = postApi.doPost(EndPoints.REGISTER,registerCard);

        response.then().statusCode(statusCode)
                .and().assertThat().body(bodyParm,equalTo(equalTo));
        if(response.getStatusCode()==200){
            accessToken = response.getBody().path("accessToken").toString();
            System.out.println("Token:"+accessToken);
        }
    }
    @Step("Регистрация пользователя")
    public void registerUser(){
        postApi.doPost(EndPoints.REGISTER,registerCard);
    }
    @After
    public void cleanUp(){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }


}
