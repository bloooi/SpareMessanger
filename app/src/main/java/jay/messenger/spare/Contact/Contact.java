package jay.messenger.spare.Contact;

import java.io.Serializable;

/**
 * Created by leejaebeom on 2017. 8. 18..
 */

public class Contact implements Serializable{
    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }
}
