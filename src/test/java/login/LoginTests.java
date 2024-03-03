package login;

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

public class LoginTests {
    private PostApi postApi = new PostApi();

    private DeleteApi deleteApi = new DeleteApi();
    private LoginRequestCard loginCard;
    private RegisterRequsetCard registerCard;
    private String accessToken;

    @Before
    public void setUp() {
        registerCard = new RegisterRequsetCard(
                "b" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Pushok"+BaseHttpClient.getRandomIndex()
        );
        loginCard = new LoginRequestCard(
                registerCard.getEmail(),
                registerCard.getPassword()
        );

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
    @After
    public void cleanUp(){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }
}
