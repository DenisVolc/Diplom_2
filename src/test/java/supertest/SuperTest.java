package supertest;

import base.*;
import json.CreateOrderRequestCard;
import json.LoginRequestCard;
import json.RegisterRequsetCard;
import json.UpdateUserReqsuestCard;
import org.junit.After;

import java.util.List;

public class SuperTest {
    protected PostApi postApi = new PostApi();
    protected DeleteApi deleteApi = new DeleteApi();
    protected GetApi getApi = new GetApi();
    protected PatchApi patchApi = new PatchApi();
    protected LoginRequestCard loginCard;
    protected RegisterRequsetCard registerCard;
    protected CreateOrderRequestCard createOrderCard;
    protected UpdateUserReqsuestCard updateCard;

    protected String accessToken;
    protected List<String> ingredients;

    public void doBefore(){
        registerCard = new RegisterRequsetCard(
                "b" + BaseHttpClient.getRandomIndex() + "@b.com",
                BaseHttpClient.getRandomIndex(),
                "Pushok"+BaseHttpClient.getRandomIndex()
        );
        loginCard = new LoginRequestCard(
                registerCard.getEmail(),
                registerCard.getPassword()
        );
    }
    public void getCreateOrderCard(){
        //не могу сохдать пустую карточку заказа, что бы потом положить туда ингредиенты
        //Потому что для инициализации надо поместить какой-то ингредиент в конструктор,
        //поэтому пришлось прибегнуть к такому костылю. Хочу заметить, что тут не просто хардкод)
        //Этот костыль будет работать при разном размере ingredients.
        ingredients = getApi.getIngredients().getBody().path("data._id");
        int halfIngredientsSize = (ingredients.size()/2)+1;//округляю в большую сторону
        createOrderCard =  new CreateOrderRequestCard(ingredients.subList(halfIngredientsSize-1,halfIngredientsSize));
        

    }

    @After
    public void cleanUp(){
        if(accessToken!=null){
            deleteApi.deleteUser(accessToken).then().statusCode(202);
        }
    }

}
