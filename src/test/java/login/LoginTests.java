package login;


import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import supertest.SuperTest;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTests extends SuperTest {

    @Before
    public void setUp() {
        doBefore();
    }
    @Test
    @DisplayName("Проверка успешной авторизации")
    public void loginTest(){
        registerUser();
        loginUserAssertThat(200,"user.email",loginCard.getEmail());
    }
    @Test
    @DisplayName("Проврка авторизации с неверным email")
    public void wrongEmailLoginTest(){
        registerUser();
        loginCard.setEmail("1"+registerCard.getEmail());
        loginUserAssertThat(401,"message","email or password are incorrect");
    }
    @Test
    @DisplayName("Проврка авторизации с неверным паролем")
    public void wrongPasswordLoginTest(){
        registerUser();
        loginCard.setPassword("1"+registerCard.getPassword());
        loginUserAssertThat(401,"message","email or password are incorrect");
    }
    @Test
    @DisplayName("Проврка авторизации с неверным логином и паролем")
    public void wrongEmailAndPasswordLoginTest(){
        registerUser();
        loginCard.setEmail("1"+registerCard.getEmail());
        loginCard.setPassword("1"+registerCard.getPassword());
        loginUserAssertThat(401,"message","email or password are incorrect");
    }
    //--------------------------------------------------------------------------------------------------
    @Step("Регистрация пользователя")
    public void registerUser(){
        Response response = postApi.doPost(EndPoints.REGISTER,registerCard);
        if(response.getStatusCode()==200) {
            accessToken = response.getBody().path("accessToken").toString();
        }
    }
    @Step("Авторизация пользователя и проверка ответа")
    public void loginUserAssertThat(int statusCode, String bodyParm, String equalTo){
        postApi.doPost(EndPoints.LOGIN,loginCard)
                .then().statusCode(statusCode)
                .and().assertThat().body(bodyParm,equalTo(equalTo));
    }
}
