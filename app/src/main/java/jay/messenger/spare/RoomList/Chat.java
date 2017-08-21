package jay.messenger.spare.RoomList;

/**
 * Created by leejaebeom on 2017. 8. 8..
 */

public class Chat {

    private String text;
    private String timeStamp;
    private String name;

    public Chat() {
    }

    public Chat(String text, String timeStamp, String name) {
        this.text = text;
        this.timeStamp = timeStamp;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
