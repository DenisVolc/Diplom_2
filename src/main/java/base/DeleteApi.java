package base;

import constants.EndPoints;
import io.restassured.response.Response;

public class DeleteApi extends BaseHttpClient{
    public Response deleteUser(String token){
        return doDeleteRequest(EndPoints.USER,token)
                .thenReturn();
    }
}
