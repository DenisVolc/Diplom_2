package order;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import supertest.SuperTest;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTests extends SuperTest {

    @Before
    public void setUp(){
        doBefore();
        getCreateOrderCard();
        accessToken = registerUser(super.registerCard);

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
        postApi.doPostWithAuth(EndPoints.ORDERS, createOrderCard, accessToken)
                .then().statusCode(200)
                .and().assertThat().body("success",equalTo(true));
    }
    @DisplayName("Cоздание заказа без ингредиентов")
    @Test
    public void noIngredsCreateOrderTest(){
        createOrderCard.clear();
        postApi.doPost(EndPoints.ORDERS,createOrderCard)
                .then().statusCode(400)
                .and().assertThat().body("message",equalTo("Ingredient ids must be provided"));
    }
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void WrongIngredsCreateOrderTest() {
        addIngredient();
        postApi.doPostWithAuth(EndPoints.ORDERS, createOrderCard, accessToken)
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

}

