package freela.api.FREELAAPI.application.web.Exception;

public class ErrorReturn {
    private boolean error;
    private String message;

    public ErrorReturn(String message) {
        this.message = message;
        this.error = true;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
