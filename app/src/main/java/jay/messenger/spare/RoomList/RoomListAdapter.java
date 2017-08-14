package jay.messenger.spare.RoomList;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import jay.messenger.spare.UserInfo;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */

public class RoomListAdapter extends FirebaseRecyclerAdapter<RoomListModel,RoomListViewHolder>{
    DatabaseReference room;
    public RoomListAdapter(Class<RoomListModel> model, int messageLayout, Class<RoomListViewHolder> viewHolder, DatabaseReference ref) {
        super(model, messageLayout, viewHolder, ref);
        room = ref;
    }

    @Override
    protected void populateViewHolder(final RoomListViewHolder viewHolder, final RoomListModel model, int position) {
        if (model != null){
            if (UserInfo.getPhone().equals(model.Users.user1Phone)){
                viewHolder.name.setText(model.Users.user2Name);
            }else {
                viewHolder.name.setText(model.Users.user2Name);
            }
        }

    }
}
