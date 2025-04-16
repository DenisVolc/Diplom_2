package retrofit.json.loginuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginUserRequest {
    private String email;
    private String password;

}
