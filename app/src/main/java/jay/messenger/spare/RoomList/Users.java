package jay.messenger.spare.RoomList;

import java.io.Serializable;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */
public class Users implements Serializable {
    public String user1Name;
    public String user2Name;
    public String user1Phone;
    public String user2Phone;
    public int usersCount;

    public Users() {
    }

    public Users(String user1Name, String user2Name, String user1Phone, String user2Phone, int usersCount) {
        this.user1Name = user1Name;
        this.user2Name = user2Name;
        this.user1Phone = user1Phone;
        this.user2Phone = user2Phone;
        this.usersCount = usersCount;
    }
}
