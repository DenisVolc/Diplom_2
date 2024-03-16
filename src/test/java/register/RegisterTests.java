package register;


import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import supertest.SuperTest;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTests extends SuperTest {

    @Before
    public void setUp(){
        doBefore();
    }
    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void registerTest(){
        registerUser(200,"user.email",registerCard.getEmail());
    }
    @Test
    @DisplayName("Проверка создания пользователя, который уже зарегистрирован;")
    public void duplicateRegisterTest(){
        registerUser();
        registerUser(403,"message","User already exists");
    }
    @Test
    @DisplayName("Проверка регистарции без email")
    public void noEmailRegisterTest(){
        registerCard.setEmail("");
        registerUser(403,"message","Email, password and name are required fields");
    }
    @Test
    @DisplayName("Проверка регистарции без пароля")
    public void noPasswordRegisterTest(){
        registerCard.setPassword("");
        registerUser(403,"message","Email, password and name are required fields");
    }
    @Test
    @DisplayName("Проверка регистарции без имени")
    public void noNameRegisterTest(){
        registerCard.setName("");
        registerUser(403,"message","Email, password and name are required fields");
    }
    //--------------------------------------------------------------
    @Step("Регистрация пользователя и проверка ответа")
    public void registerUser(int statusCode, String bodyParm, String equalTo){
        Response response = postApi.doPost(EndPoints.REGISTER,registerCard);

        response.then().statusCode(statusCode)
                .and().assertThat().body(bodyParm,equalTo(equalTo));
        if(response.getStatusCode()==200){
            accessToken = response.getBody().path("accessToken").toString();
        }
    }
    @Step("Регистрация пользователя")
    public void registerUser(){
        postApi.doPost(EndPoints.REGISTER,registerCard);
    }

}
