package jay.messenger.spare;

import java.util.HashMap;

/**
 * Created by leejaebeom on 2017. 8. 12..
 */

public class UserInfo {

    private static boolean isLogin = false;
    private static String name;
    private static String phone;
    private static HashMap<String, String> chatRooms;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserInfo.isLogin = isLogin;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserInfo.name = name;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        UserInfo.phone = phone;
    }

    public static HashMap<String, String> getChatRooms() {
        return chatRooms;
    }

    public static void setChatRooms(HashMap<String, String> chatRooms) {
        UserInfo.chatRooms = chatRooms;
    }


}
