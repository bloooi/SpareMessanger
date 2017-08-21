package jay.messenger.spare.Contact;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jay.messenger.spare.R;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerViewHolder> {
    ArrayList<Contact> contacts;

    public ContactRecyclerAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts_list, parent, false);
        return new ContactsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsRecyclerViewHolder holder, final int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.phone.setText(contacts.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
