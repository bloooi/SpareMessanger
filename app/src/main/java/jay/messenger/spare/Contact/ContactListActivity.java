package jay.messenger.spare.Contact;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jay.messenger.spare.CreateRoomDialog;
import jay.messenger.spare.R;
import jay.messenger.spare.RoomList.RoomListModel;
import jay.messenger.spare.RoomList.Users;
import jay.messenger.spare.User;
import jay.messenger.spare.UserInfo;
import jay.messenger.spare.util.DialogCallback;
import jay.messenger.spare.util.RecyclerItemClickListener;
import jay.messenger.spare.util.Values;

public class ContactListActivity extends AppCompatActivity implements DialogCallback{

    RecyclerView contactRecycler;
    DatabaseReference firebaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        firebaseReference = FirebaseDatabase.getInstance().getReference();
        contactRecycler = (RecyclerView)findViewById(R.id.contact_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        contactRecycler.setLayoutManager(manager);
        contactRecycler.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));
        final ArrayList<Contact> contacts = getContactsList();
        Collections.sort(contacts, new Ascending());
        contactRecycler.setAdapter(new ContactRecyclerAdapter(contacts));

        contactRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, contactRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FragmentManager fm = getSupportFragmentManager();
                CreateRoomDialog dialog = CreateRoomDialog.newInstance(contacts.get(position));
                dialog.show(fm,"create_room");
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private ArrayList<Contact> getContactsList(){
        ArrayList<Contact> result = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String name;
        String phoneNumber;
        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            result.add(new Contact(name, phoneNumber));
        }

        cursor.close();

        return result;
    }


    @Override
    public void onCallback(final String name, final String phone) {
        Users users = new Users(UserInfo.getName(), name, UserInfo.getPhone(), phone, 2);
        RoomListModel model = new RoomListModel(users);

        final String idKey = firebaseReference.child(Values.CHILD_ROOMS).push().getKey();
        firebaseReference.child(Values.CHILD_ROOMS).child(idKey).setValue(model);
        firebaseReference.child(Values.CHILD_USERS).child(UserInfo.getPhone()).child(Values.CHILD_CHATROOMS).child(idKey).setValue(phone);

        firebaseReference.child(Values.CHILD_USERS).child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null){
                    firebaseReference.child(Values.CHILD_USERS).child(phone).child("name").setValue(name);   //상대방 데이터 생성
                }

                firebaseReference.child(Values.CHILD_USERS).child(phone).child(Values.CHILD_CHATROOMS).child(idKey).setValue(UserInfo.getPhone());
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    class Ascending implements Comparator<Contact>{

        @Override
        public int compare(Contact t1, Contact t2) {
            return t1.getName().compareTo(t2.getName());
        }
    }
}
