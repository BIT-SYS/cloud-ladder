package util;

public class ApiState {
    public String image_provider = null;
    public String image_token = null;

    private ApiState() {
    }

    private final static ApiState state = new ApiState();

    public static ApiState getSingleton() {
        return state;
    }
}
