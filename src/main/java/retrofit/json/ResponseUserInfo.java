package retrofit.json;

public class ResponseUserInfo{
    public boolean success;
    public User user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseUserInfo(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public class User {
        public String email;
        public String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(String email, String name) {
            this.email = email;
            this.name = name;
        }
    }
}

