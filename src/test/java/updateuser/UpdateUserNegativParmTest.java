package updateuser;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.UpdateUserReqsuestCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import supertest.SuperTest;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class UpdateUserNegativParmTest extends SuperTest {
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
        doBefore();
        updateCard = new UpdateUserReqsuestCard(
                updateEmail+registerCard.getEmail(),
                updateName+registerCard.getName()
        );
        registerUser(registerCard);
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя без авторизации")
    public void updateNoLogin(){
        updateUserAssert(updateCard,401,"message","You should be authorised","");
    }

    //--------------------------------------------------------------------
    @Step("Регистрация пользователя")
    public void registerUser(Object registerCard){
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
}

