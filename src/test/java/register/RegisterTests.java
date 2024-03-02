package register;

import base.BaseHttpClient;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import json.RegisterRequsetCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTests {
    private PostApi postApi = new PostApi();
    private RegisterRequsetCard registerCard;
    @Before
    public void setUp(){
         registerCard = new RegisterRequsetCard(
                "a"+ BaseHttpClient.getRandomIndex() +"@a.com",
                "123",
                "Sasha"
        );
    }
    @Test
    public void registerTest(){
        postApi.doPost(EndPoints.REGISTER,registerCard)
                .then().statusCode(200)
                .and().assertThat().body("success",equalTo(true))
                .and().assertThat().body("user.email",equalTo(registerCard.getEmail()));
    }

    @Test
    public void duplicateRegisterTest(){
        postApi.doPost(EndPoints.REGISTER,registerCard);
        postApi.doPost(EndPoints.REGISTER,registerCard)
                .then().statusCode(403)
                .and().assertThat().body("message",equalTo("User already exists"));
    }
    @Test
    public void noEmailRegisterTest(){
        registerCard.setEmail("");
        assertThatNoRequiredField();
    }
    @Test
    public void noPasswordRegisterTest(){
        registerCard.setPassword("");
        assertThatNoRequiredField();
    }
    @Test
    public void noNameRegisterTest(){
        registerCard.setName("");
        assertThatNoRequiredField();
    }
    @Step
    public void assertThatNoRequiredField(){
        postApi.doPost(EndPoints.REGISTER,registerCard)
                .then().statusCode(403)
                .and().assertThat().body("message",equalTo("Email, password and name are required fields"));
    }

    @After
    public void cleanUp(){
        //todo сделать удалеине созданного пользователя
    }

}
