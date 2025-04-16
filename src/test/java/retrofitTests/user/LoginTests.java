package retrofitTests.user;


import org.junit.Test;
import retrofit.json.registeruser.RegisterUser200Response;
import retrofit.json.registeruser.RegisterUserRequest;
import retrofitTests.SuperTest;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTests extends SuperTest {
    @Test
    public void createUser() throws IOException {
        String email = "a"+Math.random()+"@a.com";
        String password = "password";
        String userName = "username2134";

        RegisterUserRequest body = new RegisterUserRequest(email,password,userName);
        Response<RegisterUser200Response> response = registerUserAPI.regisetUser(body).execute();

        assertTrue(response.isSuccessful());
        assertEquals(response.body().getUser().getName(),userName);
    }
}
