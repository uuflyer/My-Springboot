package hello.entity;

public abstract class Result<T> {
    public enum ResultStatus {
        OK("ok"),
        FAIL("fail");

        private String status;

        ResultStatus(String status) {
            this.status = status;
        }
    }

    ResultStatus status;
    String msg;
    T data;

    public Result(ResultStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultStatus status, String msg) {
        this(status, msg, null);
    }

    public Object getData() {
        return data;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
