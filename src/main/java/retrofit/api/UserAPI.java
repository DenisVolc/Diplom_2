package retrofit.api;

import com.google.gson.JsonElement;
import constants.EndPoints;
import retrofit.json.ResponseUserInfo;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface UserAPI {
    @GET(EndPoints.USER)
    Call<ResponseUserInfo> getUserInfo(@Header("Authorization") String token);

    @PATCH(EndPoints.USER)
    Call<JsonElement> patchUser(@Header("Authorization") String token);

    @DELETE(EndPoints.USER)
    Call<Void> deleteUser();



}
