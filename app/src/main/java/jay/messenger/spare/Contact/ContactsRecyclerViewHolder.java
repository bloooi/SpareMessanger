package jay.messenger.spare.Contact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import jay.messenger.spare.R;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */

public class ContactsRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView phone;
    public ContactsRecyclerViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.item_contact_name);
        phone= (TextView) itemView.findViewById(R.id.item_contact_phone);
    }
}
