package retrofit.json.createuser;

public class CreateUser200Response {
        private boolean success;
        private User user;
        private String accessToken;
        private String refreshToken;


        // Getter Methods

        public boolean getSuccess() {
            return success;
        }

        public User getUser() {
            return user;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        // Setter Methods

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setUser(User userObject) {
            this.user = userObject;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

    public class User {
        private String email;
        private String name;


        // Getter Methods

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        // Setter Methods

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
