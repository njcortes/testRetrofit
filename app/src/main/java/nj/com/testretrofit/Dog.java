package nj.com.testretrofit;

public class Dog {

    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
