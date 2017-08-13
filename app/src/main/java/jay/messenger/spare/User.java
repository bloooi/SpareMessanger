package jay.messenger.spare;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leejaebeom on 2017. 8. 9..
 */

public class User {
    public String name;
    public String phone;
    public HashMap<String, String> chatRooms;

    public User() {
    }

    public User(String name, String phone, HashMap <String, String> chatRooms) {
        this.name = name;
        this.phone = phone;
        this.chatRooms = chatRooms;
    }

}
