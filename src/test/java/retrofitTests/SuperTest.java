package retrofitTests;

import constants.EndPoints;
import org.junit.Before;
import retrofit.api.LoginUserAPI;
import retrofit.api.RegisterUserAPI;
import retrofit.api.UserAPI;
import retrofit.json.registeruser.RegisterUserRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuperTest {
    protected Retrofit retrofit;
    protected UserAPI userAPI;
    protected RegisterUserAPI registerUserAPI;
    protected LoginUserAPI loginUserAPI;
    protected RegisterUserRequest registerDefaultUserRequest = new RegisterUserRequest(
            "a" +Math.random() + "@mail.com",
            "qwerty" +Math.random(),
            "userName" +Math.random()
    );

    @Before
    public void setUp(){
         retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         userAPI = retrofit.create(UserAPI.class);
         registerUserAPI = retrofit.create(RegisterUserAPI.class);
         loginUserAPI = retrofit.create(LoginUserAPI.class);

    }

}
