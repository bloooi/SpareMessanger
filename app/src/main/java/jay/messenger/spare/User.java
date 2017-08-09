package jay.messenger.spare;

import java.util.ArrayList;

/**
 * Created by leejaebeom on 2017. 8. 9..
 */

public class User {
    public String name;
    public String phone;
    public ArrayList<Conversations> conversations;

    public User() {
    }

    public User(String name, String phone, ArrayList<Conversations> conversations) {
        this.name = name;
        this.phone = phone;
        this.conversations = conversations;
    }

    class Conversations{
        private String hash;
        private String talker;
    }
}
