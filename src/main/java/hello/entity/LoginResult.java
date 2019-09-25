package hello.entity;

public class LoginResult extends Result<User> {
    boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }


    public static Result successExecute(String message) {
        return new LoginResult(ResultStatus.OK, message, null, false);
    }

    public static Result successLogin(String message, User user) {
        return new LoginResult(ResultStatus.OK, message, user, true);
    }

    public static Result failure(String message) {
        return new LoginResult(ResultStatus.FAIL, message, null, false);
    }

    private LoginResult(ResultStatus status, String msg, User user, boolean isLogin) {
        super(status, msg, user);
        this.isLogin = isLogin;
    }
}
