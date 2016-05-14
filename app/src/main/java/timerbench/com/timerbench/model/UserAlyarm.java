package timerbench.com.timerbench.model;

/**
 * Created by Ivan on 14.05.2016.
 */
public class UserAlyarm {
    private String message;
    private String time;

    public UserAlyarm() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
