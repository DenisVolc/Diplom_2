package updateuser;

import base.BaseHttpClient;
import base.DeleteApi;
import base.PatchApi;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.LoginRequestCard;
import json.RegisterRequsetCard;
import json.UpdateUserReqsuestCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest {
    private RegisterRequsetCard registerCard;
    private LoginRequestCard loginCard;
    private UpdateUserReqsuestCard updateCard;
    private PostApi postApi = new PostApi();
    private PatchApi patchApi = new PatchApi();
    private DeleteApi deleteApi = new DeleteApi();
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
        updateCard = new UpdateUserReqsuestCard(
                1+loginCard.getEmail(),
                1+registerCard.getName()
        );
        registerUser();

    }

    @Test
    @DisplayName("Проверка обновления данных пользователя после авторизации")
    public void update(){
        loginUser();
        updateUserAssert(updateCard,200,"user.email",updateCard.getEmail());
    }
    @Test
    @DisplayName("Проверка обновления данных пользователя без авторизации")
    public void updateNoLogin(){
        updateUserAssert(updateCard,401,"message","You should be authorised");
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя старыми данными")
    public void sameUpdate(){
        updateUserAssert(registerCard,403,"message","User with such email already exists");
    }
    //--------------------------------------------------------------------
    @Step("Регистрация пользователя")
    public void registerUser(){
        Response response = postApi.doPost(EndPoints.REGISTER,registerCard);
        if(response.getStatusCode()==200) {
            accessToken = response.getBody().path("accessToken").toString();
        }
    }

    @Step("Авторизация пользователя")
    public void loginUser(){
        Response response = postApi.doPost(EndPoints.LOGIN,loginCard);
//        if(response.getStatusCode()==200) {
//            accessToken = response.getBody().path("accessToken").toString();
//        }
    }
    @Step("Обновить данные пользователя")
    public void updateUserAssert(Object body,int statusCode,String bodyParm,String equalTo){
        Response response = patchApi.updateUser(accessToken,body);
        response.then().statusCode(statusCode)
                .and().assertThat().body(bodyParm,equalTo(equalTo));
        ;
//                response.then().statusCode(200)
//                .and().assertThat().body("user.email",equalTo(updateCard.getEmail()));
    }

    @After
    public void cleanUp(){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }


}
