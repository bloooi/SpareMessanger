package jay.messenger.spare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageActivity extends AppCompatActivity {
    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageTextView;
        TextView messengerTextView;
        TextView timestempTextView;
//        ImageView messageImageView;


        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.message_text);
            messengerTextView = (TextView) itemView.findViewById(R.id.messenger_text);
            timestempTextView = (TextView) itemView.findViewById(R.id.timestemp_text);
//            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
        }
    }

    //상수
    public static final String NODE_MESSAGES = "messages";
    public static final String NODE_PHONE = "+821031329208";
    //UI init
    Button sendBtn;
    EditText messageText;
    private ProgressBar mProgressBar;

    //RecyclerView
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    //Firebase init
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        sendBtn = (Button)findViewById(R.id.send_btn);
        messageText = (EditText) findViewById(R.id.message_text);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.message_recycler);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(messageText.getText().toString(), "2017.08.08 00:00:00", "이재범", "01031329208");
                mFirebaseDatabaseReference.child(NODE_MESSAGES)
                        .push().setValue(message);
                messageText.setText("");


            }
        });


        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                Message.class,
                R.layout.item_message,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(NODE_MESSAGES)) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                mProgressBar.setVisibility(View.INVISIBLE);

                viewHolder.messageTextView.setText(model.getMessage());
                viewHolder.messengerTextView.setText(model.getName());
                viewHolder.timestempTextView.setText(model.getTimeStamp());
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);

                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount -1) &&
                                lastVisiblePosition == (positionStart - 1))){
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

    }
}
