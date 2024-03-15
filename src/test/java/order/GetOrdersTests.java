package order;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import supertest.SuperTest;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class GetOrdersTests extends SuperTest {

    private int expectedOrderNumber;
    private ArrayList actualOrderNumber;

    @Before
    public void setUp(){
        doBefore();
        getCreateOrderCard();

    }
    @Test
    @DisplayName("Проверка отображения заказов пользователя")
    public void GetOrdersTest(){
        registerUser(registerCard);
        expectedOrderNumber = createOrder(createOrderCard);
        Response response = getOrder(accessToken);
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
            accessToken = response.getBody().path("accessToken").toString();
        }
    }
    @Step("Создать заказ")
    public int createOrder(Object body){
        Response response = postApi.doPostWithAuth(EndPoints.ORDERS,body, accessToken);
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
