package hello.entity;

public class Result {
    String status;
    String msg;
    boolean isLogin;
    Object data;

    public static Result failure(String message) {
        return new Result("fail", message, false);
    }

    public static Result successExecute(String message) {
        return new Result("ok", message, false);
    }

    public static Result successLogin(String message, Object object) {
        return new Result("ok", message, true, object);
    }

    public Result(String status, String msg, boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public Result(String status, String msg, boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public Object getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
