package order;

import base.BaseHttpClient;
import base.GetApi;
import base.PostApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.CreateOrderRequestCard;
import json.RegisterRequsetCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class GetOrdersTests {
    GetApi getApi = new GetApi();
    PostApi postApi = new PostApi();
    RegisterRequsetCard registerRequsetCard;
    List<String> ingredients;
    CreateOrderRequestCard createOrderCard;
    private String token;
    private int expectedOrderNumber;
    private ArrayList actualOrderNumber;

    @Before
    public void setUp(){
        ingredients = getApi.getIngredients().getBody().path("data._id");
        createOrderCard = new CreateOrderRequestCard(ingredients.subList(4,5));//todo убрать костыль
        registerRequsetCard = new RegisterRequsetCard(
                "TestUser" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Sasha"+BaseHttpClient.getRandomIndex()
        );
    }
    @Test
    @DisplayName("Проверка отображения заказов пользователя")
    public void GetOrdersTest(){
        registerUser(registerRequsetCard);
        expectedOrderNumber = createOrder(createOrderCard);
        Response response = getOrder(token);
        actualOrderNumber = response.getBody().path("orders.number");
        response.then().assertThat().statusCode(200);
        assertEquals(expectedOrderNumber, actualOrderNumber.get(0));

    }
    @Test
    @DisplayName("Проверка отображения заказов пользователя без аутентификации")
    public void noAuthGetOrdersTest(){
        getOrder().then().assertThat().statusCode(401)
                .and().assertThat().body("message",equalTo("You should be authorised"));
    }
    //---------------------------------------------------STEPS----------------------------------------------------------
    @Step("Регистрация пользователя")
    public void registerUser(Object body){
        Response response = postApi.doPost(EndPoints.REGISTER, body);
        if(response.getStatusCode()==200) {
            token = response.getBody().path("accessToken").toString();
        }
    }
    @Step("Создать заказ")
    public int createOrder(Object body){
        Response response = postApi.doPostWithAuth(EndPoints.ORDERS,body,token);
        return response.getBody().path("order.number");
    }
    @Step("Вывести заказ пользователя")
    public Response getOrder(String token) {
        return getApi.getOrders(token);
    }
    @Step("Вывести заказ пользователя без авторизации")
    public Response getOrder() {
        return getApi.getOrders();
    }
}
