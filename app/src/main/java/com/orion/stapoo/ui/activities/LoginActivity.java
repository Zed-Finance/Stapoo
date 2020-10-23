package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                validateCredentials(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });

    }

    private void validateCredentials(final String username, final String password) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        prefManager.setIsLoggedIn(true);
                        prefManager.setUsername(username);
                        startActivity(new Intent(getApplicationContext(), AvatarActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}