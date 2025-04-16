package retrofit.json.registeruser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RegisterUser200Response {
        private boolean success;
        private User user;
        private String accessToken;
        private String refreshToken;

    @Getter
    @Setter
    @AllArgsConstructor
    public class User {
        private String email;
        private String name;
    }
}
