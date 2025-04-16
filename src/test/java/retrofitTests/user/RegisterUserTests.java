package retrofitTests.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import retrofit.json.loginuser.LoginUserRequest;
import retrofit.json.registeruser.RegisterUser200Response;
import retrofit.json.registeruser.RegisterUserRequest;
import retrofit2.Response;
import retrofitTests.SuperTest;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterUserTests extends SuperTest {

//- создать уникального пользователя;
    @DisplayName("создать уникального пользователя")
    @Test
    public void registerUserTest() throws IOException {
        registerUserAPI.regisetUser(registerDefaultUserRequest).execute();
        Response<RegisterUser200Response> response =loginUserAPI.loginUser(
                new LoginUserRequest(
                        registerDefaultUserRequest.getEmail(),
                        registerDefaultUserRequest.getPassword()
                )).execute();

        assertTrue(response.isSuccessful());
        assertEquals("Неправильный статус-код",200,response.code());
    }

//- создать пользователя, который уже зарегистрирован;
//- создать пользователя и не заполнить одно из обязательных полей.

}
