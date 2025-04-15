package retrofitTests.login;


import org.junit.Test;
import retrofit.json.ResponseUserInfo;
import retrofit.json.createuser.CreateUser200Response;
import retrofit.json.createuser.CreateUserRequest;
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
        CreateUserRequest body = new CreateUserRequest(email,password,userName);
        Response<CreateUser200Response> response = createUserAPI.createUser(body).execute();
        assertTrue(response.isSuccessful());

    }
}
