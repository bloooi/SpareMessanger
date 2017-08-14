package jay.messenger.spare.RoomList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import jay.messenger.spare.R;

/**
 * Created by leejaebeom on 2017. 8. 14..
 */

public class RoomListViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView recentMessage;
    public RoomListViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.item_room_name);
        recentMessage = (TextView) itemView.findViewById(R.id.item_room_recent_message);
    }
}
