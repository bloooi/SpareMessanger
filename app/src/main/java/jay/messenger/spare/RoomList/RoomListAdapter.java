package jay.messenger.spare.RoomList;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import jay.messenger.spare.R;
import jay.messenger.spare.User;
import jay.messenger.spare.UserInfo;
import jay.messenger.spare.util.Values;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListViewHolder> {
    DatabaseReference room;
    ArrayList<String> roomList;
    RoomListModel model;
    Context context;
    public RoomListAdapter(ArrayList<String> roomList, Context context) {
        room = FirebaseDatabase.getInstance().getReference();
        this.roomList = roomList;
        this.context = context;
    }

    @Override
    public RoomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_list, parent, false);
        return new RoomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RoomListViewHolder holder, int position) {
        room.child(Values.CHILD_ROOMS).child(roomList.get(position)).child(Values.CHILD_USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model = new RoomListModel(dataSnapshot.getValue(Users.class));
                model.setKey(roomList.get(holder.getAdapterPosition()));

                if (model != null){
                    if (model.Users.user1Phone == null){
                        Toast.makeText(context, "널입니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        if (UserInfo.getPhone().equals(model.Users.user1Phone)){
                            holder.name.setText(model.Users.user2Name);
                        }else {
                            holder.name.setText(model.Users.user1Name);

                        }
                    }
                    //이렇게 코드를 짜고 싶지 않았지만 방법이 없어서 이렇게 짬...
                    /*톡방은 항상 두명의 사용자고 방 만들때 자기 정보는 1번 상대방 정보는 2번으로 넣어두지만 상대방이 볼땐 자기 정보가 2번으로 들어가기에 어떻게 서로를 완벽하게 구분할 방법을 찾지 못했음
                    * 마치 연고전과 고연전 느낌이랄까..*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
