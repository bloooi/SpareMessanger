package jay.messenger.spare.RoomList;

import java.io.Serializable;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */

public class RoomListModel {
    Users Users;
    private String Key;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }


    public RoomListModel() {
    }

    public RoomListModel(Users Users) {
        this.Users = Users;
    }
}

