package login;

import base.BaseHttpClient;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import json.LoginRequestCard;
import json.RegisterRequsetCard;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTests {
    private PostApi postApi = new PostApi();
    private LoginRequestCard loginCard;
    private RegisterRequsetCard registerCard;

    @Before
    public void setUp() {
        registerCard = new RegisterRequsetCard(
                "b" + BaseHttpClient.getRandomIndex() + "@b.com",
                "124",
                "Pushok"
        );
        loginCard = new LoginRequestCard(
                registerCard.getEmail(),
                registerCard.getPassword()
        );

    }

    @Test
    public void loginTest(){
        registerUser();
        postApi.doPost(EndPoints.LOGIN,loginCard)
                .then().statusCode(200)
                .and().assertThat().body("user.email",equalTo(loginCard.getEmail()));
    }
    @Test
    public void wrongEmailLoginTest(){
        registerUser();
        loginCard.setEmail("1"+registerCard.getEmail());
        assertThatWrongEmailPassword();
    }
    @Step
    public void registerUser(){
            postApi.doPost(EndPoints.REGISTER,registerCard);
    }
    @Step
    public void assertThatWrongEmailPassword(){
        postApi.doPost(EndPoints.LOGIN,loginCard)
                .then().statusCode(401)
                .and().assertThat().body("message",equalTo("email or password are incorrect"));
    }
}
