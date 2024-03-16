package updateuser;

import base.BaseHttpClient;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.RegisterRequsetCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import supertest.SuperTest;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateSameUserTest extends SuperTest {
    private RegisterRequsetCard beaconUserCard;
    private RegisterRequsetCard testUserCard;
    private String testAccessToken;
    private String beaconAccessToken;


    @Before
    public void setUp() {
        beaconUserCard = new RegisterRequsetCard(
                "beacon_user" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Pushok"+BaseHttpClient.getRandomIndex()
        );
        testUserCard = new RegisterRequsetCard(
                "test_user" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Sasha"+BaseHttpClient.getRandomIndex()
        );
        beaconAccessToken = registerUser(beaconUserCard);
        testAccessToken = registerUser(testUserCard);

    }
    @Test
    @DisplayName("Проверка обновления данных пользователя старыми данными")
    public void sameUpdate(){
        updateUserAssert(beaconUserCard,403,"message","User with such email already exists",testAccessToken);
    }
    //--------------------------------------------------------------------
    @Step("Регистрация пользователя")
    public String registerUser(Object body){
        String accessToken = null;
        Response response = postApi.doPost(EndPoints.REGISTER, body);
        if(response.getStatusCode()==200) {
            accessToken = response.getBody().path("accessToken").toString();
        }
        return accessToken;
    }
    @Step("Обновить данные пользователя")
    public void updateUserAssert(Object body,int statusCode,String bodyParm,String equalTo,String accessToken){
        Response response = patchApi.updateUser(accessToken,body);
        response.then().statusCode(statusCode)
                .and().assertThat().body(bodyParm,equalTo(equalTo));
    }

    public void deleteUser(String accessToken){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }
    @After
    public void cleanUp(){
        deleteUser(beaconAccessToken);
        deleteUser(testAccessToken);
    }
}
