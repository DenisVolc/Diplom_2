package order;

import base.BaseHttpClient;
import base.DeleteApi;
import base.GetApi;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.CreateOrderRequestCard;
import json.RegisterRequsetCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTests {
    GetApi getApi = new GetApi();
    PostApi postApi = new PostApi();
    DeleteApi deleteApi = new DeleteApi();
    CreateOrderRequestCard createOrderCard;
    RegisterRequsetCard registerRequsetCard;
    String token;
    List<String> ingredients;
    @Before
    public void setUp(){
        ingredients = getApi.getIngredients().getBody().path("data._id");
        createOrderCard = new CreateOrderRequestCard(ingredients.subList(0,1));//todo убрать костыль
        registerRequsetCard = new RegisterRequsetCard(
                "TestUser" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Sasha"+BaseHttpClient.getRandomIndex()
        );
        token = registerUser(registerRequsetCard);

    }
//--------------------------------------------------TESTS---------------------------------------------------------------
    @DisplayName("Создание заказа без аутентификации")
    @Test
    public void noAuthCreateOrderTest(){
        addIngredient(3);
        addIngredient(1);
        addIngredient(4);
        postApi.doPost(EndPoints.ORDERS,createOrderCard)
                .then().statusCode(200)
                .and().assertThat().body("success",equalTo(true));
    }
    @DisplayName("Создание заказа c аутентификацией")
    @Test
    public void CreateOrderTest() {
        addIngredient(3);
        addIngredient(1);
        addIngredient(4);
        postApi.doPostWithAuth(EndPoints.ORDERS, createOrderCard, token)
                .then().statusCode(200)
                .and().assertThat().body("success",equalTo(true));
    }
    @DisplayName("Cоздание заказа без ингредиентов")
    @Test
    public void noIngredsCreateOrderTest(){
        createOrderCard.clear();
        postApi.doPost(EndPoints.ORDERS,createOrderCard)
                .then().statusCode(400)
                .and().assertThat().body("message",equalTo("Ingredient ids must be provided"));;
    }
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void WrongIngredsCreateOrderTest() {
        addIngredient();
        postApi.doPostWithAuth(EndPoints.ORDERS, createOrderCard, token)
                .then().statusCode(500);
    }
//-------------------------------------------------STEPS----------------------------------------------------------------
    @Step("Добавить ингредиент")
    public void addIngredient(int index){
        createOrderCard.addIngredients(ingredients.get(index));
    }
    @Step("Добавить ингредиент с неверным хешем")
    public void addIngredient(){
        createOrderCard.addIngredients(ingredients.get(0)+1);
    }
    @Step("Регистрация пользователя")
    public String registerUser(Object body){
        String accessToken = null;
        Response response = postApi.doPost(EndPoints.REGISTER, body);
        if(response.getStatusCode()==200) {
            accessToken = response.getBody().path("accessToken").toString();
        }
        return accessToken;
    }
    @Step("Удалить пользователя")
    public void deleteUser(String accessToken){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    @After
    public void cleanUp(){
        deleteUser(token);
    }
}

