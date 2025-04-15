package retrofitTests;

import constants.EndPoints;
import org.junit.Before;
import retrofit.api.UserAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuperTest {

    @Before
    public void setUp(){
         final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         public final UserAPI userAPI = retrofit.create(UserAPI.class);

    }


}
