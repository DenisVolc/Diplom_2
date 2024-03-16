package base;

import constants.EndPoints;
import io.restassured.response.Response;

public class GetApi extends BaseHttpClient{
    public Response getIngredients(){
        return doGetRequest(EndPoints.INGREDIENTS);
    }
    public Response getOrders(){
        return doGetRequest(EndPoints.ORDERS);
    }
    public Response getOrders(String token){
        return doGetRequest(EndPoints.ORDERS, token);
    }
}
