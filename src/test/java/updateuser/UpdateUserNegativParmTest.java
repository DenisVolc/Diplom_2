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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;



@RunWith(Parameterized.class)
public class UpdateUserNegativParmTest {
    private RegisterRequsetCard registerCard;
    private LoginRequestCard loginCard;
    private UpdateUserReqsuestCard updateCard;
    private PostApi postApi = new PostApi();
    private PatchApi patchApi = new PatchApi();
    private DeleteApi deleteApi = new DeleteApi();
    private String accessToken;
    private String updateEmail ;
    private String updateName ;


    public UpdateUserNegativParmTest(String updateEmail, String updateName) {
        this.updateEmail = updateEmail;
        this.updateName = updateName;
    }

    @Parameterized.Parameters
    public static Object[][] dataSet(){
        return new Object[][]{
                {"1",""},
                {"","1"},
                {"1","1"},
        };
    }
    @Before
    public void setUp() {
        registerCard = new RegisterRequsetCard(
                "b" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Pushok"+BaseHttpClient.getRandomIndex()
        );
        updateCard = new UpdateUserReqsuestCard(
                updateEmail+registerCard.getEmail(),
                updateName+registerCard.getName()
        );
        registerUser();
    }

        @Test
    @DisplayName("Проверка обновления данных пользователя без авторизации")
    public void updateNoLogin(){
        updateUserAssert(updateCard,401,"message","You should be authorised","");
    }

    //--------------------------------------------------------------------
    @Step("Регистрация пользователя")
    public void registerUser(){
        Response response = postApi.doPost(EndPoints.REGISTER,registerCard);
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

    @After
    public void cleanUp(){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }


}

