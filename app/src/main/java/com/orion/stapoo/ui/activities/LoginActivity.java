package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.models.User;
import com.orion.stapoo.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    List<User> userList;
    PrefManager prefManager;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userList = new ArrayList<>();
        prefManager = new PrefManager(this);

        final EditText edtUsername = findViewById(R.id.edt_username);
        final EditText edtPassword = findViewById(R.id.edt_password);
        Button btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtUsername.getText().toString().trim())) {
                    edtUsername.setError("Username cannot be empty");
                } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    edtPassword.setError("Password cannot be empty");
                } else {
                    validateCredentials(edtUsername.getText().toString(), edtPassword.getText().toString());
                }
            }
        });

    }

    private void validateCredentials(final String username, final String password) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    //userList.add(user);
                    assert user != null;
                    if (user.getUsername().equals(username)) {
                        if (user.getPassword().equals(password)) {
                            toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG);
                            toast.show();
                            prefManager.setIsLoggedIn(true);
                            prefManager.setUsername(username);
                            checkAndNavigate();
                            break;
                        }
                    }else{
                        toast = Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
          }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        toast.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //toast.cancel();
    }

    private void checkAndNavigate() {
        FirebaseDatabase.getInstance().getReference().child("users").child(prefManager.getUsername()).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), AvatarActivity.class));
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}