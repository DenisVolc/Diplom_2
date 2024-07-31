package base;

import constants.EndPoints;
import io.restassured.response.Response;

public class PatchApi extends BaseHttpClient{
    public Response updateUser(String token,Object body){
        return doPatchRequest(EndPoints.USER,body,token).thenReturn();
    }
}
