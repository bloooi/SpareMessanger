package jay.messenger.spare;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Locale;

import jay.messenger.spare.util.SharedPreference;
import jay.messenger.spare.util.Values;

import static jay.messenger.spare.util.Values.PREF_USER_LOGIN_KEY;
import static jay.messenger.spare.util.Values.PREF_USER_NAME_KEY;
import static jay.messenger.spare.util.Values.PREF_USER_PHONE_KEY;
import static jay.messenger.spare.util.Values.PREF_USER_KEY;
import static jay.messenger.spare.util.Values.PREF_USER_ROOMS_KEY;

public class LoginActivity extends AppCompatActivity {

    Spinner localeCodeSpinner;

    TextInputLayout nameLayout;
    TextInputLayout phoneLayout;

    AppCompatEditText name;
    AppCompatEditText phone;

    Button loginBtn;

    String combinePhoneNumber;
    //Firebase
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        localeCodeSpinner = (Spinner)findViewById(R.id.locale_code_spinner);

        nameLayout = (TextInputLayout)findViewById(R.id.login_input_name);
        phoneLayout = (TextInputLayout)findViewById(R.id.login_input_phone);

        name = (AppCompatEditText)findViewById(R.id.login_name);
        phone = (AppCompatEditText)findViewById(R.id.login_phone);

        loginBtn = (Button) findViewById(R.id.login_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        localeCodeSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Values.SERVICE_COUNTRY_CODE));
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinePhoneNumber = localeCodeSpinner.getSelectedItem().toString() + phone.getText().toString().substring(1);  //국가 번호 + 앞에 0을 뺸 전화 번호

                databaseReference.child(Values.CHILD_USERS).child(combinePhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null){
                            user.phone = dataSnapshot.getKey();
                            if (!user.name.equals(name.getText().toString())){
                                databaseReference.child(Values.CHILD_USERS).child(combinePhoneNumber).removeValue();
                                user = null;
                            }
                        }

                        if (user == null){
                            databaseReference.child(Values.CHILD_USERS).child(combinePhoneNumber).child(Values.CHILD_NAME).setValue(name.getText().toString());   //데이터 생성
                            UserInfo.setName(name.getText().toString());
                            UserInfo.setPhone(combinePhoneNumber);
                            UserInfo.setChatRooms(null);
                            UserInfo.setIsLogin(true);
                        }else{
                            UserInfo.setName(user.name);
                            UserInfo.setPhone(user.phone);
                            UserInfo.setChatRooms(user.chatRooms);
                            UserInfo.setIsLogin(true);
                        }

                        SharedPreference.saveData(getApplicationContext(), PREF_USER_KEY, PREF_USER_NAME_KEY, UserInfo.getName());
                        SharedPreference.saveData(getApplicationContext(), PREF_USER_KEY, PREF_USER_PHONE_KEY, UserInfo.getPhone());
                        SharedPreference.saveData(getApplicationContext(), PREF_USER_KEY, PREF_USER_LOGIN_KEY, UserInfo.isLogin());

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
//                        SharedPreference.saveData(getApplicationContext(), PREF_USER_KEY, PREF_USER_ROOMS_KEY, UserInfo.getChatRooms());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("LA" + databaseError.getMessage(), databaseError.getDetails());

                    }
                });
            }
        });
    }
}
