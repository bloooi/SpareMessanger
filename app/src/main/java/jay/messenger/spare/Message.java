package jay.messenger.spare;

/**
 * Created by leejaebeom on 2017. 8. 8..
 */

public class Message {

    private String message;
    private String timeStamp;
    private String name;
    private String phone;

    public Message() {
    }

    public Message(String message, String timeStamp, String name, String phone) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.name = name;
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
