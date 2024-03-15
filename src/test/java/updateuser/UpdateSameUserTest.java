package updateuser;

import base.BaseHttpClient;
import base.DeleteApi;
import base.PatchApi;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.RegisterRequsetCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateSameUserTest {
    private RegisterRequsetCard firstUserCard;
    private RegisterRequsetCard secondUserCard;
    private PostApi postApi = new PostApi();
    private PatchApi patchApi = new PatchApi();
    private DeleteApi deleteApi = new DeleteApi();
    private String accessToken;


    @Before
    public void setUp() {
        firstUserCard = new RegisterRequsetCard(
                "b" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Pushok"+BaseHttpClient.getRandomIndex()
        );
        secondUserCard = new RegisterRequsetCard(
                "с" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Sasha"+BaseHttpClient.getRandomIndex()
        );

        registerUser(firstUserCard);

        registerUser(secondUserCard);//токен accessToken перезаписывается актуальный для тестируемого пользователя
    }
    @Test
    @DisplayName("Проверка обновления данных пользователя старыми данными")
    public void sameUpdate(){
        updateUserAssert(firstUserCard,403,"message","User with such email already exists",accessToken);
    }

    //--------------------------------------------------Steps-----------------------------------------------------------
    @Step("Регистрация пользователя")
    public void registerUser(Object body){
        Response response = postApi.doPost(EndPoints.REGISTER,body);
        if(response.getStatusCode()==200) {
            accessToken = response.getBody().path("accessToken").toString();
        }
    }
    @Step("Обновить данные пользователя")
    public void updateUserAssert(Object body,int statusCode,String bodyParm,String equalTo,String accessToken){
        Response response = patchApi.updateUser(accessToken,body);
        response.then().statusCode(statusCode)
                .and().assertThat().body(bodyParm,equalTo(equalTo));
    }
    @Step("Удалить пользователя")
    public void deleteUser(){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }
    @After
    public void cleanUp(){
        deleteUser(); //todo дописать чтоб удалить первого пользователя
    }
}
