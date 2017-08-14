package jay.messenger.spare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jay.messenger.spare.RoomList.RoomListAdapter;
import jay.messenger.spare.RoomList.RoomListModel;
import jay.messenger.spare.RoomList.RoomListViewHolder;
import jay.messenger.spare.RoomList.Users;
import jay.messenger.spare.util.SharedPreference;
import jay.messenger.spare.util.Values;

public class MainActivity extends AppCompatActivity {

    TextView temp;
    RecyclerView roomList;
    DatabaseReference roomListReference;
    DatabaseReference roomReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        temp = (TextView)findViewById(R.id.temp_text);
        roomList = (RecyclerView)findViewById(R.id.main_room_list);

        roomList.setLayoutManager(new LinearLayoutManager(this));
        UserInfo.setIsLogin(SharedPreference.getData(this, Values.PREF_USER_KEY, Values.PREF_USER_LOGIN_KEY, false));

        if (!UserInfo.isLogin()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else{
            UserInfo.setName(SharedPreference.getData(this, Values.PREF_USER_KEY, Values.PREF_USER_NAME_KEY, ""));
            UserInfo.setPhone(SharedPreference.getData(this, Values.PREF_USER_KEY, Values.PREF_USER_PHONE_KEY, ""));
        }
//        temp.setText(UserInfo.getName() + " " + UserInfo.getPhone());

        roomListReference = FirebaseDatabase.getInstance().getReference();
        roomReference = roomListReference.child(Values.CHILD_ROOMS);
        roomList.setAdapter(
                new RoomListAdapter(
                        RoomListModel.class,
                        R.layout.item_room_list,
                        RoomListViewHolder.class,
                        roomReference
                ));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users users = new Users(UserInfo.getName(), "김도윤",UserInfo.getPhone(), "+821058559208", 2);
                RoomListModel model = new RoomListModel(users);

                String idKey = roomListReference.child("Rooms").push().getKey();
                roomListReference.child(Values.CHILD_ROOMS).child(idKey).setValue(model);
                roomListReference.child(Values.CHILD_USERS).child(UserInfo.getPhone()).child(Values.CHILD_CHATROOMS).child(idKey).setValue("+821058559208");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
