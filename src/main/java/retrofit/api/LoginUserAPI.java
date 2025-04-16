package retrofit.api;

import constants.EndPoints;
import retrofit.json.loginuser.LoginUserRequest;
import retrofit.json.registeruser.RegisterUser200Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginUserAPI {

    @POST(EndPoints.LOGIN)
    public Call<RegisterUser200Response> loginUser(@Body LoginUserRequest body);

}
