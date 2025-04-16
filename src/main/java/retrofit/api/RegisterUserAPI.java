package retrofit.api;

import constants.EndPoints;
import retrofit.json.registeruser.RegisterUser200Response;
import retrofit.json.registeruser.RegisterUserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterUserAPI {
    @POST(EndPoints.REGISTER)
    Call<RegisterUser200Response> regisetUser(@Body RegisterUserRequest body);

}
