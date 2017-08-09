package jay.messenger.spare;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout nameLayout;
    TextInputLayout phoneLayout;

    AppCompatEditText name;
    AppCompatEditText phone;

    Button loginBtn;

    //Firebase
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameLayout = (TextInputLayout)findViewById(R.id.login_input_name);
        phoneLayout = (TextInputLayout)findViewById(R.id.login_input_phone);

        name = (AppCompatEditText)findViewById(R.id.login_name);
        phone = (AppCompatEditText)findViewById(R.id.login_phone);

        loginBtn = (Button) findViewById(R.id.login_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Users").child(phone.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null){
                            databaseReference.child("Users").child(phone.getText().toString()).child("name").setValue("김도윤");   //데이터 생
                        }
//                        user.phone = phone.getText().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
