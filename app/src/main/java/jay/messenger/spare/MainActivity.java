package jay.messenger.spare;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;

import jay.messenger.spare.Contact.ContactListActivity;
import jay.messenger.spare.RoomList.RoomListAdapter;
import jay.messenger.spare.util.RecyclerItemClickListener;
import jay.messenger.spare.util.SharedPreference;
import jay.messenger.spare.util.Values;

public class MainActivity extends AppCompatActivity {
    ProgressBar roomProgress;
    RecyclerView roomRecyclerView;
    DatabaseReference roomListReference;
//    DatabaseReference userReference;
    ArrayList<String> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        roomProgress = (ProgressBar)findViewById(R.id.main_room_progress);
        roomRecyclerView = (RecyclerView)findViewById(R.id.main_room_list);
        roomListReference = FirebaseDatabase.getInstance().getReference();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        roomRecyclerView.setLayoutManager(manager);
        roomRecyclerView.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));
        UserInfo.setIsLogin(SharedPreference.getData(this, Values.PREF_USER_KEY, Values.PREF_USER_LOGIN_KEY, false));

        if (!UserInfo.isLogin()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else {
            UserInfo.setName(SharedPreference.getData(this, Values.PREF_USER_KEY, Values.PREF_USER_NAME_KEY, ""));
            UserInfo.setPhone(SharedPreference.getData(this, Values.PREF_USER_KEY, Values.PREF_USER_PHONE_KEY, ""));

//            userReference = roomListReference.child(Values.CHILD_USERS).child(UserInfo.getPhone());
//            userReference.keepSynced(true);

            roomListReference.child(Values.CHILD_USERS).child(UserInfo.getPhone()).child(Values.CHILD_CHATROOMS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    roomProgress.setVisibility(View.GONE);
                    UserInfo.setChatRooms((HashMap<String,String>)dataSnapshot.getValue());
                    if(UserInfo.getChatRooms() != null){
                        roomRecyclerView.setVisibility(View.VISIBLE);
                        roomList = new ArrayList<>(UserInfo.getChatRooms().keySet());
                        RoomListAdapter adapter = new RoomListAdapter(roomList, MainActivity.this);
                        roomRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        roomRecyclerView.setVisibility(View.INVISIBLE);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("userReference", databaseError.getMessage());
                }
            });
        }

        roomRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, roomRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent chatActivityIntent = new Intent(MainActivity.this, ChatActivity.class);
                chatActivityIntent.putExtra("IDKey", roomList.get(position));
                startActivity(chatActivityIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        startActivity(new Intent(MainActivity.this, ContactListActivity.class));

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Snackbar.make(view, "악용 하지 않아요 ㅠㅠ 다시 승인 해주실거죠?", Snackbar.LENGTH_SHORT).show();
                    }
                };

                new TedPermission(MainActivity.this)
                        .setPermissionListener(permissionListener)
                        .setRationaleMessage("방을 쉽게 만들기 위해서는 주소록 접근 권한이 필요해요\n다른 용도로는 절대 사용하지 않을 거에요!")
                        .setDeniedMessage("주소록 권한이 없으면 쉽게 대화방을 만드실 수 없어요 ㅠㅠ \n실수로 잘못 누르셨다면 다시 승인 해주세요.")
                        .setPermissions(Manifest.permission.READ_CONTACTS)
                        .check();

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
