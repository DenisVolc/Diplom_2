package retrofit.api;

import com.google.gson.JsonElement;
import constants.EndPoints;
import retrofit.json.createuser.CreateUser200Response;
import retrofit.json.createuser.CreateUserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateUserAPI {
    @POST(EndPoints.REGISTER)
    Call<CreateUser200Response> createUser(@Body CreateUserRequest body);

}
