package jay.messenger.spare.util;

/**
 * Created by leejaebeom on 2017. 8. 12..
 */

public abstract class Values {
    //SharedPreference Key
    public static final String PREF_USER_KEY = "userInfo";
    public static final String PREF_USER_NAME_KEY = "name";
    public static final String PREF_USER_PHONE_KEY = "phone";
    public static final String PREF_USER_LOGIN_KEY = "isLogin";
    public static final String PREF_USER_ROOMS_KEY = "chatRooms";

    //Firebase Child Node
    public static final String CHILD_USERS = "Users";
    public static final String CHILD_CHATROOMS= "chatRooms";
    public static final String CHILD_ROOMS= "Rooms";
    public static final String CHILD_CHAT= "chat";
    public static final String CHILD_NAME= "name";
    public static final String CHILD_ROOM_INFO= "info";
}
