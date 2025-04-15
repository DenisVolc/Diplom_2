package retrofitTests;

import constants.EndPoints;
import org.junit.Before;
import retrofit.api.CreateUserAPI;
import retrofit.api.UserAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuperTest {
    protected Retrofit retrofit;
    protected UserAPI userAPI;
    protected CreateUserAPI createUserAPI;

    @Before
    public void setUp(){
         retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         userAPI = retrofit.create(UserAPI.class);
         createUserAPI = retrofit.create(CreateUserAPI.class);
    }


}
